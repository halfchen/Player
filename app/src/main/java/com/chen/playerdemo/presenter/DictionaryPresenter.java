package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.DictionaryData;
import com.chen.playerdemo.contract.DictionaryContract;
import com.chen.playerdemo.model.DictionaryModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-19
 **/
public class DictionaryPresenter extends BasePresenter<DictionaryContract.View> implements DictionaryContract.Presenter {

    private DictionaryContract.Model model;

    public DictionaryPresenter() {
        model = new DictionaryModel();
    }

    @Override
    public void getDictionary(String key, String name) {
        if (!isViewAttached()) {
            return;
        }

        model.getDictionary(key, name)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<DictionaryData>() {
                    @Override
                    public void accept(DictionaryData dictionaryData) throws Exception {
                        mView.setData(dictionaryData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
