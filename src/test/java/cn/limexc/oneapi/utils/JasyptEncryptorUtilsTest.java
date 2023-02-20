/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import org.junit.jupiter.api.Test;

/**
 * @author LIMEXC
 * @since 2022-05-11
 **/
public class JasyptEncryptorUtilsTest {

    @Test
    public void encryptionTest(){
        JasyptEncryptorUtils.encode("");
    }

    @Test
    public void decryptionTest(){
        JasyptEncryptorUtils.decode("");
    }
}
