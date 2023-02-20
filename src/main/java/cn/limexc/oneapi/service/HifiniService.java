/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service;

/**
 * @author LIMEXC
 * @since 2022-06-21
 **/
public interface HifiniService {

    /**
     * 通过cookie登录/更新
     *
     * @param cookie
     */
    void loginHifini(String cookie);
}
