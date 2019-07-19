package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.DictionaryData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public interface DictionaryContract {

    interface Model {

        Flowable<DictionaryData> getDictionary(String key, String name);
    }

    interface View extends BaseView {

        void setData(DictionaryData data);
    }

    interface Presenter {

        void getDictionary(String key, String name);
    }
}
