package com.chen.playerdemo.view.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.contract.GankSearchContract;
import com.chen.playerdemo.presenter.GankSearchPresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.dialog.AnimHelper;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Gank搜索
 */
@Route(path = Constants.PATH_GANK_SEARCH)
public class GankSearchActivity extends BaseActivity<GankSearchPresenter> implements GankSearchContract.View {

    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.type)
    TextView mTvType;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private String value;
    private List<GankBean.ResultsBean> mList = new ArrayList<>();
    private CommonRecyclerAdapter<GankBean.ResultsBean> adapter;
    private int page = 1;
    private String[] mTypes = {"全部", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App"};
    private String type = "all";
    private String selectType = "全部";

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_search;
    }

    @Override
    protected void initView() {
        mPresenter = new GankSearchPresenter();
        mPresenter.attachView(this);

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value = s.toString();
                if (StringUtils.isEmpty(value)) {
                    clear.setVisibility(View.GONE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtils.isEmpty(value)) {
                        ToastUtils.show("请输入您想要搜索的内容");
                        return true;
                    }
                    //  下面就是大家的业务逻辑
                    page = 1;
                    mPresenter.getSearch(value, type, page);
                    hideKeyboard(GankSearchActivity.this, mEdtSearch);
                    return true;
                }
                return false;
            }
        });

        initAdapter();

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                mPresenter.getSearch(value, type, page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getSearch(value, type, page);
            }
        });
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<GankBean.ResultsBean>(this, mList, R.layout.item_gank_view) {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.left = DimensUtils.dp2px(GankSearchActivity.this, 15);
                outRect.right = DimensUtils.dp2px(GankSearchActivity.this, 15);
                if (position == 0) {
                    outRect.top = DimensUtils.dp2px(GankSearchActivity.this, 15);
                } else if (position == mList.size() - 1) {
                    outRect.bottom = DimensUtils.dp2px(GankSearchActivity.this, 15);
                } else {
                    outRect.top = DimensUtils.dp2px(GankSearchActivity.this, 8);
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.clear, R.id.type})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.clear) {
            mEdtSearch.setText("");
        } else if (i == R.id.type) {
            AnyLayer anyLayer = AnyLayer.target(ll_search);
            anyLayer.direction(AnyLayer.Direction.BOTTOM)
                    .contentView(R.layout.dialog_gank_search_type)
                    .backgroundColorRes(R.color.dialog_bg)
                    .gravity(Gravity.TOP | Gravity.RIGHT)
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
                    .setLinearAdapter(R.id.recyclerViewDialog, new CommonRecyclerAdapter<String>(GankSearchActivity.this, Arrays.asList(mTypes), R.layout.adapter_gank_search_type_item) {
                        @Override
                        public void convert(ViewHolder holder, String item, int position) {
                            if (selectType.equals(item)) {
                                holder.setBackground(R.id.tv_stage, getResources().getColor(R.color.orange));
                            } else {
                                holder.setBackground(R.id.tv_stage, getResources().getColor(R.color.bg_f4));
                            }
                            holder.setText(R.id.tv_stage, item);
                            holder.setOnIntemClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectType = item;
                                    mTvType.setText(selectType);
                                    if (item.equals("全部")) {
                                        type = "all";
                                    } else {
                                        type = item;
                                    }
                                    anyLayer.dismiss();
                                }
                            });
                        }
                    })
                    .show();
        }
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

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
