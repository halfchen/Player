package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.ArticleData;
import com.chen.playerdemo.bean.tools.CategoryData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public interface WechatArticleContract {

    interface Model{

        Flowable<CategoryData> getCategory(String key);

        Flowable<ArticleData> getArticle(String key, String cid, int page, int size);
    }

    interface View extends BaseView{

        void setCategory(CategoryData data);

        void setArticle(ArticleData data);
    }

    interface Presenter{

        void getCategory(String key);

        void getArticle(String key, String cid, int page, int size);
    }
}
