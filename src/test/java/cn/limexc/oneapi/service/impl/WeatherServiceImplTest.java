/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.limexc.oneapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LIMEXC
 * @since 2022-05-17
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherServiceImplTest {

    @Autowired
    WeatherService weatherService;

    @Test
    public void weatherTest(){
        try {
            String url = weatherService.generateGetDiaryWeatherURL(
                    "shanghai",
                    "zh-Hans",
                    "c",
                    "1",
                    "1"
            );
            System.out.println("URL:" + url);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}
