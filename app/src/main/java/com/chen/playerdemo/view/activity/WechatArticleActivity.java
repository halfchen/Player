package com.chen.playerdemo.view.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.ArticleData;
import com.chen.playerdemo.bean.tools.CategoryData;
import com.chen.playerdemo.contract.WechatArticleContract;
import com.chen.playerdemo.presenter.WechatArticlePresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.widget.dialog.AnimHelper;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_WX_ARTICLE)
public class WechatArticleActivity extends BaseActivity<WechatArticlePresenter> implements WechatArticleContract.View {

    @BindView(R.id.cid)
    TextView mTvCid;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page = 1;
    private int size = 20;
    private String selectCid = "";
    private List<CategoryData.ResultBean> mCidList = new ArrayList<>();

    private List<ArticleData.ResultBean.ListBean> mArticleList = new ArrayList<>();
    private CommonRecyclerAdapter<ArticleData.ResultBean.ListBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wechat_article;
    }

    @Override
    protected void initView() {
        mPresenter = new WechatArticlePresenter();
        mPresenter.attachView(this);

        mPresenter.getCategory(Constants.Mob.Mob_key);

        initAdapter();

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!StringUtils.isEmpty(selectCid)) {
                    page++;
                    mPresenter.getArticle(Constants.Mob.Mob_key, selectCid, page, size);
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (!StringUtils.isEmpty(selectCid)) {
                    page = 1;
                    mPresenter.getArticle(Constants.Mob.Mob_key, selectCid, page, size);
                    recyclerView.scrollToPosition(0);
                    refreshLayout.setEnableLoadMore(true);
                } else {
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<ArticleData.ResultBean.ListBean>(this, mArticleList, R.layout.item_wechat_view) {
                @Override
                public void convert(ViewHolder holder, ArticleData.ResultBean.ListBean item, int position) {
                    ImageView imageView = holder.getView(R.id.image);
                    ImageUtils.newInstance().load(item.getThumbnails(), imageView);
                    holder.setText(R.id.title, item.getTitle());
                    holder.setText(R.id.time, item.getPubTime());
                    holder.setText(R.id.author, item.getSubTitle());
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(Constants.PATH_WEB)
                                    .withString("title", item.getTitle())
                                    .withString("url", item.getSourceUrl())
                                    .navigation();
                        }
                    });
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.left = DimensUtils.dp2px(WechatArticleActivity.this, 15);
                outRect.right = DimensUtils.dp2px(WechatArticleActivity.this, 15);
                if (position == 0) {
                    outRect.top = DimensUtils.dp2px(WechatArticleActivity.this, 15);
                } else if (position == mArticleList.size() - 1) {
                    outRect.bottom = DimensUtils.dp2px(WechatArticleActivity.this, 15);
                    outRect.top = DimensUtils.dp2px(WechatArticleActivity.this, 8);
                } else {
                    outRect.top = DimensUtils.dp2px(WechatArticleActivity.this, 8);
                }
            }
        });

    }

    @Override
    public void setCategory(CategoryData data) {
        if (data != null) {
            mCidList.clear();
            mCidList.addAll(data.getResult());
            showDialog();
        }
    }

    @Override
    public void setArticle(ArticleData data) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (data != null && data.getResult() != null) {
            if (page == 1) {
                mArticleList.clear();
                mArticleList.addAll(data.getResult().getList());
            } else {
                mArticleList.addAll(data.getResult().getList());
            }
            adapter.notifyDataSetChanged();
            if (data.getResult().getList().size() < 20) {
                refreshLayout.setEnableLoadMore(false);
            }
        }
    }

    @OnClick({R.id.back, R.id.cid})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cid:
                if (mCidList.size() > 0) {
                    showDialog();
                }
                break;
        }
    }

    private void showDialog() {
        AnyLayer anyLayer = AnyLayer.target(appbar);
        anyLayer.direction(AnyLayer.Direction.BOTTOM)
                .contentView(R.layout.dialog_wechat_cid)
                .backgroundColorRes(R.color.dialog_bg)
                .gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .contentAnim(new AnyLayer.IAnim() {
                    @Override
                    public long inAnim(View content) {
                        AnimHelper.startTopInAnim(content, Constants.ANIM_DURATION);
                        return Constants.ANIM_DURATION;
                    }

                    @Override
                    public long outAnim(View content) {
                        AnimHelper.startTopOutAnim(content, Constants.ANIM_DURATION);
                        return Constants.ANIM_DURATION;
                    }
                })
                .setGridAdapter(R.id.recyclerViewDialog, new CommonRecyclerAdapter<CategoryData.ResultBean>(WechatArticleActivity.this, mCidList, R.layout.adapter_gank_search_type_item) {
                    @Override
                    public void convert(ViewHolder holder, CategoryData.ResultBean item, int position) {
                        if (selectCid.equals(item.getCid())) {
                            holder.setBackground(R.id.tv_stage, getResources().getColor(R.color.orange));
                        } else {
                            holder.setBackground(R.id.tv_stage, getResources().getColor(R.color.bg_f4));
                        }
                        holder.setText(R.id.tv_stage, item.getName());
                        holder.setOnIntemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!selectCid.equals(item.getCid())) {
                                    selectCid = item.getCid();
                                    mTvCid.setText(item.getName());
                                    page = 1;
                                    mPresenter.getArticle(Constants.Mob.Mob_key, selectCid, page, size);
                                    recyclerView.scrollToPosition(0);
                                    refreshLayout.setEnableLoadMore(true);
                                }
                                anyLayer.dismiss();
                            }
                        });
                    }
                }, 4)
                .show();
    }
}
