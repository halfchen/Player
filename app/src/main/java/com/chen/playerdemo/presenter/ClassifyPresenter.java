package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.video.CategoriesBean;
import com.chen.playerdemo.contract.ClassifyContract;
import com.chen.playerdemo.model.ClassifyModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-6-18
 **/
public class ClassifyPresenter extends BasePresenter<ClassifyContract.View> implements ClassifyContract.Presenter {

    private ClassifyContract.Model model;

    public ClassifyPresenter() {
        model = new ClassifyModel();
    }

    @Override
    public void getCategories() {
        if (!isViewAttached()) {
            return;
        }
        model.getCategories()
                .compose(RxScheduler.<CategoriesBean>Flo_io_main())
                .as(mView.<CategoriesBean>bindAutoDispose())
                .subscribe(new Consumer<CategoriesBean>() {
                    @Override
                    public void accept(CategoriesBean categoriesBean) throws Exception {
                        mView.setData(categoriesBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
