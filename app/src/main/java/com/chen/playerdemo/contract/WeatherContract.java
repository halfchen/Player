package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.WeatherData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public interface WeatherContract {

    interface Model {

        Flowable<WeatherData> getWeather(String key, String city, String province);
    }

    interface View extends BaseView {

        void setData(WeatherData data);
    }

    interface Presenter {

        void getWeather(String key, String city, String province);
    }
}
