package com.chen.playerdemo.presenter;

import android.util.Log;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.RecommendContract;
import com.chen.playerdemo.model.RecommendModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-5-29
 **/
public class RecommendPresenter extends BasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private RecommendContract.Model model;

    public RecommendPresenter() {
        model = new RecommendModel();
    }

    @Override
    public void getAllRec(int page) {
        if (!isViewAttached()) {
            return;
        }
        model.getAllRec(page)
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
                        Log.e("====", throwable.toString());

                    }
                });
    }
}
