/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 获取和判断文件头信息
 *
 * @author LIMEXC
 * @since 2022-06-23
 */
@Slf4j
public class GetTypeByHeadUtils {
    /**
     * 缓存文件头信息-文件头信息
     */
    public static final HashMap<String, String> FILE_TYPES = new HashMap<>();
    static {
        FILE_TYPES.put("FFD8FFE0", "jpg");
        FILE_TYPES.put("89504E47", "png");
        FILE_TYPES.put("47494638", "gif");
        FILE_TYPES.put("49492A00", "tif");
        FILE_TYPES.put("424D", "bmp");
        FILE_TYPES.put("38425053", "psd");
        FILE_TYPES.put("41433130", "dwg");
        FILE_TYPES.put("7B5C727466", "rtf");
        FILE_TYPES.put("3C3F786D6C", "xml");
        FILE_TYPES.put("68746D6C3E", "html");
        FILE_TYPES.put("44656C69766572792D646174653A", "eml");
        FILE_TYPES.put("D0CF11E0", "doc");
        FILE_TYPES.put("5374616E64617264204A", "mdb");
        FILE_TYPES.put("252150532D41646F6265", "ps");
        FILE_TYPES.put("255044462D312E", "pdf");
        FILE_TYPES.put("504B0304", "docx");
        FILE_TYPES.put("52617221", "rar");
        FILE_TYPES.put("57415645", "wav");
        FILE_TYPES.put("41564920", "avi");
        FILE_TYPES.put("2E524D46", "rm");
        FILE_TYPES.put("000001BA", "mpg");
        FILE_TYPES.put("000001B3", "mpg");
        FILE_TYPES.put("6D6F6F76", "mov");
        FILE_TYPES.put("3026B2758E66CF11", "asf");
        FILE_TYPES.put("4D546864", "mid");
        FILE_TYPES.put("1F8B08", "gz");
        FILE_TYPES.put("4D5A9000", "exe/dll");
        FILE_TYPES.put("75736167", "txt");
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        log.info(getFileHeader(filePath));
        log.info(FILE_TYPES.get(getFileHeader(filePath)));
        return FILE_TYPES.get(getFileHeader(filePath));
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[4];
            /*
             * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
             * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
             */
            int totalByte = is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception ignored) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return value;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src
     *            要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte b : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(b & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        log.info(builder.toString());
        return builder.toString();
    }

    public static void main(String[] args) {
        final String fileType = getFileType("D:\\Downloads\\79e0b9f092ec574ebbae276b1aff6e55.jpg");
        System.out.println(fileType);
    }
}
