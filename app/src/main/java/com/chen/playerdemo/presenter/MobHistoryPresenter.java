package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.HistoryData;
import com.chen.playerdemo.contract.MobHistoryContract;
import com.chen.playerdemo.model.MobHistoryModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class MobHistoryPresenter extends BasePresenter<MobHistoryContract.View> implements MobHistoryContract.Presenter {

    private MobHistoryContract.Model model;

    public MobHistoryPresenter() {
        model = new MobHistoryModel();
    }

    @Override
    public void getHistory(String key, String day) {
        if (!isViewAttached()) {
            return;
        }

        model.getHistory(key, day)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<HistoryData>() {
                    @Override
                    public void accept(HistoryData historyData) throws Exception {
                        mView.setData(historyData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
