package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListInfo;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public interface MusicContract {

    interface Model {
        Flowable<BannerInfo> requestBanner();

        Flowable<HighQuality> requestHighQuality();

        Flowable<HighQuality> requestPlayList();

        Flowable<PlayListInfo> requestPersonalized();
    }

    interface View extends BaseView {
        void setBanner(BannerInfo banner);

        void setHighData(HighQuality highData);

        void setPlayListData(HighQuality highData);

        void setPersonalData(PlayListInfo personalData);
    }

    interface Presenter {

        void requestBanner();

        void requestHighQuality();

        void requestPlayList();

        void requestPersonalized();
    }
}
