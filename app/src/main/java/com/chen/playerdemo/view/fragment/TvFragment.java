package com.chen.playerdemo.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.tv.TVData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<TVData> mList = new ArrayList<>();
    private CommonRecyclerAdapter<TVData> adapter;

    public TvFragment() {
        // Required empty public constructor
    }

    public static TvFragment newInstance() {
        return new TvFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tv;
    }

    @Override
    protected void initView(View view) {
        mList.clear();
        mList.add(new TVData("CCTV1", Constants.TV.CCTV1, R.drawable.tv_shape_01));
        mList.add(new TVData("CCTV3", Constants.TV.CCTV3, R.drawable.tv_shape_02));
        mList.add(new TVData("CCTV5", Constants.TV.CCTV5, R.drawable.tv_shape_03));
        mList.add(new TVData("CCTV6", Constants.TV.CCTV6, R.drawable.tv_shape_04));
        mList.add(new TVData("湖南卫视", Constants.TV.HNTV, R.drawable.tv_shape_05));
        mList.add(new TVData("香港卫视", Constants.TV.HKS, R.drawable.tv_shape_06));
        mList.add(new TVData("香港财经", Constants.TV.BSTV, R.drawable.tv_shape_07));
        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<TVData>(getContext(), mList, R.layout.item_tv) {
                @Override
                public void convert(ViewHolder holder, TVData item, int position) {
                    RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
                    rl_bg.setBackgroundResource(item.getColorId());
                    holder.setText(R.id.name, item.getName());
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(Constants.PATH_TV)
                                    .withString("url", item.getUrl())
                                    .navigation();
                        }
                    });
                }
            };
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
