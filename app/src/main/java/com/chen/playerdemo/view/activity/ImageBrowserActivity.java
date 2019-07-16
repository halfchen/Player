package com.chen.playerdemo.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chen.playerdemo.R;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.download.DownloadManager;
import com.chen.playerdemo.download.OnDownloadListener;
import com.chen.playerdemo.utils.ToastUtils;
import com.chen.playerdemo.widget.photoview.MNGestureView;
import com.chen.playerdemo.widget.photoview.PreviewPositionListener;
import com.chen.playerdemo.widget.photoview.ProgressWheel;
import com.chen.playerdemo.widget.photoview.ZoomOutPageTransformer;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageBrowserActivity extends BaseActivity {

    @BindView(R.id.mnGestureView)
    MNGestureView mnGestureView;
    @BindView(R.id.viewPagerBrowser)
    ViewPager viewPagerBrowser;
    @BindView(R.id.tvNumShow)
    TextView tvNumShow;
    @BindView(R.id.rl_black_bg)
    RelativeLayout rl_black_bg;

    private static final String ImageList = "list";
    private static final String ImagePosition = "position";
    private Context context;
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private int currentPosition;
    private DownloadManager downloadManager;
    private ProgressDialog mProgressDialog;
    private static PreviewPositionListener mlistener;

    /**
     * 打开浏览页面
     *
     * @param context   上下文
     * @param view      点击的当前View
     * @param position  默认打开第几个
     * @param imageList 数据源ArrayList<String>
     */
    public static void showImageBrowser(Context context, View view, int position, ArrayList<String> imageList, PreviewPositionListener listener) {
        mlistener = listener;
        Intent intent = new Intent(context, ImageBrowserActivity.class);
        intent.putExtra(ImageList, imageList);
        intent.putExtra(ImagePosition, position);

        //android V4包的类,用于两个activity转场时的缩放效果实现
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        try {
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.browser_enter_anim, 0);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_browser;
    }

    @Override
    protected void initView() {
        context = this;
        imageUrlList = getIntent().getStringArrayListExtra(ImageList);
        currentPosition = getIntent().getIntExtra(ImagePosition, 1);

        tvNumShow.setText(String.valueOf((currentPosition + 1) + "/" + imageUrlList.size()));

        downloadManager = DownloadManager.getInstance();
        initProgressDialog();
        initViewPager();
    }

    private void initViewPager() {
        viewPagerBrowser.setAdapter(new MyAdapter());
        viewPagerBrowser.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPagerBrowser.setCurrentItem(currentPosition);
        viewPagerBrowser.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                tvNumShow.setText(String.valueOf((position + 1) + "/" + imageUrlList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mnGestureView.setOnSwipeListener(new MNGestureView.OnSwipeListener() {
            @Override
            public void downSwipe() {
                finishBrowser();
            }

            @Override
            public void onSwiping(float deltaY) {
                tvNumShow.setVisibility(View.GONE);

                float mAlpha = 1 - deltaY / 500;
                if (mAlpha < 0.3) {
                    mAlpha = 0.3f;
                }
                if (mAlpha > 1) {
                    mAlpha = 1;
                }
                rl_black_bg.setAlpha(mAlpha);
            }

            @Override
            public void overSwipe() {
                tvNumShow.setVisibility(View.VISIBLE);
                rl_black_bg.setAlpha(1);
            }
        });
    }

    @OnClick(R.id.tvSave)
    public void click(View view) {
        downloadImage(imageUrlList.get(currentPosition));
    }

    private void downloadImage(String path) {
        mProgressDialog.show();
        downloadManager.startDownload(path, downloadListener);
    }

    /**
     * 下载进度
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this, R.style.DialogTheme);
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

    /**
     * 通知更新相册
     */
    private void updateAlbum() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(downloadManager.getDownloadFilePath()));
        intent.setData(uri);
        context.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！
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
            updateAlbum();
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

    private class MyAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyAdapter() {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View inflate = layoutInflater.inflate(R.layout.mn_image_browser_item_show_image, container, false);
            final PhotoView imageView = (PhotoView) inflate.findViewById(R.id.photoImageView);
            RelativeLayout rl_browser_root = (RelativeLayout) inflate.findViewById(R.id.rl_browser_root);
            final ProgressWheel progressWheel = (ProgressWheel) inflate.findViewById(R.id.progressWheel);
            final RelativeLayout rl_image_placeholder_bg = (RelativeLayout) inflate.findViewById(R.id.rl_image_placeholder_bg);
            final ImageView iv_fail = (ImageView) inflate.findViewById(R.id.iv_fail);

            iv_fail.setVisibility(View.GONE);

            final String url = imageUrlList.get(position);
            Glide
                    .with(context)
                    .load(url)
                    .thumbnail(0.2f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressWheel.setVisibility(View.GONE);
                            iv_fail.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressWheel.setVisibility(View.GONE);
                            rl_image_placeholder_bg.setVisibility(View.GONE);
                            iv_fail.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);


            rl_browser_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });

            container.addView(inflate);
            return inflate;
        }
    }

    @Override
    public void onBackPressed() {
        finishBrowser();
    }

    private void finishBrowser() {
        tvNumShow.setVisibility(View.GONE);
        rl_black_bg.setAlpha(0);
        mlistener.scrollToPosition(currentPosition);
        finish();
        this.overridePendingTransition(0, R.anim.browser_exit_anim);
    }
}
