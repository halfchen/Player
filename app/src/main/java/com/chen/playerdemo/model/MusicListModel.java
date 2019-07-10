package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.music.PlayListDetail;
import com.chen.playerdemo.contract.MusicListContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public class MusicListModel implements MusicListContract.Model {
    @Override
    public Flowable<PlayListDetail> requestPlayListDetail(String id) {
        return RetrofitClient.getInstance().getApi(Constants.BASE_URL).requestPlayListDetail(id);
    }
}
