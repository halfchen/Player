package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.MobileData;
import com.chen.playerdemo.contract.MobileContract;
import com.chen.playerdemo.model.MobileModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class MobilePresenter extends BasePresenter<MobileContract.View> implements MobileContract.Presenter {

    private MobileContract.Model model;

    public MobilePresenter() {
        model = new MobileModel();
    }

    @Override
    public void searchMobile(String key, String phone) {
        if (!isViewAttached()) {
            return;
        }

        model.searchMobile(key, phone)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<MobileData>() {
                    @Override
                    public void accept(MobileData mobileData) throws Exception {
                        mView.setData(mobileData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
