package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.FindBean;
import com.chen.playerdemo.contract.FindContract;
import com.chen.playerdemo.model.FindModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-5-31
 **/
public class FindPresenter extends BasePresenter<FindContract.View> implements FindContract.Presenter {

    private FindContract.Model model;

    public FindPresenter() {
        model = new FindModel();
    }

    @Override
    public void getFind() {
        if (!isViewAttached()) {
            return;
        }
        model.getFind()
                .compose(RxScheduler.<FindBean>Flo_io_main())
                .as(mView.<FindBean>bindAutoDispose())
                .subscribe(new Consumer<FindBean>() {
                    @Override
                    public void accept(FindBean allRec) throws Exception {
                        mView.setData(allRec);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
