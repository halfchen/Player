package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListInfo;
import com.chen.playerdemo.contract.MusicContract;
import com.chen.playerdemo.model.MusicModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public class MusicPresenter extends BasePresenter<MusicContract.View> implements MusicContract.Presenter {

    private MusicContract.Model model;

    public MusicPresenter() {
        model = new MusicModel();
    }

    @Override
    public void requestBanner() {
        if (!isViewAttached()) {
            return;
        }
        model.requestBanner()
                .compose(RxScheduler.<BannerInfo>Flo_io_main())
                .as(mView.<BannerInfo>bindAutoDispose())
                .subscribe(new Consumer<BannerInfo>() {
                    @Override
                    public void accept(BannerInfo bannerInfo) throws Exception {
                        mView.setBanner(bannerInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void requestHighQuality() {
        if (!isViewAttached()) {
            return;
        }
        model.requestHighQuality()
                .compose(RxScheduler.<HighQuality>Flo_io_main())
                .as(mView.<HighQuality>bindAutoDispose())
                .subscribe(new Consumer<HighQuality>() {
                    @Override
                    public void accept(HighQuality highQuality) throws Exception {
                        mView.setHighData(highQuality);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void requestPlayList() {
        if (!isViewAttached()) {
            return;
        }
        model.requestPlayList()
                .compose(RxScheduler.<HighQuality>Flo_io_main())
                .as(mView.<HighQuality>bindAutoDispose())
                .subscribe(new Consumer<HighQuality>() {
                    @Override
                    public void accept(HighQuality highQuality) throws Exception {
                        mView.setPlayListData(highQuality);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void requestPersonalized() {
        if (!isViewAttached()) {
            return;
        }
        model.requestPersonalized()
                .compose(RxScheduler.<PlayListInfo>Flo_io_main())
                .as(mView.<PlayListInfo>bindAutoDispose())
                .subscribe(new Consumer<PlayListInfo>() {
                    @Override
                    public void accept(PlayListInfo playListInfo) throws Exception {
                        mView.setPersonalData(playListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}
