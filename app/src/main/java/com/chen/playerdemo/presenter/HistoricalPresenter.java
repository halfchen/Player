package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.HistoricalContract;
import com.chen.playerdemo.model.HistoricalModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class HistoricalPresenter extends BasePresenter<HistoricalContract.View> implements HistoricalContract.Presenter {

    private HistoricalContract.Model model;

    public HistoricalPresenter() {
        model = new HistoricalModel();
    }

    @Override
    public void getHistorical(int start) {
        if (!isViewAttached()) {
            return;
        }
        model.getHistorical(start)
                .compose(RxScheduler.<AllRec>Flo_io_main())
                .as(mView.<AllRec>bindAutoDispose())
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
