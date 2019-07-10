package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.SearchContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-21
 **/
public class SearchModel implements SearchContract.Model {

    @Override
    public Flowable<AllRec> getSearch(int start, String query) {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getSearch(start, 10, query);
    }
}
