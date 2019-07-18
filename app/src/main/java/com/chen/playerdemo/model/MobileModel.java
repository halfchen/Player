package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.MobileData;
import com.chen.playerdemo.contract.MobileContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class MobileModel implements MobileContract.Model {
    @Override
    public Flowable<MobileData> searchMobile(String key, String phone) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getMobile(key, phone);
    }
}
