package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.VideoPlayContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-20
 **/
public class VideoPlayModel implements VideoPlayContract.Model {

    @Override
    public Flowable<AllRec> getRelated(String id) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getRelated(id);
    }
}
