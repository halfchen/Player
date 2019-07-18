package com.chen.playerdemo.view.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.ToolsData;
import com.chen.playerdemo.utils.DimensUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工具
 */
@Route(path = Constants.PATH_TOOLS)
public class ToolsActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<ToolsData> mList = new ArrayList<>();
    private CommonRecyclerAdapter<ToolsData> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tools;
    }

    @Override
    protected void initView() {
        mList.clear();
        mList.add(new ToolsData(R.mipmap.ic_weather, "天气"));
        mList.add(new ToolsData(R.mipmap.ic_history, "历史上的今天"));
        mList.add(new ToolsData(R.mipmap.ic_calendar, "万年历"));
        mList.add(new ToolsData(R.mipmap.ic_mobile, "手机号查询"));
        mList.add(new ToolsData(R.mipmap.ic_choiceness, "微信精选"));
        mList.add(new ToolsData(R.mipmap.ic_dictionaries, "新华字典"));
        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<ToolsData>(this, mList, R.layout.adapter_tools_item) {
                @Override
                public void convert(ViewHolder holder, ToolsData item, int position) {
                    holder.setText(R.id.name, item.getName());
                    holder.setImageResource(R.id.image, item.getResId());
                    LinearLayout linearLayout = holder.getView(R.id.ll_bg);
                    if (position == 0) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_01);
                    } else if (position == 1) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_03);
                    } else if (position == 2) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_04);
                    } else if (position == 3) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_02);
                    } else if (position == 4) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_05);
                    } else if (position == 5) {
                        linearLayout.setBackgroundResource(R.drawable.tv_shape_06);
                    }
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (position == 0) {
                            } else if (position == 1) {
                                jumpActivity(Constants.PATH_HISTORY);
                            } else if (position == 2) {
                                jumpActivity(Constants.PATH_CALENDAR);
                            } else if (position == 3) {
                                jumpActivity(Constants.PATH_MOBILE);
                            } else if (position == 4) {
                                jumpActivity(Constants.PATH_WX_ARTICLE);
                            } else if (position == 5) {
                            }
                        }
                    });
                }
            };
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position < 2) {
                    outRect.top = DimensUtils.dp2px(ToolsActivity.this, 15);
                } else {
                    outRect.top = DimensUtils.dp2px(ToolsActivity.this, 6);
                }
                if (position % 2 == 0) {
                    outRect.left = DimensUtils.dp2px(ToolsActivity.this, 15);
                    outRect.right = DimensUtils.dp2px(ToolsActivity.this, 3);
                } else {
                    outRect.left = DimensUtils.dp2px(ToolsActivity.this, 3);
                    outRect.right = DimensUtils.dp2px(ToolsActivity.this, 15);
                }
            }
        });
    }

    @OnClick(R.id.back)
    public void click(View view) {
        finish();
    }
}
