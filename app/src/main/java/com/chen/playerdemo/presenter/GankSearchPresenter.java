package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankSearchContract;
import com.chen.playerdemo.model.GankSearchModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-16
 **/
public class GankSearchPresenter extends BasePresenter<GankSearchContract.View> implements GankSearchContract.Presenter {

    private GankSearchContract.Model model;

    public GankSearchPresenter() {
        model = new GankSearchModel();
    }

    @Override
    public void getSearch(String input, String type, int page) {
        if (!isViewAttached()) {
            return;
        }

        model.getSearch(input, type, page)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<GankBean>() {
                    @Override
                    public void accept(GankBean gankBean) throws Exception {
                        mView.setData(gankBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
