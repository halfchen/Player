package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.gank.GankBean;

import io.reactivex.Flowable;

public interface GankContract {

    interface Model {

        Flowable<GankBean> getGankData(String type, int count, int page);
    }

    interface View extends BaseView {

        void setData(GankBean gankBean);
    }

    interface Presenter {

        void getGankData(String type, int count, int page);
    }
}
