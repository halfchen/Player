package com.chen.playerdemo.model;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.tools.CalendarData;
import com.chen.playerdemo.contract.CalendarContract;
import com.chen.playerdemo.network.RetrofitClient;

import io.reactivex.Flowable;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class CalendarModel implements CalendarContract.Model {
    @Override
    public Flowable<CalendarData> getCalendar(String key, String date) {
        return RetrofitClient.getInstance().getApi(Constants.Mob.MOB_BASE).getCalendar(key, date);
    }
}
