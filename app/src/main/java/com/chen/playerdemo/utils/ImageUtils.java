package com.chen.playerdemo.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chen.playerdemo.App;
import com.chen.playerdemo.R;

import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class ImageUtils {

    private static ImageUtils imageUtils;

    public static ImageUtils newInstance() {
        if (imageUtils == null) {
            imageUtils = new ImageUtils();
        }
        return imageUtils;
    }

    /**
     * 加载图片
     *
     * @param uri
     * @param imageView
     */
    public void load(Uri uri, ImageView imageView) {
        Glide.with(App.getContext())
                .load(uri)
                .into(imageView);
    }

    public void load(Integer resId, ImageView imageView) {
        imageView.setImageResource(resId);
    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void load(String path, ImageView imageView) {
        Glide.with(App.getContext())
                .asDrawable()
                .load(path)
                .error(R.mipmap.icon_error)
                .into(imageView);
    }

    /**
     * 宽高等比加载图片
     *
     * @param path
     * @param imageView
     * @param width
     */
    public void load(String path, ImageView imageView, int width) {
        Glide.with(App.getContext())
                .load(path)
                .override(width, SIZE_ORIGINAL)
                .fitCenter()
                .into(imageView);
    }

    public void downLoad(String path) {
        try {
            Glide.with(App.getContext())
                    .load(path)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高斯模糊
     *
     * @param url
     * @param imageView
     */
    public void blurTransformation(String url, ImageView imageView) {
        Glide.with(App.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 4)))
                .into(imageView);
    }

    /**
     * 高斯模糊
     *
     * @param url
     * @param imageView
     */
    public void blurTransformation(int url, ImageView imageView) {
        Glide.with(App.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 4)))
                .into(imageView);
    }

    /**
     * 获取图片颜色
     *
     * @param resId
     * @param imageView
     */
    public void loadPickColor(Integer resId, ImageView imageView) {
        Glide.with(App.getContext()).asBitmap()
                .load(resId)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette == null) return;
                                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT), imageView);
                                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT), imageView);
                                } else {
                                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT), imageView);
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 获取图片颜色
     *
     * @param url
     * @param imageView
     */
    public int[] loadPickColor(String url, ImageView imageView) {
        final int[] darkColor = new int[2];
        Glide.with(App.getContext()).asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette == null) return;
                                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT), imageView);
                                    darkColor[0] = palette.getDarkVibrantColor(Color.TRANSPARENT);
                                    darkColor[1] = palette.getVibrantColor(Color.TRANSPARENT);
                                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT), imageView);
                                    darkColor[0] = palette.getDarkMutedColor(Color.TRANSPARENT);
                                    darkColor[1] = palette.getMutedColor(Color.TRANSPARENT);
                                } else {
                                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT), imageView);
                                    darkColor[0] = palette.getLightMutedColor(Color.TRANSPARENT);
                                    darkColor[1] = palette.getLightVibrantColor(Color.TRANSPARENT);
                                }
                            }
                        });
                    }
                });
        return darkColor;
    }

    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor, int color, ImageView ivBg) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = darkColor;

        Bitmap bgBitmap = Bitmap.createBitmap(ivBg.getWidth(), ivBg.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvas.setBitmap(bgBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        canvas.drawRoundRect(rectF, 20, 20, paint);
        canvas.drawRect(rectF, paint);
        ivBg.setImageBitmap(bgBitmap);
    }
}
