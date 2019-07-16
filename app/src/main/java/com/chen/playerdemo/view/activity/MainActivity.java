package com.chen.playerdemo.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.utils.OnClickUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.view.fragment.GankFragment;
import com.chen.playerdemo.view.fragment.MusicFragment;
import com.chen.playerdemo.view.fragment.TvFragment;
import com.chen.playerdemo.view.fragment.VideoFragment;
import com.chen.playerdemo.view.fragment.WelfareFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_MAIN)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tabs)
    public TabLayout tabs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.search)
    ImageView mIvSearch;
    @BindView(R.id.rank)
    ImageView mIvRank;

    private VideoFragment mVideoFragment;
    private MusicFragment mMusicFragment;
    private TvFragment mTvFragment;
    private GankFragment mGankFragment;
    private WelfareFragment mWelfareFragment;
    private int nowPage = 1;// 1:视频首页  2:Gank

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        // 得到Fragment管理器以及事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mVideoFragment == null) {
            mVideoFragment = VideoFragment.newInstance();
            transaction.add(R.id.fragment, mVideoFragment);
        } else {
            transaction.show(mVideoFragment);
        }
        transaction.commit();
    }

    @OnClick({R.id.search, R.id.rank})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.search:
                if (nowPage == 1) {
                    jumpActivity(Constants.PATH_SEARCH);
                } else if (nowPage == 2) {
                    jumpActivity(Constants.PATH_GANK_SEARCH);
                }
                break;
            case R.id.rank:
                jumpActivity(Constants.PATH_RANK);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (OnClickUtils.isOnDoubleClick()) {
                moveTaskToBack(false);
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            } else {
                ToastUtils.show(getResources().getString(R.string.home_exit_hint));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();
        if (id == R.id.nav_video) {
            nowPage = 1;
            hideFragments(transaction, "", View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
                transaction.add(R.id.fragment, mVideoFragment);
            } else {
                transaction.show(mVideoFragment);
            }
        } else if (id == R.id.nav_music) {
            hideFragments(transaction, "MUSIC", View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.GONE);
            if (mMusicFragment == null) {
                mMusicFragment = new MusicFragment();
                transaction.add(R.id.fragment, mMusicFragment);
            } else {
                transaction.show(mMusicFragment);
            }
        } else if (id == R.id.nav_tv) {
            hideFragments(transaction, "TV", View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.GONE);
            if (mTvFragment == null) {
                mTvFragment = new TvFragment();
                transaction.add(R.id.fragment, mTvFragment);
            } else {
                transaction.show(mTvFragment);
            }
        } else if (id == R.id.nav_gank) {
            nowPage = 2;
            hideFragments(transaction, "GANK", View.VISIBLE, View.INVISIBLE, View.VISIBLE, View.GONE);
            if (mGankFragment == null) {
                mGankFragment = new GankFragment();
                transaction.add(R.id.fragment, mGankFragment);
            } else {
                transaction.show(mGankFragment);
            }
        } else if (id == R.id.nav_welfare) {
            hideFragments(transaction, "福利", View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.GONE);
            if (mWelfareFragment == null) {
                mWelfareFragment = new WelfareFragment();
                transaction.add(R.id.fragment, mWelfareFragment);
            } else {
                transaction.show(mWelfareFragment);
            }
        } else if (id == R.id.nav_share) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_setting) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_mine) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_about) {
            jumpToWeb("https://github.com/halfchen");
        }

        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFragments(FragmentTransaction transaction, String title, int titleVB, int ivRankVB, int ivSearchVB, int tabsVB) {
        mTvTitle.setText(title);
        mTvTitle.setVisibility(titleVB);
        mIvRank.setVisibility(ivRankVB);
        mIvSearch.setVisibility(ivSearchVB);
        tabs.setVisibility(tabsVB);
        if (mVideoFragment != null) {
            transaction.hide(mVideoFragment);
        }
        if (mMusicFragment != null) {
            transaction.hide(mMusicFragment);
        }
        if (mTvFragment != null) {
            transaction.hide(mTvFragment);
        }
        if (mGankFragment != null) {
            transaction.hide(mGankFragment);
        }
        if (mWelfareFragment != null) {
            transaction.hide(mWelfareFragment);
        }
    }

    public void jumpToWeb(String webUrl) {
        ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString("url", webUrl)
                .withString("title", "关于")
                .navigation();
    }
}
