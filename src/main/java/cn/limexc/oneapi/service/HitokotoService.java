/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service;

import cn.limexc.oneapi.pojo.HitokotoVO;

/**
 * @author LIMEXC
 * @since 2022-05-13
 **/
public interface HitokotoService {

    /**
     * 获取一言
     *
     * @param type  类型
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return  一言VO
     */
    HitokotoVO getHitokoto( String type,String minLength, String maxLength);
}
