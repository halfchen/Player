package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-21
 **/
public interface SearchContract {

    interface Model {

        Flowable<AllRec> getSearch(int start, String query);
    }

    interface View extends BaseView {

        void setData(AllRec data);
    }

    interface Presenter {

        void getSearch(int start, String query);
    }
}
