/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service;

/**
 * @author LIMEXC
 * @since 2022-05-17
 **/
public interface WeatherService {

    String generateGetDiaryWeatherURL(String location, String language, String unit, String start, String days);
}
