package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.gank.Welfare;
import com.chen.playerdemo.contract.WelfareContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

public class WelfareModel implements WelfareContract.Model {
    @Override
    public Flowable<Welfare> getWelfare(int page) {
        return RetrofitClient.getInstance().getApi(Constants.GankUrl.base).getWelfare();
    }
}
