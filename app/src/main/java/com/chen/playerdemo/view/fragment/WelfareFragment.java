package com.chen.playerdemo.view.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankContract;
import com.chen.playerdemo.presenter.GankPresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.view.activity.ImageBrowserActivity;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.chen.playerdemo.widget.photoview.PreviewPositionListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelfareFragment extends BaseFragment<GankPresenter> implements GankContract.View, PreviewPositionListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ArrayList<String> mList = new ArrayList<>();
    private CommonRecyclerAdapter<String> adapter;
    private int page = 1;
    private AnyLayer anyLayer;

    private String mType = "福利";
    private int count = 20;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public WelfareFragment() {
        // Required empty public constructor
    }

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new GankPresenter();
        mPresenter.attachView(this);
        initAdapter();

        mPresenter.getGankData(mType, count, page);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getGankData(mType, count, page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getGankData(mType, count, page);
            }
        });
    }

    @Override
    public void showLoading() {
        anyLayer = AnyLayer.with(getContext());
        anyLayer.contentView(R.layout.dialog_loading)
                .backgroundColorRes(R.color.dialog_bg)
                .cancelableOnTouchOutside(false)
                .cancelableOnClickKeyBack(true)
                .show();
    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
        anyLayer.dismiss();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<String>(getContext(), mList, R.layout.item_welfare) {
                @Override
                public void convert(ViewHolder holder, String item, int position) {
                    ImageView ivWelfare = holder.getView(R.id.ivWelfare);
                    ImageUtils.newInstance().load(item, ivWelfare);
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageBrowserActivity.showImageBrowser(getContext(), v, position, mList, WelfareFragment.this);
                        }
                    });
                }
            };
        }
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setData(GankBean gankBean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (gankBean != null) {
            if (page == 1) {
                mList.clear();
                for (GankBean.ResultsBean bean : gankBean.getResults()) {
                    mList.add(bean.getUrl());
                }
                adapter.notifyDataSetChanged();
            } else {
                int start = mList.size();
                for (GankBean.ResultsBean bean : gankBean.getResults()) {
                    mList.add(bean.getUrl());
                }
                adapter.notifyItemRangeInserted(start, mList.size());
            }
        }
    }

    @Override
    public void scrollToPosition(int position) {
        staggeredGridLayoutManager.scrollToPositionWithOffset(position, 20);
    }
}
