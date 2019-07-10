package com.chen.playerdemo.view.activity;

import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.widget.playview.AliyunVodPlayerView;
import com.lzx.starrysky.manager.MusicManager;

import butterknife.BindView;

/**
 * 使用阿里播放器播放直播流视频
 */
@Route(path = Constants.PATH_TV)
public class TVPlayActivity extends BaseActivity {

    @BindView(R.id.video_player)
    AliyunVodPlayerView mAliyunVodPlayerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tvplay;
    }

    @Override
    protected void initView() {
        if (MusicManager.getInstance().isPlaying()) {
            MusicManager.getInstance().pauseMusic();
        }
        Constants.PLAY_PARAM_URL = getIntent().getStringExtra("url");
        initAliyunPlayerView();
    }

    private void initAliyunPlayerView() {
        mAliyunVodPlayerView.setControlBarCanShow(false);
        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        //mAliyunVodPlayerView.setCirclePlay(true);
        mAliyunVodPlayerView.setAutoPlay(true);
        if ("localSource".equals(Constants.PLAY_PARAM_TYPE)) {
            AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            alsb.setSource(Constants.PLAY_PARAM_URL);
            Uri uri = Uri.parse(Constants.PLAY_PARAM_URL);
            if ("rtmp".equals(uri.getScheme())) {
                alsb.setTitle("");
            }
            AliyunLocalSource localSource = alsb.build();
            if (mAliyunVodPlayerView != null) {
                mAliyunVodPlayerView.setLocalSource(localSource);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }
        super.onDestroy();
    }
}
