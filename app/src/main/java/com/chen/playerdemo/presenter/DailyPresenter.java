package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.DailyContract;
import com.chen.playerdemo.model.DailyModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter {

    DailyContract.Model model;

    public DailyPresenter() {
        model = new DailyModel();
    }

    @Override
    public void getDaily(String date) {
        if (!isViewAttached()) {
            return;
        }
        model.getDaily(date)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<AllRec>() {
                    @Override
                    public void accept(AllRec allRec) throws Exception {
                        mView.setData(allRec);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
