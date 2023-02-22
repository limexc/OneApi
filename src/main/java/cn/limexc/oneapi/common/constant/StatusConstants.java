/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.common.constant;

/**
 * StatusConstants
 * 用于ResponseResult中的code
 *
 * @author LIMEXC
 **/
public final class StatusConstants {

    private StatusConstants() {
        throw new IllegalStateException("Cannot create instance of static constant class");
    }

    public static final Integer OK = 200;

    public static final Integer ERROR = 500;

    public static final Integer NOT_FOUND = 404;


}
