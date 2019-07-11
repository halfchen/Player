package com.chen.playerdemo.view.fragment;


import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.gank.Welfare;
import com.chen.playerdemo.contract.WelfareContract;
import com.chen.playerdemo.download.DownloadManager;
import com.chen.playerdemo.download.OnDownloadListener;
import com.chen.playerdemo.presenter.WelfarePresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.OnClickUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanglu.photoviewerlibrary.OnSaveClickListener;
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
    private DownloadManager downloadManager;
    private ProgressDialog mProgressDialog;

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

        downloadManager = DownloadManager.getInstance();
        initProgressDialog();
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
                                    .setSaveClickListener(new OnSaveClickListener() {
                                        @Override
                                        public void onSaveClick(String url) {
                                            if (OnClickUtils.isOnDoubleClick()) {
                                                Log.e("====", "点击过快");
                                            } else {
                                                downloadImage(url);
                                            }
                                        }
                                    })
                                    .start(WelfareFragment.this);
                        }
                    });
                }
            };
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void downloadImage(String path) {
        mProgressDialog.show();
        downloadManager.startDownload(path, downloadListener);
    }

    /**
     * 下载进度
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext(), R.style.DialogTheme);
        mProgressDialog.setMessage("图片保存中，请稍后...");
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private void dismissProgressDialog() {
        mProgressDialog.setProgress(0);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private OnDownloadListener downloadListener = new OnDownloadListener() {
        @Override
        public void onException() {

        }

        @Override
        public void onProgress(int progress) {
            mProgressDialog.setProgress(progress);
        }

        @Override
        public void onSuccess() {
            ToastUtils.show("文件已保存在 Download/bukun 目录下");
            dismissProgressDialog();
        }

        @Override
        public void onFailed() {
            ToastUtils.show("下载失败");
            dismissProgressDialog();
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onCanceled() {
        }
    };

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
