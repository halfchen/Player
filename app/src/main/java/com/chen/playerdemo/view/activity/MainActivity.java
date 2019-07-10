package com.chen.playerdemo.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
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

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private VideoFragment mVideoFragment;
    private MusicFragment mMusicFragment;
    private TvFragment mTvFragment;
    private WelfareFragment mWelfareFragment;

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
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        mVideoFragment = VideoFragment.newInstance();
        mMusicFragment = MusicFragment.newInstance();
        mTvFragment = TvFragment.newInstance();
        mWelfareFragment = WelfareFragment.newInstance();

        transaction.replace(R.id.fragment, mVideoFragment);
        transaction.commit();
    }

    @OnClick({R.id.search, R.id.rank})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.search:
                jumpActivity(Constants.PATH_SEARCH);
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
        transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();
        if (id == R.id.nav_video) {
            mTvTitle.setVisibility(View.GONE);
            mIvRank.setVisibility(View.VISIBLE);
            mIvSearch.setVisibility(View.VISIBLE);
            tabs.setVisibility(View.VISIBLE);
            if (mVideoFragment == null) {
                mVideoFragment = new VideoFragment();
            }
            transaction.replace(R.id.fragment, mVideoFragment);
        } else if (id == R.id.nav_music) {
            mTvTitle.setText("MUSIC");
            mTvTitle.setVisibility(View.VISIBLE);
            mIvRank.setVisibility(View.INVISIBLE);
            mIvSearch.setVisibility(View.INVISIBLE);
            tabs.setVisibility(View.GONE);
            if (mMusicFragment == null) {
                mMusicFragment = new MusicFragment();
            }
            transaction.replace(R.id.fragment, mMusicFragment);
        } else if (id == R.id.nav_tv) {
            mTvTitle.setText("TV");
            mTvTitle.setVisibility(View.VISIBLE);
            mIvRank.setVisibility(View.INVISIBLE);
            mIvSearch.setVisibility(View.INVISIBLE);
            tabs.setVisibility(View.GONE);
            if (mTvFragment == null) {
                mTvFragment = new TvFragment();
            }
            transaction.replace(R.id.fragment, mTvFragment);
        } else if (id == R.id.nav_welfare) {
            mTvTitle.setText("福利");
            mTvTitle.setVisibility(View.VISIBLE);
            mIvRank.setVisibility(View.INVISIBLE);
            mIvSearch.setVisibility(View.INVISIBLE);
            tabs.setVisibility(View.GONE);
            if (mWelfareFragment == null) {
                mWelfareFragment = new WelfareFragment();
            }
            transaction.replace(R.id.fragment, mWelfareFragment);
        } else if (id == R.id.nav_share) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_setting) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_mine) {
            ToastUtils.show("暂未开发");
        } else if (id == R.id.nav_about) {
            jumpToWeb();
        }

        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void jumpToWeb() {
        ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString("url", "https://github.com/halfchen")
                .withString("title", "关于")
                .navigation();
    }
}
