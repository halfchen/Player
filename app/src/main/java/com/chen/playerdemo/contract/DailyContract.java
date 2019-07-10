package com.chen.playerdemo.contract;


import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

public interface DailyContract {

    interface Model {

        Flowable<AllRec> getDaily(String date);
    }

    interface View extends BaseView {

        void setData(AllRec allRec);
    }

    interface Presenter {

        void getDaily(String date);
    }
}
