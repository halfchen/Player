package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.HistoryData;
import com.chen.playerdemo.contract.MobHistoryContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class MobHistoryModel implements MobHistoryContract.Model {
    @Override
    public Flowable<HistoryData> getHistory(String key, String day) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getHistory(key, day);
    }
}
