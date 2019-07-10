package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.RecommendContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-29
 **/
public class RecommendModel implements RecommendContract.Model {

    @Override
    public Flowable<AllRec> getAllRec(int page) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getAllRec(page);
    }
}
