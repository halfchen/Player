package com.chen.playerdemo.view.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.SectionsPagerAdapter;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.view.activity.MainActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {

    @BindView(R.id.viewpaget)
    public ViewPager mViewPager;

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        TabLayout tabs = mainActivity.tabs;
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        mViewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(mViewPager);
        tabs.getTabAt(1).select();
    }
}
