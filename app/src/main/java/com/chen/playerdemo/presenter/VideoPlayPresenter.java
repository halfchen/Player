package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.VideoPlayContract;
import com.chen.playerdemo.model.VideoPlayModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-20
 **/
public class VideoPlayPresenter extends BasePresenter<VideoPlayContract.View> implements VideoPlayContract.Presenter {

    private VideoPlayContract.Model model;

    public VideoPlayPresenter() {
        model = new VideoPlayModel();
    }

    @Override
    public void getRelated(String id) {
        if (!isViewAttached()) {
            return;
        }
        model.getRelated(id)
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
