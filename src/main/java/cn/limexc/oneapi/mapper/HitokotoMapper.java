/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.pojo.HitokotoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LIMEXC
 * @since 2022-05-13
 **/
@Mapper
public interface HitokotoMapper {

    /**
     * 默认未传参数时，生成一个随机数查询并返回数据
     *
     * @param id id
     * @return 一言
     */
    HitokotoVO selectHitokotoDefault(@Param("id") int id);

    /**
     * 一言传参时返回数据
     *
     * @param param 参数
     * @return 一言
     */
    List<HitokotoVO> selectHitokoto(@Param("param")Map<String ,String > param);
}
