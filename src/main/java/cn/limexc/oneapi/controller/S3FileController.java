/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.controller;

import cn.limexc.oneapi.annotation.SystemLog;
import cn.limexc.oneapi.common.ResponseResult;
import cn.limexc.oneapi.utils.FileUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * S3文件存储 上传与返回下载地址接口
 *
 * @author LIMEXC
 * @since 2022-08-29
 **/
@Slf4j
@RestController
@RequestMapping("/v1/file")
public class S3FileController {

    @PostMapping("/upload")
    @SystemLog("文件上传")
    @PreAuthorize("hasAuthority('上传文件')")
    public ResponseResult<String> uploadFile(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if (null == file) {
                return new ResponseResult<>(500,"不能上传空文件！");
            }

            FileUtils.uploadMultipartFileByPart(file);
        } catch (Exception e) {
            return new ResponseResult<>(500,e.getMessage());
        }
        return new ResponseResult<>(200,"上传成功");
    }


}
