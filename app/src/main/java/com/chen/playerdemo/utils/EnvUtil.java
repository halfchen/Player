package com.chen.playerdemo.utils;

import android.content.Context;
import android.util.TypedValue;

import com.chen.playerdemo.App;

public class EnvUtil {

    private static int sStatusBarHeight;

    public static int getActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return DimensUtils.dp2px(context, 44);
    }

    public static int getStatusBarHeight() {
        if (sStatusBarHeight == 0) {
            int resourceId =
                    App.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sStatusBarHeight = App.getContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return sStatusBarHeight;
    }
}