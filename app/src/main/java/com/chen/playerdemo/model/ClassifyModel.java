package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.video.CategoriesBean;
import com.chen.playerdemo.contract.ClassifyContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class ClassifyModel implements ClassifyContract.Model {

    @Override
    public Flowable<CategoriesBean> getCategories() {
        return RetrofitClient.getInstance().getApi(Constants.VideoUrl.base_url).getCategories();
    }
}
