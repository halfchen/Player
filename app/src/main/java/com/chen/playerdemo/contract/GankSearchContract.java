package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-21
 **/
public interface GankSearchContract {

    interface Model {

        Flowable<GankBean> getSearch(String input, String type, int page);
    }

    interface View extends BaseView {

        void setData(GankBean data);
    }

    interface Presenter {

        void getSearch(String input, String type, int page);
    }
}
