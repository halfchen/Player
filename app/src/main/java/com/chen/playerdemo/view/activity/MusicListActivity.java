package com.chen.playerdemo.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.animation.ArgbEvaluatorCompat;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.music.PlayListDetail;
import com.chen.playerdemo.contract.MusicListContract;
import com.chen.playerdemo.presenter.MusicListPresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.SharedPref;
import com.chen.playerdemo.utils.StatusBarUtil;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.utils.WxShareUtils;
import com.chen.playerdemo.widget.CircleImageView;
import com.chen.playerdemo.widget.RecycleViewDivider;
import com.chen.playerdemo.widget.dialog.AnimHelper;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_MUSICLIST)
public class MusicListActivity extends BaseActivity<MusicListPresenter> implements MusicListContract.View, OnPlayerEventListener {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_background)
    ImageView iv_background;
    @BindView(R.id.ll_play_all)
    LinearLayout ll_play_all;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ll_detail)
    LinearLayout ll_detail;
    @BindView(R.id.coverImgUrl)
    RoundedImageView coverImgUrl;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.avatarUrl)
    ImageView avatarUrl;
    @BindView(R.id.cover)
    CircleImageView mCover;
    @BindView(R.id.song_title_1)
    TextView mSongTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.img_notifyPlayOrPause)
    ImageView img_notifyPlayOrPause;//暂停、播放

    private List<SongInfo> mList = new ArrayList<>();
    private CommonRecyclerAdapter<SongInfo> adapter;
    private int[] darkColor;
    private TimerTaskManager mTimerTaskManager;

    private List<SongInfo> lastPlayList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_music_list;
    }

    @Override
    protected void initView() {
        mPresenter = new MusicListPresenter();
        mPresenter.attachView(this);
        mTvTitle.setSelected(true);
        initData();
        updateProgressTask();

        String id = getIntent().getStringExtra(Constants.Jump.JUMP_ID);
        mPresenter.requestPlayListDetail(id);
        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<SongInfo>(this, mList, R.layout.music_detail_item) {
                @Override
                public void convert(ViewHolder holder, SongInfo item, int position) {
                    holder.setText(R.id.tv_num, (position + 1) + "");
                    holder.setText(R.id.tv_musicname, item.getSongName());
                    holder.setText(R.id.tv_author, item.getArtist());
                    holder.setOnIntemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mList.size() > 0) {
                                SharedPref.getInstance(MusicListActivity.this).putLists(Constants.SAVE, mList);
                                MusicManager.getInstance().updatePlayList(mList);
                            }
                            MusicManager.getInstance().playMusicByInfo(item);
                            jumpActivity(Constants.PATH_MUSICDETAIL);
                        }
                    });
                    holder.getView(R.id.share_music).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            share(item.getSongUrl(), item.getSongName(), item.getArtist(), item.getSongCover());
                        }
                    });
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.bg_f4)));
    }

    private void loadBitmap(int targetScene, String url, String title, String description, String bitmapUrl) {
        AnyLayer anyLayer = AnyLayer.with(this);
        anyLayer.contentView(R.layout.dialog_loading)
                .backgroundColorRes(R.color.dialog_bg)
                .cancelableOnTouchOutside(false)
                .cancelableOnClickKeyBack(true)
                .show();
        Glide.with(this).asBitmap().load(bitmapUrl).into(new SimpleTarget<Bitmap>() {
            /**
             * 成功的回调
             */
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                // 下面这句代码是一个过度dialog，因为是获取网络图片，需要等待时间
                anyLayer.dismiss();
                // 调用方法
                WxShareUtils.shareMusic(MusicListActivity.this, targetScene, url, title, description, bitmap);
            }

            /**
             * 失败的回调
             */
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                anyLayer.dismiss();
                WxShareUtils.shareMusic(MusicListActivity.this, targetScene, url, title, description, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            }
        });
    }

    private void share(String url, String title, String description, String bitmapUrl) {
        AnyLayer.target(findViewById(R.id.dialog_bottom))
                .direction(AnyLayer.Direction.TOP)
                .contentView(R.layout.dialog_share)
                .backgroundColorRes(R.color.dialog_bg)
                .gravity(Gravity.BOTTOM)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .contentAnim(new AnyLayer.IAnim() {
                    @Override
                    public long inAnim(View content) {
                        AnimHelper.startBottomInAnim(content, Constants.ANIM_DURATION);
                        return Constants.ANIM_DURATION;
                    }

                    @Override
                    public long outAnim(View content) {
                        AnimHelper.startBottomOutAnim(content, Constants.ANIM_DURATION);
                        return Constants.ANIM_DURATION;
                    }
                })
                .onClick(R.id.wechat, new AnyLayer.OnLayerClickListener() {
                    @Override
                    public void onClick(AnyLayer anyLayer, View v) {
                        anyLayer.dismiss();
                        loadBitmap(SendMessageToWX.Req.WXSceneSession, url, title, description, bitmapUrl);
                    }
                })
                .onClick(R.id.moments, new AnyLayer.OnLayerClickListener() {
                    @Override
                    public void onClick(AnyLayer anyLayer, View v) {
                        anyLayer.dismiss();
                        loadBitmap(SendMessageToWX.Req.WXSceneTimeline, url, title, description, bitmapUrl);
                    }
                })
                .show();
    }

    private void initData() {
        if (MusicManager.getInstance().getNowPlayingSongInfo() == null) {
            List<SongInfo> list = SharedPref.getInstance(this).getLists(Constants.SAVE);
            lastPlayList.clear();
            lastPlayList.addAll(list);
            if (lastPlayList != null && lastPlayList.size() > 0) {
                SongInfo songInfo = lastPlayList.get(0);
                mSongTitle.setText(songInfo.getSongName());
                ImageUtils.newInstance().load(songInfo.getSongCover(), mCover);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
        if (songInfo != null) {
            mSongTitle.setText(songInfo.getSongName());
            ImageUtils.newInstance().load(songInfo.getSongCover(), mCover);
            progressBar.setMax((int) songInfo.getDuration());
            long position = MusicManager.getInstance().getPlayingPosition();
            progressBar.setProgress((int) position);
            if (MusicManager.getInstance().isPlaying()) {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
            } else {
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_play_normal);
            }
        }
    }

    @OnClick({R.id.img_notifyPlayOrPause, R.id.img_notifyNext, R.id.img_notifyPre, R.id.ll_play_all, R.id.ll_play, R.id.back})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.img_notifyPlayOrPause) {//暂停、播放
            if (MusicManager.getInstance().getNowPlayingSongInfo() != null) {
                if (MusicManager.getInstance().isPlaying()) {
                    MusicManager.getInstance().pauseMusic();
                    img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_play_normal);
                } else {
                    img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
                    MusicManager.getInstance().playMusic();
                }
            } else {
                if (lastPlayList.size() > 0) {
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
        } else if (i == R.id.ll_play_all) {//全部播放
            if (mList.size() > 0) {
                SharedPref.getInstance(MusicListActivity.this).putLists(Constants.SAVE, mList);
                MusicManager.getInstance().updatePlayList(mList);
                MusicManager.getInstance().playMusicByInfo(mList.get(0));
                img_notifyPlayOrPause.setImageResource(R.mipmap.notify_btn_light_pause_normal);
            } else {
                ToastUtils.show("暂无歌曲");
            }
        } else if (i == R.id.ll_play) {
            if (MusicManager.getInstance().isPlaying()) {
                jumpActivity(Constants.PATH_MUSICDETAIL);
            }
        } else if (i == R.id.back) {
            finish();
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

    @Override
    public void setDetail(PlayListDetail playListDetail) {
        if (playListDetail != null && playListDetail.getResult() != null) {
            mList.clear();
            for (PlayListDetail.ResultBean.TracksBean bean : playListDetail.getResult().getTracks()) {
                SongInfo songInfo = new SongInfo();
                songInfo.setAlbumName(playListDetail.getResult().getName());
                if (playListDetail.getResult().getCreator() != null) {
                    songInfo.setAlbumArtist(playListDetail.getResult().getCreator().getNickname());
                } else if (bean.getArtists().size() > 0) {
                    songInfo.setAlbumArtist(bean.getArtists().get(0).getName());
                }
                songInfo.setSongCover(bean.getAlbum().getPicUrl());
                songInfo.setSongId(bean.getId());
                songInfo.setSongName(bean.getName());
                songInfo.setDuration(bean.getDuration());
                if (bean.getArtists().size() > 0) {
                    if (bean.getAlbum() != null) {
                        songInfo.setArtist(bean.getArtists().get(0).getName() + " - " + bean.getAlbum().getName());
                    } else {
                        songInfo.setArtist(bean.getArtists().get(0).getName());
                    }
                } else if (bean.getAlbum() != null) {
                    songInfo.setArtist(bean.getAlbum().getName());
                }
                songInfo.setSongUrl(Constants.PLAY_URL + bean.getId() + Constants.PLAY_END);
                mList.add(songInfo);
            }
            adapter.notifyDataSetChanged();
            ll_play_all.setVisibility(View.VISIBLE);
            ll_detail.setVisibility(View.VISIBLE);
            mTvNumber.setText("(共" + mList.size() + "首)");
            mTvTitle.setText(playListDetail.getResult().getName());

            name.setText(playListDetail.getResult().getName());
            description.setText(playListDetail.getResult().getDescription());
            ImageUtils.newInstance().load(playListDetail.getResult().getCoverImgUrl(), coverImgUrl);
            if (playListDetail.getResult().getCreator() != null) {
                nickname.setText(playListDetail.getResult().getCreator().getNickname());
                ImageUtils.newInstance().load(playListDetail.getResult().getCreator().getAvatarUrl(), avatarUrl);
            }
            darkColor = ImageUtils.newInstance().loadPickColor(playListDetail.getResult().getCoverImgUrl(), iv_background);

            appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    if (i < -280) {
                        toolbar.setBackgroundColor(darkColor[0]);
                        StatusBarUtil.setWindowStatusBarColor(MusicListActivity.this, darkColor[0]);
                    } else {
                        float fraction = (float) Math.abs(i) / 280;
                        Integer barColor = ArgbEvaluatorCompat.getInstance().evaluate(fraction, getResources().getColor(R.color.transparent), darkColor[0]);
                        StatusBarUtil.setWindowStatusBarColor(MusicListActivity.this, barColor);
                        toolbar.setBackgroundColor(barColor);
                    }
                }
            });
        }
    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        if (mSongTitle != null) {
            mSongTitle.setText(songInfo.getSongName());
        }
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
    protected void onDestroy() {
        super.onDestroy();
        mTimerTaskManager.removeUpdateProgressTask();
        MusicManager.getInstance().removePlayerEventListener(this);
    }
}
