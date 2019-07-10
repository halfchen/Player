package com.chen.playerdemo.view.fragment;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseFragment;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListInfo;
import com.chen.playerdemo.contract.MusicContract;
import com.chen.playerdemo.presenter.MusicPresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.SharedPref;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.CircleImageView;
import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends BaseFragment<MusicPresenter> implements MusicContract.View, OnPlayerEventListener {

    @BindView(R.id.banner)
    MZBannerView mMZBanner;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;//推荐
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;//精品
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;//我的

    @BindView(R.id.ll_playlist_1)
    LinearLayout ll_playlist_1;
    @BindView(R.id.ll_playlist_2)
    LinearLayout ll_playlist_2;
    @BindView(R.id.ll_playlist_3)
    LinearLayout ll_playlist_3;

    @BindView(R.id.cover)
    CircleImageView mCover;
    @BindView(R.id.song_title_1)
    TextView mSongTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.img_notifyPlayOrPause)
    ImageView img_notifyPlayOrPause;//暂停、播放

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<BannerInfo.BannersBean> bannerList = new ArrayList<>();

    private List<HighQuality.PlaylistsBean> mList1 = new ArrayList<>();
    private List<HighQuality.PlaylistsBean> mList2 = new ArrayList<>();
    private List<PlayListInfo.ResultBean> mList3 = new ArrayList<>();
    private CommonRecyclerAdapter<HighQuality.PlaylistsBean> adapter1;
    private CommonRecyclerAdapter<HighQuality.PlaylistsBean> adapter2;
    private CommonRecyclerAdapter<PlayListInfo.ResultBean> adapter3;

    private List<SongInfo> lastPlayList = new ArrayList<>();

    private TimerTaskManager mTimerTaskManager;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        return new MusicFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new MusicPresenter();
        mPresenter.attachView(this);
        MediaSessionConnection.getInstance().connect();
        initData();
        initAdapter();

        mPresenter.requestBanner();
        mPresenter.requestPlayList();
        mPresenter.requestHighQuality();
        mPresenter.requestPersonalized();

        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                if (bannerList.get(position).getTargetType() == 10) {
//                    jumpToDetail(bannerList.get(position).getTargetId());
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestBanner();
                mPresenter.requestPlayList();
                mPresenter.requestHighQuality();
                mPresenter.requestPersonalized();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
        if (songInfo != null) {
            mSongTitle.setText(songInfo.getSongName());
            ImageUtils.newInstance().load(songInfo.getSongCover(), mCover);
            progressBar.setMax((int) songInfo.getDuration());
            long position = MusicManager.getInstance().getPlayingPosition();
            progressBar.setProgress((int) position);
            updateProgressTask();
            if (MusicManager.getInstance().isPlaying()) {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
            } else {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_play_normal);
            }
        }
    }

    private void initData() {
        if (MusicManager.getInstance().getNowPlayingSongInfo() == null) {
            List<SongInfo> list = SharedPref.getInstance(getContext()).getLists(Constants.SAVE);
            lastPlayList.clear();
            lastPlayList.addAll(list);
            if (lastPlayList != null && lastPlayList.size() > 0) {
                SongInfo songInfo = lastPlayList.get(0);
                mSongTitle.setText(songInfo.getSongName());
                ImageUtils.newInstance().load(songInfo.getSongCover(), mCover);
            }
            MusicManager.getInstance().updatePlayList(lastPlayList);
        }
    }

    private void updateProgressTask() {
        if (mTimerTaskManager == null) {
            mTimerTaskManager = new TimerTaskManager();
        }
        MusicManager.getInstance().addPlayerEventListener(this);
        mTimerTaskManager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                long position = MusicManager.getInstance().getPlayingPosition();
                long duration = MusicManager.getInstance().getDuration();
                progressBar.setMax((int) duration);
                progressBar.setProgress((int) position);
            }
        });
        mTimerTaskManager.startToUpdateProgress();
    }

    @OnClick({R.id.img_notifyPlayOrPause, R.id.img_notifyNext, R.id.img_notifyPre, R.id.ll_play})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.img_notifyPlayOrPause) {//暂停、播放
            if (MusicManager.getInstance().getNowPlayingSongInfo() != null) {
                if (MusicManager.getInstance().isPlaying()) {
                    MusicManager.getInstance().pauseMusic();
                    img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_play_normal);
                } else {
                    updateProgressTask();
                    img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
                    MusicManager.getInstance().playMusic();
                }
            } else {
                if (lastPlayList.size() > 0) {
                    updateProgressTask();
                    img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
                    MusicManager.getInstance().playMusicByInfo(lastPlayList.get(0));
                    MusicManager.getInstance().updatePlayList(lastPlayList);
                }
            }
        } else if (i == R.id.img_notifyNext) {//下一首
            if (MusicManager.getInstance().isSkipToNextEnabled()) {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
                MusicManager.getInstance().skipToNext();
            } else {
                ToastUtils.show("没有下一首了");
            }
        } else if (i == R.id.img_notifyPre) {//上一首
            if (MusicManager.getInstance().isSkipToPreviousEnabled()) {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
                MusicManager.getInstance().skipToPrevious();
            } else {
                ToastUtils.show("没有上一首了");
            }
        } else if (i == R.id.ll_play) {
            if (MusicManager.getInstance().isPlaying()) {
                jumpActivity(Constants.PATH_MUSICDETAIL);
            }
        }
    }

    private void jumpToDetail(String id) {
        ARouter.getInstance()
                .build(Constants.PATH_MUSICLIST)
                .withString(Constants.Jump.JUMP_ID, id)
                .navigation();
    }

    private void initAdapter() {
        if (adapter1 == null) {
            adapter1 = new CommonRecyclerAdapter<HighQuality.PlaylistsBean>(getContext(), mList1, R.layout.songlist_item) {
                @Override
                public void convert(ViewHolder holder, HighQuality.PlaylistsBean item, int position) {
                    holder.setText(R.id.textView, item.getName());
                    if (!StringUtils.isEmpty(item.getCoverImgUrl())) {
                        ImageUtils.newInstance().load(item.getCoverImgUrl().trim(), holder.getView(R.id.imageView));
                    }
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jumpToDetail(item.getId());
                        }
                    });
                }
            };
        }
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 3);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView1.setLayoutManager(gridLayoutManager1);
        recyclerView1.setAdapter(adapter1);

        if (adapter2 == null) {
            adapter2 = new CommonRecyclerAdapter<HighQuality.PlaylistsBean>(getContext(), mList2, R.layout.songlist_item) {
                @Override
                public void convert(ViewHolder holder, HighQuality.PlaylistsBean item, int position) {
                    holder.setText(R.id.textView, item.getName());
                    ImageUtils.newInstance().load(item.getCoverImgUrl(), holder.getView(R.id.imageView));
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jumpToDetail(item.getId());
                        }
                    });
                }
            };
        }
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView2.setLayoutManager(gridLayoutManager2);
        recyclerView2.setAdapter(adapter2);

        if (adapter3 == null) {
            adapter3 = new CommonRecyclerAdapter<PlayListInfo.ResultBean>(getContext(), mList3, R.layout.songlist_item) {
                @Override
                public void convert(ViewHolder holder, PlayListInfo.ResultBean item, int position) {
                    holder.setText(R.id.textView, item.getName());
                    ImageUtils.newInstance().load(item.getPicUrl(), holder.getView(R.id.imageView));
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jumpToDetail(item.getId());
                        }
                    });
                }
            };
        }
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 3);
        recyclerView3.setNestedScrollingEnabled(false);
        recyclerView3.setLayoutManager(gridLayoutManager3);
        recyclerView3.setAdapter(adapter3);
    }

    @Override
    public void setBanner(BannerInfo banner) {
        if (banner != null) {
            bannerList.clear();
            bannerList.addAll(banner.getBanners());
        }
        // 设置数据
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();
    }

    /**
     * 精品
     *
     * @param highData
     */
    @Override
    public void setHighData(HighQuality highData) {
        if (highData != null) {
            mList2.clear();
            mList2.addAll(highData.getPlaylists());
            adapter2.notifyDataSetChanged();
            if (mList2.size() == 0) {
                ll_playlist_2.setVisibility(View.GONE);
            } else {
                ll_playlist_2.setVisibility(View.VISIBLE);
            }
        } else {
            ll_playlist_2.setVisibility(View.GONE);
        }
    }

    /**
     * 推荐
     *
     * @param highData
     */
    @Override
    public void setPlayListData(HighQuality highData) {
        if (highData != null) {
            mList1.clear();
            mList1.addAll(highData.getPlaylists());
            adapter1.notifyDataSetChanged();
            if (mList1.size() == 0) {
                ll_playlist_1.setVisibility(View.GONE);
            } else {
                ll_playlist_1.setVisibility(View.VISIBLE);
            }
        } else {
            ll_playlist_1.setVisibility(View.GONE);
        }
    }

    /**
     * 我的
     *
     * @param personalData
     */
    @Override
    public void setPersonalData(PlayListInfo personalData) {
        if (personalData != null) {
            mList3.clear();
            mList3.addAll(personalData.getResult());
            adapter3.notifyDataSetChanged();
            if (mList3.size() == 0) {
                ll_playlist_3.setVisibility(View.GONE);
            } else {
                ll_playlist_3.setVisibility(View.VISIBLE);
            }
        } else {
            ll_playlist_3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        mSongTitle.setText(songInfo.getSongName());
        ImageUtils.newInstance().load(songInfo.getSongCover(), mCover);
        progressBar.setMax((int) songInfo.getDuration());
        long position = MusicManager.getInstance().getPlayingPosition();
        progressBar.setProgress((int) position);
    }

    @Override
    public void onPlayerStart() {
        mTimerTaskManager.startToUpdateProgress();
    }

    @Override
    public void onPlayerPause() {
        mTimerTaskManager.stopToUpdateProgress();
    }

    @Override
    public void onPlayerStop() {
        mTimerTaskManager.stopToUpdateProgress();
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {
        mTimerTaskManager.stopToUpdateProgress();
    }

    @Override
    public void onBuffering() {
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        mTimerTaskManager.stopToUpdateProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimerTaskManager != null) {
            mTimerTaskManager.removeUpdateProgressTask();
            mTimerTaskManager = null;
        }
        MusicManager.getInstance().removePlayerEventListener(this);
    }

    public static class BannerViewHolder implements MZViewHolder<BannerInfo.BannersBean> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.circular_banner_item, null);
            mImageView = view.findViewById(R.id.imageView);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerInfo.BannersBean data) {
            // 数据绑定
            ImageUtils.newInstance().load(data.getImageUrl(), mImageView);
        }
    }

}
