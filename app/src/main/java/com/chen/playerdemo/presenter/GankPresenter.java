package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankContract;
import com.chen.playerdemo.model.GankModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

public class GankPresenter extends BasePresenter<GankContract.View> implements GankContract.Presenter {

    private GankContract.Model model;

    public GankPresenter() {
        model = new GankModel();
    }

    @Override
    public void getGankData(String type, int count, int page) {
        if (!isViewAttached()) {
            return;
        }

        mView.showLoading();
        model.getGankData(type, count, page)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<GankBean>() {
                    @Override
                    public void accept(GankBean gankBean) throws Exception {
                        mView.hideLoading();
                        mView.setData(gankBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }
}
