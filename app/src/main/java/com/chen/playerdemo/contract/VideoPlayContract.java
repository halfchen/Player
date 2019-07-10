package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-20
 **/
public interface VideoPlayContract {

    interface Model {

        Flowable<AllRec> getRelated(String id);
    }

    interface View extends BaseView {

        void setData(AllRec data);
    }

    interface Presenter {

        void getRelated(String id);
    }
}
