package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.HistoryData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public interface MobHistoryContract {

    interface Model {
        Flowable<HistoryData> getHistory(String key, String day);
    }

    interface View extends BaseView {

        void setData(HistoryData data);
    }

    interface Presenter {

        void getHistory(String key, String day);
    }
}
