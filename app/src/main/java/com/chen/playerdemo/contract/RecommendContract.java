package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-5-29
 **/
public interface RecommendContract {

    interface Model {
        Flowable<AllRec> getAllRec(int page);
    }

    interface View extends BaseView {

        void setData(AllRec allRec);
    }

    interface Presenter {

        void getAllRec(int page);
    }
}
