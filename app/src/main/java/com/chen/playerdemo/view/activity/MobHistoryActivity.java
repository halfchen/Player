package com.chen.playerdemo.view.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.tools.HistoryData;
import com.chen.playerdemo.contract.MobHistoryContract;
import com.chen.playerdemo.presenter.MobHistoryPresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.widget.ExpandableTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 历史上的今天
 */
@Route(path = Constants.PATH_HISTORY)
public class MobHistoryActivity extends BaseActivity<MobHistoryPresenter> implements MobHistoryContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView mTitle;

    private List<HistoryData.ResultBean> mList = new ArrayList<>();
    private CommonRecyclerAdapter<HistoryData.ResultBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mob_history;
    }

    @Override
    protected void initView() {
        mPresenter = new MobHistoryPresenter();
        mPresenter.attachView(this);

        String day = TimeUtils.getMobHistoryDate(new Date());
        mTitle.setText("历史上的今天(" + day + ")");

        mPresenter.getHistory(Constants.Mob.Mob_key, day);

        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<HistoryData.ResultBean>(this, mList, R.layout.adapter_mobhistory_item) {
                @Override
                public void convert(ViewHolder holder, HistoryData.ResultBean item, int position) {
                    TextView title = holder.getView(R.id.title);
                    title.setText(item.getTitle());
                    title.setSelected(true);
                    holder.setText(R.id.date, item.getDate().substring(0,4));
                    ExpandableTextView textView = holder.getView(R.id.event);
                    textView.setText(item.getEvent(), position);
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position < 1) {
                    outRect.top = DimensUtils.dp2px(MobHistoryActivity.this, 15);
                } else if (position == mList.size() - 1) {
                    outRect.top = DimensUtils.dp2px(MobHistoryActivity.this, 6);
                    outRect.bottom = DimensUtils.dp2px(MobHistoryActivity.this, 15);
                } else {
                    outRect.top = DimensUtils.dp2px(MobHistoryActivity.this, 6);
                }
                outRect.left = DimensUtils.dp2px(MobHistoryActivity.this, 15);
                outRect.right = DimensUtils.dp2px(MobHistoryActivity.this, 15);
            }
        });
    }

    @OnClick(R.id.back)
    public void click(View view) {
        finish();
    }

    @Override
    public void setData(HistoryData data) {
        if (data != null) {
            mList.addAll(data.getResult());
            adapter.notifyDataSetChanged();
        }
    }
}
