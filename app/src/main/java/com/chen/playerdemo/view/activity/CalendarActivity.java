package com.chen.playerdemo.view.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.CalendarData;
import com.chen.playerdemo.contract.CalendarContract;
import com.chen.playerdemo.presenter.CalendarPresenter;
import com.chen.playerdemo.utils.TimeUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 万年历
 */
@Route(path = Constants.PATH_CALENDAR)
public class CalendarActivity extends BaseActivity<CalendarPresenter> implements CalendarContract.View {

    @BindView(R.id.date)
    TextView mTvDate;
    @BindView(R.id.lunar)
    TextView mTvLunar;
    @BindView(R.id.lunarYear)
    TextView mTvLunarYear;
    @BindView(R.id.suit)
    TextView mTvSuit;
    @BindView(R.id.avoid)
    TextView mTvAvoid;

    private int dayNum = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initView() {
        mPresenter = new CalendarPresenter();
        mPresenter.attachView(this);
        mPresenter.getCalendar(Constants.Mob.Mob_key, TimeUtils.getOldDateByDay2(new Date(), dayNum));
    }

    @OnClick({R.id.back, R.id.before, R.id.after})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.before:
                dayNum -= 1;
                mPresenter.getCalendar(Constants.Mob.Mob_key, TimeUtils.getOldDateByDay2(new Date(), dayNum));
                break;
            case R.id.after:
                dayNum += 1;
                mPresenter.getCalendar(Constants.Mob.Mob_key, TimeUtils.getOldDateByDay2(new Date(), dayNum));
                break;
        }
    }

    @Override
    public void setData(CalendarData data) {
        if (data != null && data.getResult() != null) {
            mTvDate.setText(data.getResult().getDate());
            mTvLunar.setText(data.getResult().getLunar());
            mTvLunarYear.setText(data.getResult().getLunarYear() + "(" + data.getResult().getZodiac() + ")" + data.getResult().getWeekday());
            mTvSuit.setText(data.getResult().getSuit());
            mTvAvoid.setText(data.getResult().getAvoid());
        }
    }
}
