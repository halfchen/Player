package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.MonthlyContract;
import com.chen.playerdemo.model.MonthlyModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class MonthlyPresenter extends BasePresenter<MonthlyContract.View> implements MonthlyContract.Presenter {

    private MonthlyContract.Model model;

    public MonthlyPresenter() {
        model = new MonthlyModel();
    }

    @Override
    public void getMonthly(int start) {
        if (!isViewAttached()) {
            return;
        }
        model.getMonthly(start)
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
