package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.WeeklyContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class WeeklyModel implements WeeklyContract.Model {

    @Override
    public Flowable<AllRec> getWeekly(int start) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getWeekly(start, 10);
    }
}
