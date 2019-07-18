package com.chen.playerdemo.view.fragment;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankContract;
import com.chen.playerdemo.presenter.GankPresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends BaseFragment<GankPresenter> implements GankContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private ArrayList<GankBean.ResultsBean> mList = new ArrayList<>();
    private CommonRecyclerAdapter<GankBean.ResultsBean> adapter;
    private int page = 1;
    private AnyLayer anyLayer;

    private String mType = "all";
    private int count = 15;

    public GankFragment() {
        // Required empty public constructor
    }

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
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
            adapter = new CommonRecyclerAdapter<GankBean.ResultsBean>(getContext(), mList, R.layout.item_gank_view) {
                @Override
                public void convert(ViewHolder holder, GankBean.ResultsBean item, int position) {
                    ImageView imageView = holder.getView(R.id.image);
                    if (item.getImages().size() == 0) {
                        imageView.setVisibility(View.GONE);
                    } else {
                        imageView.setVisibility(View.VISIBLE);
                        ImageUtils.newInstance().load(item.getImages().get(0), imageView);
                    }
                    holder.setText(R.id.title, item.getDesc());
                    holder.setText(R.id.type, item.getType());
                    holder.setText(R.id.author, item.getWho());
                    holder.setText(R.id.time, item.getPublishedAt().substring(0, 10));
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(Constants.PATH_WEB)
                                    .withString("title", item.getDesc())
                                    .withString("url", item.getUrl())
                                    .navigation();
                        }
                    });
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.left = DimensUtils.dp2px(getContext(), 15);
                outRect.right = DimensUtils.dp2px(getContext(), 15);
                if (position == 0) {
                    outRect.top = DimensUtils.dp2px(getContext(), 15);
                } else if (position == mList.size() - 1) {
                    outRect.bottom = DimensUtils.dp2px(getContext(), 15);
                    outRect.top = DimensUtils.dp2px(getContext(), 8);
                } else {
                    outRect.top = DimensUtils.dp2px(getContext(), 8);
                }
            }
        });
    }

    @Override
    public void setData(GankBean gankBean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (gankBean != null) {
            if (page == 1) {
                mList.clear();
                mList.addAll(gankBean.getResults());
            } else {
                mList.addAll(gankBean.getResults());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
