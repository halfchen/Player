package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.music.PlayListDetail;
import com.chen.playerdemo.contract.MusicListContract;
import com.chen.playerdemo.model.MusicListModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public class MusicListPresenter extends BasePresenter<MusicListContract.View> implements MusicListContract.Presenter {

    private MusicListContract.Model model;

    public MusicListPresenter() {
        model = new MusicListModel();
    }

    @Override
    public void requestPlayListDetail(String id) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.requestPlayListDetail(id)
                .compose(RxScheduler.<PlayListDetail>Flo_io_main())
                .as(mView.<PlayListDetail>bindAutoDispose())
                .subscribe(new Consumer<PlayListDetail>() {
                    @Override
                    public void accept(PlayListDetail playListDetail) throws Exception {
                        mView.hideLoading();
                        mView.setDetail(playListDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }
}
