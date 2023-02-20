/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.scheduled.constant;

/**
 * 定时任务状态枚举
 *
 * @author LIMEXC
 * @since 2022-05-24
 **/
public enum ScheduledStatus {

    /**
     * 禁用
     */
    DISABLE(0, "禁用"),
    /**
     * 启用
     */
    ENABLE(1, "启用");

    private int code;
    private String name;

    ScheduledStatus(int code, String name){
        this.code = code;
        this.name = name;
    }

    public static ScheduledStatus getByCode(int code){
        for (ScheduledStatus st : values()) {
            if(code == st.getCode()){
                return st;
            }

        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
