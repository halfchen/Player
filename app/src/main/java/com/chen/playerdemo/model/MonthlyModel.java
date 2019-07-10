package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.MonthlyContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class MonthlyModel implements MonthlyContract.Model {

    @Override
    public Flowable<AllRec> getMonthly(int start) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getMonthly(start, 10);
    }
}
