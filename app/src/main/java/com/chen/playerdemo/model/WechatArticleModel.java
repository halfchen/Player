package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.ArticleData;
import com.chen.playerdemo.bean.tools.CategoryData;
import com.chen.playerdemo.contract.WechatArticleContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class WechatArticleModel implements WechatArticleContract.Model {
    @Override
    public Flowable<CategoryData> getCategory(String key) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getCategory(key);
    }

    @Override
    public Flowable<ArticleData> getArticle(String key, String cid, int page, int size) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getArticle(key, cid, page, size);
    }
}
