package com.chen.playerdemo;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.utils.ToastUtils;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.notification.NotificationConstructor;

/**
 * Created by chenbin
 * 2019-7-2
 **/
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        ToastUtils.init(getApplicationContext());
        MusicManager.initMusicManager(this);
        //配置通知栏
        NotificationConstructor constructor = new NotificationConstructor.Builder()
                .setCreateSystemNotification(false)
                .bulid();
        MusicManager.getInstance().setNotificationConstructor(constructor);
    }

    public static Context getContext() {
        return mContext;
    }
}
