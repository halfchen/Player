package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankSearchContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-16
 **/
public class GankSearchModel implements GankSearchContract.Model {
    @Override
    public Flowable<GankBean> getSearch(String input, String type, int page) {
        return RetrofitClient.getInstance().getApi(Constants.GankUrl.base).gankSearch(input, type, page);
    }
}
