package com.chen.playerdemo.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.MultiTypeSupport;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.tv.TVData;
import com.chen.playerdemo.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        mList.clear();
        String tvInfo = Utils.getFromAssets(getContext(), "live.txt");
        Type type = new TypeToken<List<TVData>>() {
        }.getType();
        mList = gson.fromJson(tvInfo, type);
        initAdapter();
    }

    private void initAdapter() {
        MultiTypeSupport multiTypeSupport = new MultiTypeSupport() {

            @Override
            public int getLayoutId(Object item, int position) {
                if (mList.get(position).getType().equals("title")) {
                    return R.layout.item_tv_title;
                } else {
                    return R.layout.item_tv;
                }
            }
        };
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<TVData>(getContext(), mList, multiTypeSupport) {
                @Override
                public void convert(ViewHolder holder, TVData item, int position) {
                    if (mList.get(position).getType().equals("title")) {
                        holder.setText(R.id.name, item.getName());
                    } else {
                        RelativeLayout rl_bg = holder.getView(R.id.rl_bg);
                        if (position % 7 == 0) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_01);
                        } else if (position % 7 == 1) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_02);
                        } else if (position % 7 == 2) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_03);
                        } else if (position % 7 == 3) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_04);
                        } else if (position % 7 == 4) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_05);
                        } else if (position % 7 == 5) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_06);
                        } else if (position % 7 == 6) {
                            rl_bg.setBackgroundResource(R.drawable.tv_shape_07);
                        }
                        holder.setText(R.id.name, item.getName());
                        holder.setOnIntemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance()
                                        .build(Constants.PATH_TV)
                                        .withString("url", item.getLive())
                                        .navigation();
                            }
                        });
                    }
                }
            };
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (mList.get(i).getType().equals("title")) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
