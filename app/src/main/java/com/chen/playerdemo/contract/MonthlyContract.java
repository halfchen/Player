package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.AllRec;

import io.reactivex.Flowable;

public interface MonthlyContract {

    interface Model {

        Flowable<AllRec> getMonthly(int start);
    }

    interface View extends BaseView {

        void setData(AllRec allRec);
    }

    interface Presenter {

        void getMonthly(int start);
    }
}
