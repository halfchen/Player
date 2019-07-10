package com.chen.playerdemo.contract;


import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.video.CategoriesBean;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public interface ClassifyContract {

    interface Model {

        Flowable<CategoriesBean> getCategories();
    }

    interface View extends BaseView {

        void setData(CategoriesBean bean);
    }

    interface Presenter {

        void getCategories();
    }
}
