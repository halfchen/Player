package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.WeatherData;
import com.chen.playerdemo.contract.WeatherContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public class WeatherModel implements WeatherContract.Model {
    @Override
    public Flowable<WeatherData> getWeather(String key, String city, String province) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getWeather(key, "思明区",city, province);
    }
}
