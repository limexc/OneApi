/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.limexc.oneapi.mapper.HitokotoMapper;
import cn.limexc.oneapi.pojo.HitokotoVO;
import cn.limexc.oneapi.service.HitokotoService;
import cn.limexc.oneapi.utils.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LIMEXC
 * @since 2022-05-13
 **/
@Slf4j
@Service
public class HitokotoServiceImpl implements HitokotoService {
    @Autowired
    HitokotoMapper hitokotoMapper;

    private static final String HITOKOTO_REDIS_KEY="ONEAPI_HITOKOTO_HASH";


    /**
     * 获取一言
     *
     * @return 一句话
     */
    @Override
    public HitokotoVO getHitokoto(String type,String minLength, String maxLength){
        // 是否走默认标识
        Boolean isDefault = Boolean.TRUE;
        // 保存查询条件
        Map<String,String> param = new HashMap<>(16);
        HitokotoVO hitokoto;
        // 三个查询条件
        if (!StringUtil.isNullOrEmpty(type)){
            isDefault = Boolean.FALSE;
            // 判断传入格式是否正确
            param.put("type",type);
        }
        if (!StringUtil.isNullOrEmpty(maxLength)){
            isDefault = Boolean.FALSE;
            param.put("maxLength",maxLength);
        }
        if (!StringUtil.isNullOrEmpty(minLength)){
            isDefault = Boolean.FALSE;
            param.put("minLength",minLength);
        }

        // 默认查询
        if (isDefault){
            hitokoto = getHitokotoDefault();
        }else {
            hitokoto = getHitokotoRule(param);
        }

        return hitokoto;
    }




    private HitokotoVO getHitokotoDefault() {
        int random = RandomUtil.randomInt(0,5212);
        // 看一下缓存中有没有，有的话直接取缓存中的数据
        HitokotoVO hitokoto = readCache(String.valueOf(random));
        if (null == hitokoto){
            hitokoto = hitokotoMapper.selectHitokotoDefault(random);
            writeCache(hitokoto);
        }
        return hitokoto;
    }

    private HitokotoVO getHitokotoRule(Map<String ,String > param){
        int random;
        HitokotoVO hitokoto = new HitokotoVO();
        List<HitokotoVO> hitokotoList = hitokotoMapper.selectHitokoto(param);
        if (null != hitokotoList && hitokotoList.size()>0){
            random = RandomUtil.randomInt(0,hitokotoList.size());
            hitokoto = hitokotoList.get(random);
            // 写缓存
            writeCache(hitokotoList);
        }
        return hitokoto;
    }

    /**
     * 获取到数据后写redis缓存
     *
     * @param hitokoto 一言
     */
    private void writeCache(HitokotoVO hitokoto){
        RedisUtils.HashOps.hPut(HITOKOTO_REDIS_KEY,String.valueOf(hitokoto.getId()), JSONObject.toJSONString(hitokoto));
    }

    /**
     * 批量写缓存，用于默认的简易查询
     * 写缓存操作可以单独分出线程去做
     *
     * @param hitokotoList 一言List
     */
    private void writeCache(List<HitokotoVO> hitokotoList){
        // 读缓存,对数据取补集后写入 写库的数据-在库数据-->真正需要写库数据
        Map<Object, Object> libraryData = RedisUtils.HashOps.hGetAll(HITOKOTO_REDIS_KEY);
        Map<String,String> libData = libraryData.entrySet().stream().collect(Collectors.toMap(map->(String) map.getKey(), map -> (String)map.getValue()));
        Map<String,String> hitokotoMap = new HashMap<>();
        hitokotoList.forEach(hitokoto -> {
            hitokotoMap.put(String.valueOf(hitokoto.getId()),JSONObject.toJSONString(hitokoto));
        });
        Map<String,String > newHitokotoMap = new HashMap<>(0);
        if (null!=libraryData && libraryData.size()>0 && hitokotoMap.size()>0){
            Set<String> libDataMapKey = libData.keySet();
            Set<String> writeMapKey = hitokotoMap.keySet();
            writeMapKey.removeAll(libDataMapKey);
            newHitokotoMap = hitokotoMap.entrySet()
                    .stream()
                    .filter(map -> writeMapKey.contains(map.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        if (newHitokotoMap.size()>0){
            RedisUtils.HashOps.hPutAll(HITOKOTO_REDIS_KEY,newHitokotoMap);
        }
    }

    /**
     * 从redis中获取数据
     *
     * @param hitokotoId 一言Id
     * @return hitokotoVO 一言
     */
    private HitokotoVO readCache(String hitokotoId){
        HitokotoVO hitokotoVO = null;
        if (RedisUtils.HashOps.hExists(HITOKOTO_REDIS_KEY,hitokotoId)){
            String hitokotoJson = String.valueOf(RedisUtils.HashOps.hGet(HITOKOTO_REDIS_KEY,hitokotoId));
            hitokotoVO = JSON.parseObject(hitokotoJson,HitokotoVO.class);
            log.info("一言 命中缓存数据！ -> {}",hitokotoJson);
        }
        return hitokotoVO;
    }


}
