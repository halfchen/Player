package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.music.PlayListDetail;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public interface MusicListContract {

    interface Model {
        Flowable<PlayListDetail> requestPlayListDetail(String id);
    }

    interface View extends BaseView {

        void setDetail(PlayListDetail playListDetail);
    }

    interface Presenter {

        void requestPlayListDetail(String id);
    }
}
