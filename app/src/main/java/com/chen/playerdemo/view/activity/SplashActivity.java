package com.chen.playerdemo.view.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.splash.AdMessageBean;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.NetUtils;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity implements OnPermission {

    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.layout_skip)
    LinearLayout layoutSkip;
    @BindView(R.id.iv_advertising)
    ImageView ivAdvertising;

    int timeCount = 0;
    boolean continueCount = true;
    private int initTimeCount;
    private AdMessageBean mAdMessageBean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (continueCount) {
                countNum();
                handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
    }

    private int countNum() {//数秒
        initTimeCount = mAdMessageBean.getAdTime();
        timeCount++;
        if (timeCount == 3) {//数秒，超过3秒后如果没有网络，则进入下一个页面
            if (!NetUtils.isConnected(SplashActivity.this)) {
                continueCount = false;
                hasPermission(null, true);
            }
            if (StringUtils.isEmpty(mAdMessageBean.getAdPictureUrl())) {
                //如果无广告则进入下一页
                continueCount = false;
                hasPermission(null, true);
            } else {
                //如果有网络，则显示广告图片
                ivAdvertising.setVisibility(View.VISIBLE);
                layoutSkip.setVisibility(View.VISIBLE);
            }
        }
        tvSecond.setText((initTimeCount - timeCount) + " ");
        if (timeCount == initTimeCount) {
            continueCount = false;
            hasPermission(null, true);
        }
        return timeCount;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (NetUtils.isConnected(SplashActivity.this)) {
            double random = Math.random();
            if (random < 0.25) {
                mAdMessageBean = new AdMessageBean(6, "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg", "http://gank.io/api");
            } else if (random >= 0.25 && random < 0.5) {
                mAdMessageBean = new AdMessageBean(6, "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg", "https://github.com/halfchen");
            } else if (random >= 0.5 && random <= 0.75) {
                mAdMessageBean = new AdMessageBean(6, "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg", "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg");
            } else {
                mAdMessageBean = new AdMessageBean(6, "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg", "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg");
            }
            ImageUtils.newInstance().load(mAdMessageBean.getAdPictureUrl(), ivAdvertising);
        }
        if (XXPermissions.isHasPermission(this, Permission.Group.STORAGE) && XXPermissions.isHasPermission(this, Permission.READ_PHONE_STATE)) {
            layoutSkip.setVisibility(View.INVISIBLE);
            handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
        } else {
            layoutSkip.setVisibility(View.INVISIBLE);
            requestFilePermission();
        }
    }

    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.READ_PHONE_STATE)
//                .permission(Permission.REQUEST_INSTALL_PACKAGES)
                .request(this);
    }

    public void hasPermission(List<String> granted, boolean isAll) {
        if (isAll) {
            ARouter.getInstance()
                    .build(Constants.PATH_MAIN)
                    .navigation();
            handler.removeMessages(-1);
            finish();
        }
    }

    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限，请手动授予权限");
            XXPermissions.gotoPermissionSettings(SplashActivity.this, true);
        } else {
            ToastUtils.show("请先授予权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 500);
        }
    }

    @OnClick({R.id.iv_advertising, R.id.layout_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_advertising:
                String url = mAdMessageBean.getAdUrl();
                if (!StringUtils.isEmpty(url)) {
                    continueCount = false;
                    ARouter.getInstance()
                            .build(Constants.PATH_WEB)
                            .withString("title", "广告")
                            .withString("url", url)
                            .withString("from", "ad")
                            .navigation();
                    finish();
                }
                break;
            case R.id.layout_skip:
                continueCount = false;
                hasPermission(null, true);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }
}
