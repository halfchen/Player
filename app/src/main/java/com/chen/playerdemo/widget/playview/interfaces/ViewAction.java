package com.chen.playerdemo.widget.playview.interfaces;

import com.chen.playerdemo.widget.playview.AliyunScreenMode;
import com.chen.playerdemo.widget.playview.ControlView;
import com.chen.playerdemo.widget.playview.gesture.GestureView;

/**
 * 定义UI界面通用的操作。
 * 主要实现类有{@link ControlView} ,
 * {@link GestureView}
 */
public interface ViewAction {

    /**
     * 隐藏类型
     */
    public enum HideType {
        /**
         * 正常情况下的隐藏
         */
        Normal,
        /**
         * 播放结束的隐藏，比如出错了
         */
        End
    }

    /**
     * 重置
     */
    public void reset();

    /**
     * 显示
     */
    public void show();

    /**
     * 隐藏
     *
     * @param hideType 隐藏类型
     */
    public void hide(HideType hideType);

    /**
     * 设置屏幕全屏情况
     *
     * @param mode {@link AliyunScreenMode#Small}：小屏. {@link AliyunScreenMode#Full}:全屏
     */
    public void setScreenModeStatus(AliyunScreenMode mode);
}
