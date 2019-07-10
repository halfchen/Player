package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.gank.Welfare;

import io.reactivex.Flowable;

public interface WelfareContract {

    interface Model {

        Flowable<Welfare> getWelfare(int page);
    }

    interface View extends BaseView {

        void setData(Welfare welfare);
    }

    interface Presenter {

        void getWelfare(int page);
    }
}
