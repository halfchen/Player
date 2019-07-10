package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.gank.Welfare;
import com.chen.playerdemo.contract.WelfareContract;
import com.chen.playerdemo.model.WelfareModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

public class WelfarePresenter extends BasePresenter<WelfareContract.View> implements WelfareContract.Presenter {

    private WelfareContract.Model model;

    public WelfarePresenter() {
        model = new WelfareModel();
    }

    @Override
    public void getWelfare(int page) {
        if (!isViewAttached()) {
            return;
        }

        mView.showLoading();
        model.getWelfare(page)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Welfare>() {
                    @Override
                    public void accept(Welfare welfare) throws Exception {
                        mView.hideLoading();
                        mView.setData(welfare);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }
}
