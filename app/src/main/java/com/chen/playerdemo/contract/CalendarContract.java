package com.chen.playerdemo.contract;

import com.chen.playerdemo.base.BaseView;
import com.chen.playerdemo.bean.tools.CalendarData;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public interface CalendarContract {

    interface Model{

        Flowable<CalendarData> getCalendar(String key, String date);
    }

    interface View extends BaseView{

        void setData(CalendarData data);
    }

    interface Presenter{

        void getCalendar(String key, String date);
    }
}
