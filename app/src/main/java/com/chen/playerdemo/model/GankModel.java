package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

public class GankModel implements GankContract.Model {
    @Override
    public Flowable<GankBean> getGankData(String type, int count, int page) {
        return RetrofitClient.getInstance().getApi(Constants.GankUrl.base).getGankData(type, count, page);
    }
}
