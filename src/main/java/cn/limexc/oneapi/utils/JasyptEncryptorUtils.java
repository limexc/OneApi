/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 密码加密工具
 *
 * @author LIMEXC
 * @since 2022-05-11
 **/
@Slf4j
public final class JasyptEncryptorUtils {

    /**
     * 盐值
     */
    private static final String SALT = "lybgeek";

    private static BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    static {
        basicTextEncryptor.setPassword(SALT);
    }

    private JasyptEncryptorUtils(){}

    /**
     * 明文加密
     * @param plaintext 明文密码
     * @return 加密密码
     */
    public static String encode(String plaintext){
        log.info("明文字符串：{}", plaintext);
        String ciphertext = basicTextEncryptor.encrypt(plaintext);
        log.info("加密后字符串：{}",ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext 加密密码
     * @return 明文密码
     */
    public static String decode(String ciphertext){
        log.info("加密字符串：{}" , ciphertext);
        ciphertext = "ENC(" + ciphertext + ")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertext)){
            String plaintext = PropertyValueEncryptionUtils.decrypt(ciphertext,basicTextEncryptor);
            log.info("解密后的字符串：{}" , plaintext);
            return plaintext;
        }
        log.error("解密失败");
        return "";
    }
}