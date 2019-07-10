package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

public interface HistoricalContract {

    interface Model {

        Flowable<AllRec> getHistorical(int start);
    }

    interface View extends BaseView {

        void setData(AllRec allRec);
    }

    interface Presenter {

        void getHistorical(int start);
    }
}
