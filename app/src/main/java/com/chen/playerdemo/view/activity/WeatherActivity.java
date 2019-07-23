package com.chen.playerdemo.view.activity;

import android.support.design.animation.ArgbEvaluatorCompat;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
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
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.ArcProgressView;
import com.chen.playerdemo.widget.dialog.AnyLayer;
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
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.temperature)
    TextView mTvTemperature;
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.weather)
    TextView mTvWeather;
    @BindView(R.id.wind)
    TextView mTvWind;
    @BindView(R.id.humidity)
    TextView mTvHumidity;
    @BindView(R.id.date)
    TextView mTvDate;
    @BindView(R.id.arc_progress)
    ArcProgressView mArcProgressView;

    private String cityName;
    private String provinceName;
    private AnyLayer anyLayer;

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
        showLoading();
        LocationUtils.getLocation(this, new LocationUtils.OnLocationListener() {
            @Override
            public void onSuccess(AMapLocation aMapLocation) {
                //获取城市
                cityName = aMapLocation.getCity();
                if (cityName.endsWith("市")) {
                    cityName = cityName.substring(0, cityName.length() - 1);
                }
                provinceName = aMapLocation.getProvince();
                if (provinceName.endsWith("省") || provinceName.endsWith("市")) {
                    provinceName = provinceName.substring(0, provinceName.length() - 1);
                }
                mTvTitle.setText(cityName);
                mPresenter.getWeather(Constants.Mob.Mob_key, cityName, provinceName);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                hideLoading();
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
            mTvWeather.setText(weather.getWeather());
            mTvWind.setText(weather.getWind());
            mTvHumidity.setText(weather.getHumidity());
            mTvDate.setText(weather.getDate() + " " + weather.getWeek());
            if (!StringUtils.isEmpty(weather.getPollutionIndex())) {
                mArcProgressView.setCurrentCount(500, Integer.parseInt(weather.getPollutionIndex()));
            }
            String hour = TimeUtils.getHour(new Date());
            int[] darkColor;
            if (TimeUtils.timeCompare(hour, weather.getSunset())) {//大于日落时间，夜间图片
                darkColor = ImageUtils.newInstance().loadPickColor(R.mipmap.weather_bg_night, iv_background);
                ImageUtils.newInstance().load(R.mipmap.weather_bg_night, banner);
            } else {
                darkColor = ImageUtils.newInstance().loadPickColor(R.mipmap.weather_bg_day, iv_background);
                ImageUtils.newInstance().load(R.mipmap.weather_bg_day, banner);
            }

            appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    if (i < -280) {
                        mToolbar.setBackgroundColor(darkColor[0]);
                        StatusBarUtil.setWindowStatusBarColor(WeatherActivity.this, darkColor[0]);
                    } else {
                        float fraction = (float) Math.abs(i) / 280;
                        Integer barColor = ArgbEvaluatorCompat.getInstance().evaluate(fraction, getResources().getColor(R.color.transparent), darkColor[0]);
                        StatusBarUtil.setWindowStatusBarColor(WeatherActivity.this, barColor);
                        mToolbar.setBackgroundColor(barColor);
                    }
                }
            });
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

    @Override
    public void showLoading() {
        anyLayer = AnyLayer.with(this);
        anyLayer.contentView(R.layout.dialog_loading)
                .backgroundColorRes(R.color.dialog_bg)
                .cancelableOnTouchOutside(false)
                .cancelableOnClickKeyBack(true)
                .show();
    }

    @Override
    public void hideLoading() {
        anyLayer.dismiss();
    }
}
