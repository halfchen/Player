package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListInfo;
import com.chen.playerdemo.contract.MusicContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public class MusicModel implements MusicContract.Model {
    @Override
    public Flowable<BannerInfo> requestBanner() {
        return RetrofitClient.getInstance().getApi(Constants.BASE_URL).requestBanner();
    }

    @Override
    public Flowable<HighQuality> requestHighQuality() {
        return RetrofitClient.getInstance().getApi(Constants.BASE_URL).requestHighQuality();
    }

    @Override
    public Flowable<HighQuality> requestPlayList() {
        return RetrofitClient.getInstance().getApi(Constants.BASE_URL).requestPlayList();
    }

    @Override
    public Flowable<PlayListInfo> requestPersonalized() {
        return RetrofitClient.getInstance().getApi(Constants.BASE_URL).requestPersonalized();
    }
}
