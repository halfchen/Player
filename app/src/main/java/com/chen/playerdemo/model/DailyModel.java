package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.DailyContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

public class DailyModel implements DailyContract.Model {

    @Override
    public Flowable<AllRec> getDaily(String date) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getDaily(date, 2);
    }
}
