package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.SearchContract;
import com.chen.playerdemo.model.SearchModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-21
 **/
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private SearchContract.Model model;

    public SearchPresenter() {
        model = new SearchModel();
    }

    @Override
    public void getSearch(int start, String query) {
        if (!isViewAttached()) {
            return;
        }

        model.getSearch(start, query)
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
