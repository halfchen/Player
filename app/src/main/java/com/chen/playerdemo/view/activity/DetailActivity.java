package com.chen.playerdemo.view.activity;

import android.support.v4.media.session.PlaybackStateCompat;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.StatusBarUtil;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.ProgressView;
import com.chen.playerdemo.widget.musiccoverview.MusicCoverView;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constants.PATH_MUSICDETAIL)
public class DetailActivity extends BaseActivity implements MusicCoverView.Callbacks, OnPlayerEventListener {

    @BindView(R.id.cover)
    MusicCoverView mCoverView;
    @BindView(R.id.song_title)
    TextView mSongTitle;
    @BindView(R.id.artist)
    TextView mArtist;
    @BindView(R.id.repeat)
    ImageView mRepeatView;
    @BindView(R.id.shuffle)
    ImageView mShuffleView;
    @BindView(R.id.previous)
    ImageView mPrevious;
    @BindView(R.id.rewind)
    ImageView mRewind;
    @BindView(R.id.forward)
    ImageView mForward;
    @BindView(R.id.next)
    ImageView mNext;
    @BindView(R.id.time)
    TextView mTimeView;
    @BindView(R.id.duration)
    TextView mDurationView;
    @BindView(R.id.progress)
    ProgressView mProgressView;

    private TimerTaskManager mTimerTaskManager;

    @Override
    public int getLayoutId() {
        return R.layout.content_detail;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setWindowStatusBarColor(this, getResources().getColor(R.color.musicplay_bg));
        mTimerTaskManager = new TimerTaskManager();

        SongInfo songInfo = MusicManager.getInstance().getNowPlayingSongInfo();
        updateUI(songInfo);
        mCoverView.setCallbacks(this);

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                MusicManager.getInstance().playMusic();
                mCoverView.start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
        MusicManager.getInstance().addPlayerEventListener(this);

        mTimerTaskManager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                long position = MusicManager.getInstance().getPlayingPosition();
                long duration = MusicManager.getInstance().getDuration();
                if (mProgressView.getMax() != duration) {
                    mProgressView.setMax((int) duration);
                    mDurationView.setText(TimeUtils.formatMusicTime(duration));
                }
                mProgressView.setProgress((int) position);
                mDurationView.setText(TimeUtils.formatMusicTime(duration));
                mTimeView.setText(TimeUtils.formatMusicTime(position));
            }
        });

        if (MusicManager.getInstance().isPlaying()) {
            mTimerTaskManager.startToUpdateProgress();
        }
    }

    private void updateUI(SongInfo songInfo) {
        if (songInfo != null && !isDestroyed() && !isFinishing()) {
            ImageUtils.newInstance().load(songInfo.getSongCover(), mCoverView);
            mCoverView.start();
            mSongTitle.setText(songInfo.getSongName());
            mArtist.setText(songInfo.getArtist());
        }
    }

    @OnClick({R.id.repeat, R.id.shuffle, R.id.previous, R.id.rewind, R.id.forward, R.id.next, R.id.fab})
    public void click(View view) {
        int i = view.getId();
        if (i == R.id.repeat) {
            int repeatMode = MusicManager.getInstance().getRepeatMode();
            if (repeatMode == PlaybackStateCompat.REPEAT_MODE_NONE) {
                MusicManager.getInstance().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE);
                ToastUtils.show("设置为单曲循环");
                mRepeatView.setImageResource(R.mipmap.ic_single_cycle);
            } else if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE) {
                MusicManager.getInstance().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL);
                ToastUtils.show("设置为列表循环");
                mRepeatView.setImageResource(R.mipmap.ic_play_order);
            } else if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ALL) {
                MusicManager.getInstance().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_NONE);
                ToastUtils.show("设置为列表播放");
                mRepeatView.setImageResource(R.mipmap.ic_loop_play);
            }
        } else if (i == R.id.shuffle) {
            int shuffleMode = MusicManager.getInstance().getShuffleMode();
            if (shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_NONE) {
                MusicManager.getInstance().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_ALL);
                mShuffleView.setImageResource(R.mipmap.ic_shuffle_play);
                ToastUtils.show("设置为随机播放");

            } else {
                MusicManager.getInstance().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE);
                mShuffleView.setImageResource(R.mipmap.ic_play_list);
                ToastUtils.show("设置为顺序播放");
            }
        } else if (i == R.id.previous) {
            if (MusicManager.getInstance().isSkipToPreviousEnabled()) {
                MusicManager.getInstance().skipToPrevious();
            } else {
                ToastUtils.show("没有上一首了");
            }
        } else if (i == R.id.rewind) {
            MusicManager.getInstance().rewind();
        } else if (i == R.id.forward) {
            MusicManager.getInstance().fastForward();
        } else if (i == R.id.next) {
            if (MusicManager.getInstance().isSkipToNextEnabled()) {
                MusicManager.getInstance().skipToNext();
            } else {
                ToastUtils.show("没有下一首了");
            }
        } else if (i == R.id.fab) {
            MusicManager.getInstance().pauseMusic();
            if (mCoverView != null) {
                mCoverView.stop();
            }
        }
    }

    @Override
    public void onMorphEnd(MusicCoverView coverView) {
    }

    @Override
    public void onRotateEnd(MusicCoverView coverView) {
        supportFinishAfterTransition();
    }

    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        updateUI(songInfo);
    }

    @Override
    public void onPlayerStart() {
        mTimerTaskManager.startToUpdateProgress();
        if (mCoverView != null) {
            mCoverView.start();
        }
    }

    @Override
    public void onPlayerPause() {
        mTimerTaskManager.stopToUpdateProgress();
        if (mCoverView != null) {
            mCoverView.stop();
        }
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
    }
}
