package com.chen.playerdemo.widget.playview.theme;

import com.chen.playerdemo.widget.playview.AliyunVodPlayerView;
import com.chen.playerdemo.widget.playview.ControlView;
import com.chen.playerdemo.widget.playview.GuideView;
import com.chen.playerdemo.widget.playview.other.QualityView;
import com.chen.playerdemo.widget.playview.other.SpeedView;
import com.chen.playerdemo.widget.playview.tipsview.ErrorView;
import com.chen.playerdemo.widget.playview.tipsview.NetChangeView;
import com.chen.playerdemo.widget.playview.tipsview.ReplayView;
import com.chen.playerdemo.widget.playview.tipsview.TipsView;

/**
 * 主题的接口。用于变换UI的主题。
 * 实现类有{@link ErrorView}，{@link NetChangeView} , {@link ReplayView} ,{@link ControlView},
 * {@link GuideView} , {@link QualityView}, {@link SpeedView} , {@link TipsView},
 * {@link AliyunVodPlayerView}
 */

public interface ITheme {
    /**
     * 设置主题
     *
     * @param theme 支持的主题
     */
    void setTheme(AliyunVodPlayerView.Theme theme);
}
