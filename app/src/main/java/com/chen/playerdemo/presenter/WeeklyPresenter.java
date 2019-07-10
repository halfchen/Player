package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.WeeklyContract;
import com.chen.playerdemo.model.WeeklyModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class WeeklyPresenter extends BasePresenter<WeeklyContract.View> implements WeeklyContract.Presenter {

    private WeeklyContract.Model model;

    public WeeklyPresenter() {
        model = new WeeklyModel();
    }

    @Override
    public void getWeekly(int start) {
        if (!isViewAttached()) {
            return;
        }

        model.getWeekly(start)
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
