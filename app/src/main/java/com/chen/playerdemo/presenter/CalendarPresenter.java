package com.chen.playerdemo.presenter;

import com.chen.playerdemo.base.BasePresenter;
import com.chen.playerdemo.bean.tools.CalendarData;
import com.chen.playerdemo.contract.CalendarContract;
import com.chen.playerdemo.model.CalendarModel;
import com.chen.playerdemo.network.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class CalendarPresenter extends BasePresenter<CalendarContract.View> implements CalendarContract.Presenter {

    private CalendarContract.Model model;

    public CalendarPresenter() {
        model = new CalendarModel();
    }

    @Override
    public void getCalendar(String key, String date) {
        if (!isViewAttached()) {
            return;
        }

        model.getCalendar(key, date)
                .compose(RxScheduler.Flo_io_main())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<CalendarData>() {
                    @Override
                    public void accept(CalendarData calendarData) throws Exception {
                        mView.setData(calendarData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
