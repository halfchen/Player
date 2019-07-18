package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.ArticleData;
import com.chen.playerdemo.bean.tools.CategoryData;
import com.chen.playerdemo.contract.WechatArticleContract;
import com.chen.playerdemo.model.WechatArticleModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class WechatArticlePresenter extends BasePresenter<WechatArticleContract.View> implements WechatArticleContract.Presenter {

    private WechatArticleContract.Model model;

    public WechatArticlePresenter() {
        model = new WechatArticleModel();
    }

    @Override
    public void getCategory(String key) {
        if (!isViewAttached()) {
            return;
        }

        model.getCategory(key)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<CategoryData>() {
                    @Override
                    public void accept(CategoryData categoryData) throws Exception {
                        mView.setCategory(categoryData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void getArticle(String key, String cid, int page, int size) {
        if (!isViewAttached()) {
            return;
        }

        model.getArticle(key, cid, page, size)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<ArticleData>() {
                    @Override
                    public void accept(ArticleData articleData) throws Exception {
                        mView.setArticle(articleData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
