package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.MobileData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public interface MobileContract {

    interface Model {

        Flowable<MobileData> searchMobile(String key, String phone);
    }

    interface View extends BaseView {

        void setData(MobileData data);
    }

    interface Presenter {

        void searchMobile(String key, String phone);
    }
}
