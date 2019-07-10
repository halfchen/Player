package com.chen.playerdemo.view.activity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chen.playerdemo.Constants;
import com.chen.playerdemo.R;
import com.chen.playerdemo.adapter.CommonRecyclerAdapter;
import com.chen.playerdemo.adapter.MultiTypeSupport;
import com.chen.playerdemo.adapter.ViewHolder;
import com.chen.playerdemo.base.BaseActivity;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.contract.VideoPlayContract;
import com.chen.playerdemo.presenter.VideoPlayPresenter;
import com.chen.playerdemo.utils.ImageUtils;
import com.chen.playerdemo.utils.StringUtils;
import com.chen.playerdemo.utils.TimeUtils;
import com.chen.playerdemo.utils.WxShareUtils;
import com.chen.playerdemo.widget.NormalGSYVideoPlayer;
import com.chen.playerdemo.widget.dialog.AnimHelper;
import com.chen.playerdemo.widget.dialog.AnyLayer;
import com.lzx.starrysky.manager.MusicManager;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.chen.playerdemo.utils.ImageLoader.loadCover;

@Route(path = Constants.PATH_VIDEO_PLAY)
public class VideoPlayActivity extends BaseActivity<VideoPlayPresenter> implements VideoPlayContract.View {

    @BindView(R.id.video_player)
    NormalGSYVideoPlayer detailPlayer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<AllRec.ItemListBeanX> mList = new ArrayList<>();
    private CommonRecyclerAdapter<AllRec.ItemListBeanX> adapter;
    private ImageView ivCoverVideo;

