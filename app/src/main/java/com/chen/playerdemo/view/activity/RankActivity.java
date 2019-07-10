package com.chen.playerdemo.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.view.fragment.RankPagerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_RANK)
public class RankActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initView() {
        RankPagerAdapter rankPagerAdapter = new RankPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(rankPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.back, R.id.search})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.search) {
            jumpActivity(Constants.PATH_SEARCH);
        }
    }
}