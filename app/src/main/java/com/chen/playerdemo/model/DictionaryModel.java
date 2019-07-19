package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.DictionaryData;
import com.chen.playerdemo.contract.DictionaryContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public class DictionaryModel implements DictionaryContract.Model {
    @Override
    public Flowable<DictionaryData> getDictionary(String key, String name) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getDictionary(key, name);
    }
}
