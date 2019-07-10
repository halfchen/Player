package com.chen.playerdemo.view.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.MultiTypeSupport;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.MonthlyContract;
import com.chen.playerdemo.presenter.MonthlyPresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 月排行
 */
public class MonthlyFragment extends BaseFragment<MonthlyPresenter> implements MonthlyContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<AllRec.ItemListBeanX> mList = new ArrayList<>();
    private CommonRecyclerAdapter<AllRec.ItemListBeanX> adapter;
    private int start = 0;

    public static MonthlyFragment newInstance() {
        return new MonthlyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monthly;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new MonthlyPresenter();
        mPresenter.attachView(this);
        initAdapter();
        mPresenter.getMonthly(start);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                start += 10;
                mPresenter.getMonthly(start);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                start = 0;
                mPresenter.getMonthly(start);
            }
        });
    }

    private void initAdapter() {
        MultiTypeSupport multiTypeSupport = new MultiTypeSupport() {
            @Override
            public int getLayoutId(Object item, int position) {
                if (mList.get(position).getData() != null) {
                    if (mList.get(position).getData().getDataType().equals(Constants.DataType.Banner)) {
                        return R.layout.item_banner;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.HorizontalScrollCard)) {
                        return R.layout.item_horizontal_scroll_card;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.ItemCollection)) {
                        if (mList.get(position).getType().equals(Constants.DataType.squareCardCollection)) {
                            return R.layout.allrec_item_collection;
                        } else {
                            return R.layout.find_item_collection;
                        }
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.VideoBeanForClient)) {
                        return R.layout.item_follow_card;
//                        return R.layout.item_video_bean_for_client;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.TextCardWithRightAndLeftTitle)) {
                        return R.layout.item_text_card_with_right_and_left_title;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.TagBriefCard)) {
                        return R.layout.item_tag_brief_card;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.FollowCard)) {
                        return R.layout.item_follow_card;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.AutoPlayVideoAdDetail)) {
                        return R.layout.item_follow_card;
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.TextCard)) {
                        if (mList.get(position).getData().getType().contains(Constants.DataType.footer)) {
                            return R.layout.item_text_card_with_tagid;
                        } else {
                            return R.layout.item_text_card;
                        }
                    } else if (mList.get(position).getData().getDataType().equals(Constants.DataType.TextCardWithTagId)) {
                        return R.layout.item_text_card_with_tagid;
                    } else {
                        return R.layout.item_horizontal_scroll_card;
                    }
                } else {
                    return R.layout.item_horizontal_scroll_card;
                }
            }
        };

        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX>(getContext(), mList, multiTypeSupport) {
                @Override
                public void convert(ViewHolder holder, AllRec.ItemListBeanX item, int position) {
                    if (item.getData().getDataType().equals(Constants.DataType.Banner)) {
                        ImageUtils.newInstance().load(item.getData().getImage(), holder.getView(R.id.banner));
                    } else if (item.getData().getDataType().equals(Constants.DataType.HorizontalScrollCard)) {
                        if (item.getData().getItemList().size() > 0) {
                            RecyclerView itemRecyclerView = holder.getView(R.id.item_recyclerView);
                            int index = item.getData().getItemList().size();
                            CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean> itemAdapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean>(getContext(), item.getData().getItemList(), R.layout.item_banner_padding) {
                                @Override
                                public void convert(ViewHolder holder, AllRec.ItemListBeanX.DataBeanXX.ItemListBean item, int position) {
                                    ViewGroup.MarginLayoutParams params = null;
                                    if (holder.getView(R.id.llBanner).getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                                        params = (ViewGroup.MarginLayoutParams) holder.getView(R.id.llBanner).getLayoutParams();
                                    } else {
                                        params = new ViewGroup.MarginLayoutParams(holder.getView(R.id.llBanner).getLayoutParams());
                                    }
                                    if (position == 0) {
                                        params.leftMargin = DimensUtils.dp2px(getContext(), 15);
                                    } else if (position == (index - 1)) {
                                        params.leftMargin = DimensUtils.dp2px(getContext(), 5);
                                        params.rightMargin = DimensUtils.dp2px(getContext(), 15);
                                    } else {
                                        params.leftMargin = DimensUtils.dp2px(getContext(), 5);
                                    }
                                    holder.getView(R.id.llBanner).setLayoutParams(params);
                                    if (item.getData() != null) {
                                        ImageUtils.newInstance().load(item.getData().getImage(), holder.getView(R.id.banner));
                                    }
                                    if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null && !StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                        holder.setOnIntemClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                jumpVideoPlay(item.getData().getContent().getData().getPlayUrl(), item.getData().getContent().getData().getTitle(), item.getData().getContent().getData().getId());
                                            }
                                        });
                                    }
                                }
                            };
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            LinearSnapHelper snapHelper = new LinearSnapHelper();
                            itemRecyclerView.setOnFlingListener(null);
                            snapHelper.attachToRecyclerView(itemRecyclerView);
                            itemRecyclerView.setLayoutManager(linearLayoutManager);
                            itemRecyclerView.setAdapter(itemAdapter);
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.ItemCollection)) {
                        if (item.getType().equals(Constants.DataType.squareCardCollection)) {//推荐
                            if (item.getData().getHeader() != null) {
                                holder.setText(R.id.subTitle, item.getData().getHeader().getSubTitle());
                                holder.setText(R.id.title, item.getData().getHeader().getTitle());
                                holder.setText(R.id.rightText, item.getData().getHeader().getRightText());
                            }
                            if (item.getData().getItemList().size() > 0) {
                                RecyclerView itemRecyclerView = holder.getView(R.id.item_recyclerView);
                                int index = item.getData().getItemList().size();
                                CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean> itemAdapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean>(getContext(), item.getData().getItemList(), R.layout.item_follow_card2) {
                                    @Override
                                    public void convert(ViewHolder holder, AllRec.ItemListBeanX.DataBeanXX.ItemListBean item, int position) {
                                        ViewGroup.MarginLayoutParams params = null;
                                        if (holder.getView(R.id.llFollowCard).getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                                            params = (ViewGroup.MarginLayoutParams) holder.getView(R.id.llFollowCard).getLayoutParams();
                                        } else {
                                            params = new ViewGroup.MarginLayoutParams(holder.getView(R.id.llFollowCard).getLayoutParams());
                                        }
                                        if (position == 0) {
                                            params.leftMargin = DimensUtils.dp2px(getContext(), 15);
                                        } else if (position == (index - 1)) {
                                            params.leftMargin = DimensUtils.dp2px(getContext(), 5);
                                            params.rightMargin = DimensUtils.dp2px(getContext(), 15);
                                        } else {
                                            params.leftMargin = DimensUtils.dp2px(getContext(), 5);
                                        }
                                        holder.getView(R.id.llFollowCard).setLayoutParams(params);
                                        if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null) {
                                            if (item.getData().getContent().getData().getCover() != null) {
                                                ImageUtils.newInstance().load(item.getData().getContent().getData().getCover().getDetail(), holder.getView(R.id.detail));
                                            }
                                            if (item.getData().getContent().getData().getAuthor() != null) {
                                                ImageUtils.newInstance().load(item.getData().getContent().getData().getAuthor().getIcon(), holder.getView(R.id.icon));
                                            }
                                            holder.setText(R.id.title, item.getData().getContent().getData().getTitle());
                                            holder.setText(R.id.slogan, item.getData().getContent().getData().getSlogan());
                                            holder.setText(R.id.duration, TimeUtils.formatVideoTime((long) item.getData().getContent().getData().getDuration()));
                                            if (!StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                                holder.setOnIntemClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        jumpVideoPlay(item.getData().getContent().getData().getPlayUrl(), item.getData().getContent().getData().getTitle(), item.getData().getContent().getData().getId());
                                                    }
                                                });
                                            }
                                        }
                                    }
                                };
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                LinearSnapHelper snapHelper = new LinearSnapHelper();
                                itemRecyclerView.setOnFlingListener(null);
                                snapHelper.attachToRecyclerView(itemRecyclerView);
                                itemRecyclerView.setLayoutManager(linearLayoutManager);
                                itemRecyclerView.setAdapter(itemAdapter);
                            }
                        } else if (item.getType().equals(Constants.DataType.specialSquareCardCollection)) {//发现热门分类
                            if (item.getData().getHeader() != null) {
                                holder.setText(R.id.title, item.getData().getHeader().getTitle());
                                holder.setText(R.id.rightText, item.getData().getHeader().getRightText());
                            }
                            if (item.getData().getItemList().size() > 0) {
                                RecyclerView itemRecyclerView = holder.getView(R.id.item_recyclerView);
                                CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean> itemAdapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean>(getContext(), item.getData().getItemList(), R.layout.item_square_card) {
                                    @Override
                                    public void convert(ViewHolder holder, AllRec.ItemListBeanX.DataBeanXX.ItemListBean item, int position) {
                                        if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null) {
                                            if (item.getData().getContent().getData().getCover() != null) {
                                                ImageUtils.newInstance().load(item.getData().getImage(), holder.getView(R.id.image));
                                            }
                                            holder.setText(R.id.title, item.getData().getTitle());
                                            if (!StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                                holder.setOnIntemClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        jumpVideoPlay(item.getData().getContent().getData().getPlayUrl(), item.getData().getContent().getData().getTitle(), item.getData().getContent().getData().getId());
                                                    }
                                                });
                                            }
                                        }
                                    }
                                };
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                itemRecyclerView.setLayoutManager(gridLayoutManager);
                                itemRecyclerView.setAdapter(itemAdapter);
                            }
                        } else {//发现专题策划
                            if (item.getData().getHeader() != null) {
                                holder.setText(R.id.title, item.getData().getHeader().getTitle());
                                holder.setText(R.id.rightText, item.getData().getHeader().getRightText());
                            }
                            if (item.getData().getItemList().size() > 0) {
                                RecyclerView itemRecyclerView = holder.getView(R.id.item_recyclerView);
                                CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean> itemAdapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX.DataBeanXX.ItemListBean>(getContext(), item.getData().getItemList(), R.layout.item_square_card2) {
                                    @Override
                                    public void convert(ViewHolder holder, AllRec.ItemListBeanX.DataBeanXX.ItemListBean item, int position) {
                                        if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null) {
                                            if (item.getData().getContent().getData().getCover() != null) {
                                                ImageUtils.newInstance().load(item.getData().getImage(), holder.getView(R.id.image));
                                            }
                                            holder.setText(R.id.title, item.getData().getTitle());
                                            if (!StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                                holder.setOnIntemClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        jumpVideoPlay(item.getData().getContent().getData().getPlayUrl(), item.getData().getContent().getData().getTitle(), item.getData().getContent().getData().getId());
                                                    }
                                                });
                                            }
                                        }
                                    }
                                };
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                itemRecyclerView.setLayoutManager(gridLayoutManager);
                                itemRecyclerView.setAdapter(itemAdapter);
                            }
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.VideoBeanForClient)) {
                        if (item.getData() != null) {
                            if (item.getData().getCover() != null) {
                                ImageUtils.newInstance().load(item.getData().getCover().getDetail(), holder.getView(R.id.detail));
                            }
                            holder.setText(R.id.title, item.getData().getTitle());
                            holder.setText(R.id.slogan, "#" + item.getData().getCategory());
                            holder.setText(R.id.duration, TimeUtils.formatVideoTime((long) item.getData().getDuration()));
                            if (item.getData().getAuthor() != null) {
                                ImageUtils.newInstance().load(item.getData().getAuthor().getIcon(), holder.getView(R.id.icon));
                            }
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.TextCardWithRightAndLeftTitle)) {
                        holder.setText(R.id.text, item.getData().getText());
                        holder.setText(R.id.rightText, item.getData().getRightText());
                    } else if (item.getData().getDataType().equals(Constants.DataType.TagBriefCard)) {
                        ImageUtils.newInstance().load(item.getData().getIcon(), holder.getView(R.id.icon));
                        holder.setText(R.id.title, item.getData().getTitle());
                        holder.setText(R.id.description, item.getData().getDescription());
                    } else if (item.getData().getDataType().equals(Constants.DataType.AutoPlayVideoAdDetail)) {
                        if (item.getData() != null && item.getData().getDetail() != null) {
                            ImageUtils.newInstance().load(item.getData().getDetail().getImageUrl(), holder.getView(R.id.detail));
                            ImageUtils.newInstance().load(item.getData().getDetail().getIcon(), holder.getView(R.id.icon));
                            holder.setText(R.id.title, item.getData().getDetail().getTitle());
                            holder.setText(R.id.slogan, item.getData().getDetail().getDescription());
                            holder.setText(R.id.duration, "#广告");
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.FollowCard)) {
                        if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null) {
                            if (item.getData().getContent().getData().getCover() != null) {
                                ImageUtils.newInstance().load(item.getData().getContent().getData().getCover().getDetail(), holder.getView(R.id.detail));
                            }
                            if (item.getData().getContent().getData().getAuthor() != null) {
                                ImageUtils.newInstance().load(item.getData().getContent().getData().getAuthor().getIcon(), holder.getView(R.id.icon));
                            } else if (item.getData().getContent().getData().getOwner() != null) {
                                ImageUtils.newInstance().load(item.getData().getContent().getData().getOwner().getAvatar(), holder.getView(R.id.icon));
                                holder.setText(R.id.slogan, item.getData().getContent().getData().getOwner().getNickname());
                            }
                            if (StringUtils.isEmpty(item.getData().getContent().getData().getTitle())) {
                                holder.setText(R.id.title, item.getData().getContent().getData().getDescription());
                            } else {
                                holder.setText(R.id.title, item.getData().getContent().getData().getTitle());
                            }
                            if (!StringUtils.isEmpty(item.getData().getContent().getData().getSlogan())) {
                                holder.setText(R.id.slogan, item.getData().getContent().getData().getSlogan());
                            } else if (!StringUtils.isEmpty(item.getData().getContent().getData().getCategory())) {
                                holder.setText(R.id.slogan, item.getData().getContent().getData().getCategory());
                            }
                            if (item.getData().getContent().getData().getDuration() != 0) {
                                holder.setText(R.id.duration, TimeUtils.formatVideoTime((long) item.getData().getContent().getData().getDuration()));
                            }
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.TextCard)) {
                        if (item.getData() != null) {
                            holder.setText(R.id.text, item.getData().getText());
                        }
                    } else if (item.getData().getDataType().equals(Constants.DataType.TextCardWithTagId)) {
                        holder.setText(R.id.text, item.getText());
                    }
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null && !StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                jumpVideoPlay(item.getData().getContent().getData().getPlayUrl(), item.getData().getContent().getData().getTitle(), item.getData().getContent().getData().getId());
                            } else if (item.getData() != null && !StringUtils.isEmpty(item.getData().getPlayUrl())) {
                                jumpVideoPlay(item.getData().getPlayUrl(), item.getData().getTitle(), item.getData().getId());
                            } else if (item.getData() != null && !StringUtils.isEmpty(item.getData().getActionUrl())) {
                                if (item.getData().getActionUrl().contains("http")) {
                                    String actionUrl = item.getData().getActionUrl();
                                    jump2WebActivity(Utils.decode(actionUrl.substring(actionUrl.indexOf("&url=") + 5)));
                                }
                            }
                        }
                    });
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void jumpVideoPlay(String url, String title, String id) {
        ARouter.getInstance()
                .build(Constants.PATH_VIDEO_PLAY)
                .withString("url", url)
                .withString("title", title)
                .withString("id", id)
                .navigation();
    }

    private void jump2WebActivity(String url) {
        ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString("url", url)
                .navigation();
    }

    @Override
    public void setData(AllRec allRec) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (allRec != null) {
            if (start == 0) {
                mList.clear();
                mList.addAll(allRec.getItemList());
            } else {
                if (allRec.getItemList().size() == 0) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                mList.addAll(allRec.getItemList());
            }
            adapter.notifyDataSetChanged();
        }
    }
}