package com.chen.playerdemo.view.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.WeatherData;
import com.chen.playerdemo.contract.WeatherContract;
import com.chen.playerdemo.presenter.WeatherPresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.LocationUtils;
import com.chen.playerdemo.utils.StatusBarUtil;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_WEATHER)
public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherContract.View, OnPermission {

    @BindView(R.id.iv_background)
    ImageView iv_background;
    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.temperature)
    TextView mTvTemperature;

    private String cityName;
    private String provinceName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView() {
        mPresenter = new WeatherPresenter();
        mPresenter.attachView(this);
        StatusBarUtil.setImmersiveStatusBar(this);
        StatusBarUtil.setImmersiveStatusBarToolbar(mToolbar, this);

        String hour = TimeUtils.getHour(new Date());

        ImageUtils.newInstance().loadPickColor(R.mipmap.weather_bg_day, iv_background);

        if (XXPermissions.isHasPermission(this, Permission.Group.LOCATION)) {
            requestLocationInfo();
        } else {
            requestFilePermission();
        }
    }

    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.LOCATION)
                .request(this);
    }

    private void requestLocationInfo() {
        LocationUtils.getLocation(this, new LocationUtils.OnLocationListener() {
            @Override
            public void onSuccess(AMapLocation aMapLocation) {
                Log.e("======", aMapLocation.toString());
                //获取城市
                cityName = aMapLocation.getCity();
                if (cityName.endsWith("市")) {
                    cityName = cityName.substring(0, cityName.length() - 1);
                }
                provinceName = aMapLocation.getProvince();
                if (provinceName.endsWith("省") || provinceName.endsWith("市")) {
                    provinceName = provinceName.substring(0, provinceName.length() - 1);
                }
                mPresenter.getWeather(Constants.Mob.Mob_key, cityName, provinceName);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void setData(WeatherData data) {
        if (data != null && data.getResult().size() > 0) {
            WeatherData.ResultBean weather = data.getResult().get(0);
            mTvTemperature.setText(weather.getTemperature());
        }
    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
        if (isAll) {
            requestLocationInfo();
        }
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限，请手动授予权限");
            XXPermissions.gotoPermissionSettings(this, true);
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

    @OnClick(R.id.back)
    public void click(View view) {
        finish();
    }
}
