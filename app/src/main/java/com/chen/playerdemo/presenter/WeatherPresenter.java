package com.chen.playerdemo.presenter;

import android.util.Log;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.WeatherData;
import com.chen.playerdemo.contract.WeatherContract;
import com.chen.playerdemo.model.WeatherModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public class WeatherPresenter extends BasePresenter<WeatherContract.View> implements WeatherContract.Presenter {

    private WeatherContract.Model model;

    public WeatherPresenter() {
        model = new WeatherModel();
    }

    @Override
    public void getWeather(String key, String city, String province) {
        if (!isViewAttached()) {
            return;
        }

        model.getWeather(key, city, province)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weather) throws Exception {
                        mView.setData(weather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
