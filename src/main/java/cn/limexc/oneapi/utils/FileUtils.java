/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import cn.limexc.oneapi.config.S3Config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
public class FileUtils {


    /**
     * default bucket
     */
    private static final String BUCKET_NAME = S3Config.getBucketName();

    private static final Integer MIN_THREAD_NUM = 1;
    private static final Integer MAX_THREAD_NUM = 5;

    private static final Long KEEP_TIME = 0L;

    /**
     * 向 AWS 客户端明确提供凭证
     */
    private static final BasicAWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials(S3Config.getAccessKey(), S3Config.getSecretKey());


    private static final AmazonS3 S3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(S3Config.getEndpoint(), ""))
            .build();


    /**
     * 分段上传文件至S3
     *
     * @param file
     */
    public static void uploadMultipartFileByPart(MultipartFile file) {
        //声明线程池
        ThreadFactory threadFactory = new CustomizableThreadFactory("s3Thread-pool-");
        ExecutorService exec = new ThreadPoolExecutor(MIN_THREAD_NUM, MAX_THREAD_NUM,
                KEEP_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10), threadFactory);


        long size = file.getSize();
        String fileName = file.getOriginalFilename();
        int minPartSize = 5 * 1024 * 1024;
        // 得到总共的段数，和 分段后，每个段的开始上传的字节位置
        List<Long> positions = Collections.synchronizedList(new ArrayList<>());
        long filePosition = 0;
        while (filePosition < size) {
            positions.add(filePosition);
            filePosition += Math.min(minPartSize, (size - filePosition));
        }
        log.info("总大小：{}，分为{}段", size, positions.size());
        // 创建一个列表保存所有分传的 PartETag, 在分段完成后会用到
        List<PartETag> partETags = Collections.synchronizedList(new ArrayList<>());
        // 第一步，初始化，声明下面将有一个 Multipart Upload
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(BUCKET_NAME, fileName);
        InitiateMultipartUploadResult initResponse = S3.initiateMultipartUpload(initRequest);
        log.info("开始上传");
        long begin = System.currentTimeMillis();
        try {
            // MultipartFile 转 File
            File toFile = multipartFileToFile(file);
            for (int i = 0; i < positions.size(); i++) {
                int finalI = i;
                exec.execute(() -> {
                    long time1 = System.currentTimeMillis();
                    UploadPartRequest uploadRequest = new UploadPartRequest()
                            .withBucketName(BUCKET_NAME)
                            .withKey(fileName)
                            .withUploadId(initResponse.getUploadId())
                            .withPartNumber(finalI + 1)
                            .withFileOffset(positions.get(finalI))
                            .withFile(toFile)
                            .withPartSize(Math.min(minPartSize, (size - positions.get(finalI))));
                    // 第二步，上传分段，并把当前段的 PartETag 放到列表中
                    partETags.add(S3.uploadPart(uploadRequest).getPartETag());
                    long time2 = System.currentTimeMillis();
                    log.info("第{}段上传耗时：{}", finalI + 1, (time2 - time1));
                });
            }
            //任务结束关闭线程池
            exec.shutdown();
            //判断线程池是否结束，不加会直接结束方法
            while (true) {
                if (exec.isTerminated()) {
                    break;
                }
            }

            // 第三步，完成上传，合并分段
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(BUCKET_NAME, fileName,
                    initResponse.getUploadId(), partETags);
            S3.completeMultipartUpload(compRequest);
            //删除本地缓存文件
            toFile.delete();
        } catch (Exception e) {
            S3.abortMultipartUpload(new AbortMultipartUploadRequest(BUCKET_NAME, fileName, initResponse.getUploadId()));
            log.error("Failed to upload, " + e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("总上传耗时：{}", (end - begin));
    }


    /**
     * MultipartFile 转 File
     */
    private static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (file.isEmpty() || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            //获取流文件
            OutputStream os = new FileOutputStream(toFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        return toFile;
    }

}
