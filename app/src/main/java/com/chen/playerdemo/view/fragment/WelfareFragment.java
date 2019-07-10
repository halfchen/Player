package com.chen.playerdemo.view.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.gank.Welfare;
import com.chen.playerdemo.contract.WelfareContract;
import com.chen.playerdemo.presenter.WelfarePresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelfareFragment extends BaseFragment<WelfarePresenter> implements WelfareContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ArrayList<String> mList = new ArrayList<>();
    private CommonRecyclerAdapter<String> adapter;
    private int page = 1;
    //    private int width;
    private AnyLayer anyLayer;

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
        mPresenter = new WelfarePresenter();
        mPresenter.attachView(this);
//        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
//        width = screenWidth - Utils.dpToPx(26, getContext());
        initAdapter();

        mPresenter.getWelfare(page);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getWelfare(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getWelfare(page);
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
                            PhotoViewer.INSTANCE
                                    .setData(mList)
                                    .setCurrentPage(position)
                                    .setImgContainer(recyclerView)
                                    .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
                                        @Override
                                        public void show(ImageView iv, String url) {
                                            ImageUtils.newInstance().load(url, iv);
                                        }
                                    })
                                    .setOnLongClickListener(new com.wanglu.photoviewerlibrary.OnLongClickListener() {
                                        @Override
                                        public void onLongClick(View view) {

                                        }
                                    })
                                    .start(WelfareFragment.this);
                        }
                    });
                }
            };
        }

//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

//        int spanCount = 2;
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                int[] first = new int[spanCount];
//                gridLayoutManager.findFirstCompletelyVisibleItemPositions(first);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
//                    gridLayoutManager.invalidateSpanAssignments();
//                }
//            }
//        });
    }

    @Override
    public void setData(Welfare welfare) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (welfare != null) {
            if (page == 1) {
                mList.clear();
                for (Welfare.ResultsBean bean : welfare.getResults()) {
                    mList.add(bean.getUrl());
                }
            } else {
                for (Welfare.ResultsBean bean : welfare.getResults()) {
                    mList.add(bean.getUrl());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
