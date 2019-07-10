package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.FindBean;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-29
 **/
public interface FindContract {

    interface Model {
        Flowable<FindBean> getFind();
    }

    interface View extends BaseView {

        void setData(FindBean allRec);
    }

    interface Presenter {

        void getFind();
    }
}
