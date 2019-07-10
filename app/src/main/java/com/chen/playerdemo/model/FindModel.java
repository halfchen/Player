package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.FindBean;
import com.chen.playerdemo.contract.FindContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-31
 **/
public class FindModel implements FindContract.Model {

    @Override
    public Flowable<FindBean> getFind() {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getFind();
    }
}