    private String url, title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView() {
        mPresenter = new VideoPlayPresenter();
        mPresenter.attachView(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");

        if (MusicManager.getInstance().isPlaying()) {
            MusicManager.getInstance().stopMusic();
        }

        initAdapter();
        ImageView mShare = detailPlayer.findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailPlayer.isIfCurrentIsFullscreen()) {
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
                                    WxShareUtils.shareVideo(VideoPlayActivity.this, SendMessageToWX.Req.WXSceneSession, url, title, "", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                                    anyLayer.dismiss();
                                }
                            })
                            .onClick(R.id.moments, new AnyLayer.OnLayerClickListener() {
                                @Override
                                public void onClick(AnyLayer anyLayer, View v) {
                                    WxShareUtils.shareVideo(VideoPlayActivity.this, SendMessageToWX.Req.WXSceneTimeline, url, title, "", BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                                    anyLayer.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public final void onClick(View it) {
                detailPlayer.startWindowFullscreen(VideoPlayActivity.this, false, true);
            }
        });
        //设置加载时封面
        ivCoverVideo = new ImageView(this);
        ivCoverVideo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        loadCover(ivCoverVideo, url, this);
        detailPlayer.setThumbImageView(ivCoverVideo);
        detailPlayer.setUp(url, true, title);
        detailPlayer.startPlayLogic();
        mPresenter.getRelated(id);

        detailPlayer.setVideoAllCallBack(new VideoAllCallBack() {
            @Override
            public void onStartPrepared(String url, Object... objects) {
            }

            @Override
            public void onPrepared(String url, Object... objects) {
            }

            @Override
            public void onClickStartIcon(String url, Object... objects) {
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
            }

            @Override
            public void onClickStop(String url, Object... objects) {
            }

            @Override
            public void onClickStopFullscreen(String url, Object... objects) {
            }

            @Override
            public void onClickResume(String url, Object... objects) {
            }

            @Override
            public void onClickResumeFullscreen(String url, Object... objects) {
            }

            @Override
            public void onClickSeekbar(String url, Object... objects) {
            }

            @Override
            public void onClickSeekbarFullscreen(String url, Object... objects) {
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                if (detailPlayer.isIfCurrentIsFullscreen()) {
                    detailPlayer.onBackFullscreen();
                }
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
            }

            @Override
            public void onEnterSmallWidget(String url, Object... objects) {
            }

            @Override
            public void onTouchScreenSeekVolume(String url, Object... objects) {
            }

            @Override
            public void onTouchScreenSeekPosition(String url, Object... objects) {
            }

            @Override
            public void onTouchScreenSeekLight(String url, Object... objects) {
            }

            @Override
            public void onPlayError(String url, Object... objects) {
            }

            @Override
            public void onClickStartThumb(String url, Object... objects) {
            }

            @Override
            public void onClickBlank(String url, Object... objects) {
            }

            @Override
            public void onClickBlankFullscreen(String url, Object... objects) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (detailPlayer.isIfCurrentIsFullscreen()) {
            detailPlayer.onBackFullscreen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setData(AllRec data) {
        if (data != null) {
            mList.clear();
            mList.addAll(data.getItemList());
            recyclerView.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        MultiTypeSupport multiTypeSupport = new MultiTypeSupport() {
            @Override
            public int getLayoutId(Object item, int position) {
                if (mList.get(position).getData() != null) {
                    if (mList.get(position).getData().getDataType().equals(Constants.DataType.TextCard)) {
                        if (mList.get(position).getData().getType().contains(Constants.DataType.footer)) {
                            return R.layout.item_text_card_with_tagid;
                        } else {
                            return R.layout.item_text_card;
                        }
                    } else {
                        return R.layout.item_video_bean_for_client;
                    }
                } else {
                    return R.layout.item_video_bean_for_client;
                }
            }
        };
        if (adapter == null) {
            adapter = new CommonRecyclerAdapter<AllRec.ItemListBeanX>(this, mList, multiTypeSupport) {
                @Override
                public void convert(ViewHolder holder, AllRec.ItemListBeanX item, int position) {
                    if (item.getData() != null) {
                        if (item.getData().getDataType().equals(Constants.DataType.VideoBeanForClient)) {
                            if (item.getData().getCover() != null) {
                                ImageUtils.newInstance().load(item.getData().getCover().getDetail(), holder.getView(R.id.detail));
                            }
                            holder.setText(R.id.title, item.getData().getTitle());
                            holder.setText(R.id.slogan, "#" + item.getData().getCategory());
                            holder.setText(R.id.duration, TimeUtils.formatVideoTime((long) item.getData().getDuration()));

                            holder.setOnIntemClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (item.getData() != null && item.getData().getContent() != null && item.getData().getContent().getData() != null && !StringUtils.isEmpty(item.getData().getContent().getData().getPlayUrl())) {
                                        url = item.getData().getContent().getData().getPlayUrl();
                                        title = item.getData().getContent().getData().getTitle();
                                        detailPlayer.setUp(url, true, title);
                                        if (item.getData().getCover() != null) {
                                            ImageUtils.newInstance().load(item.getData().getCover().getDetail(), ivCoverVideo);
                                        } else {
                                            loadCover(ivCoverVideo, item.getData().getContent().getData().getPlayUrl(), VideoPlayActivity.this);
                                        }
                                        detailPlayer.startPlayLogic();
                                        mPresenter.getRelated(item.getData().getContent().getData().getId());
                                    } else if (item.getData() != null && !StringUtils.isEmpty(item.getData().getPlayUrl())) {
                                        url = item.getData().getPlayUrl();
                                        title = item.getData().getTitle();
                                        detailPlayer.setUp(url, true, title);
                                        if (item.getData().getCover() != null) {
                                            ImageUtils.newInstance().load(item.getData().getCover().getDetail(), ivCoverVideo);
                                        } else {
                                            loadCover(ivCoverVideo, item.getData().getPlayUrl(), VideoPlayActivity.this);
                                        }
                                        detailPlayer.startPlayLogic();
                                        mPresenter.getRelated(item.getData().getId());
                                    }
                                }
                            });
                        } else {
                            if (item.getData() != null) {
                                holder.setText(R.id.text, item.getData().getText());
                            }
                        }
                    }
                }
            };
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
