package com.chen.playerdemo.view.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.video.CategoriesBean;
import com.chen.playerdemo.contract.ClassifyContract;
import com.chen.playerdemo.presenter.ClassifyPresenter;
import com.chen.playerdemo.utils.DimensUtils;
import com.chen.playerdemo.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全部分类
 */
@Route(path = Constants.PATH_CLASSIFY)
public class ClassifyActivity extends BaseActivity<ClassifyPresenter> implements ClassifyContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<CategoriesBean.ItemListBean> mList = new ArrayList<>();
    private CommonRecyclerAdapter<CategoriesBean.ItemListBean> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_classify;
    }

    @Override
    protected void initView() {
        mPresenter = new ClassifyPresenter();
        mPresenter.attachView(this);

        initAdapter();
        mPresenter.getCategories();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<CategoriesBean.ItemListBean>(this, mList, R.layout.item_square_card2) {
                @Override
                public void convert(ViewHolder holder, CategoriesBean.ItemListBean item, int position) {
                    if (item.getData() != null) {
                        ImageUtils.newInstance().load(item.getData().getImage(), holder.getView(R.id.image));
                        holder.setText(R.id.title, item.getData().getTitle());

                        holder.setOnIntemClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item.getData().getActionUrl().contains("ranklist")) {
                                    ARouter.getInstance()
                                            .build(Constants.PATH_RANK)
                                            .navigation();
                                }
                            }
                        });
                    }
                }
            };
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = DimensUtils.dp2px(ClassifyActivity.this, 3);
                int position = parent.getChildAdapterPosition(view);
                if (position % 2 == 0) {
                    outRect.left = DimensUtils.dp2px(ClassifyActivity.this, 15);
                } else {
                    outRect.left = DimensUtils.dp2px(ClassifyActivity.this, 3);
                    outRect.right = DimensUtils.dp2px(ClassifyActivity.this, 15);
                }
            }
        });
    }

    @Override
    public void setData(CategoriesBean bean) {
        mList.clear();
        if (bean != null) {
            mList.addAll(bean.getItemList());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
