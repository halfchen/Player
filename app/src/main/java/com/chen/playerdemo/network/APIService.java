package com.chen.playerdemo.network;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.gank.Welfare;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListDetail;
import com.chen.playerdemo.bean.music.PlayListInfo;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.bean.video.CategoriesBean;
import com.chen.playerdemo.bean.video.FindBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public interface APIService {

    /**
     * 精品推荐
     *
     * @return
     */
    @GET(Constants.HIGHQUALITY)
    Flowable<HighQuality> requestHighQuality();

    /**
     * 推荐
     *
     * @return
     */
    @GET(Constants.PLAYLIST)
    Flowable<HighQuality> requestPlayList();

    /**
     * banner
     *
     * @return
     */
    @GET(Constants.BANNER)
    Flowable<BannerInfo> requestBanner();

    /**
     * 歌单详情
     *
     * @param id
     * @return
     */
    @GET(Constants.PLAYLISTDETAIL)
    Flowable<PlayListDetail> requestPlayListDetail(@Query("id") String id);

    /**
     * 个人推荐歌单
     *
     * @return
     */
    @GET(Constants.PERSONALIZED)
    Flowable<PlayListInfo> requestPersonalized();


    /**
     * 推荐
     *
     * @param page
     * @return
     */
    @GET(Constants.VideoUrl.allrec)
    Flowable<AllRec> getAllRec(@Query("page") int page);

    /**
     * 发现
     *
     * @return
     */
    @GET(Constants.VideoUrl.discovery)
    Flowable<FindBean> getFind();

    /**
     * 发现
     *
     * @return
     */
    @GET(Constants.VideoUrl.feed)
    Flowable<AllRec> getDaily(@Query("date") String date,
                              @Query("num") int num);

    /**
     * 周排行
     *
     * @return
     */
    @GET(Constants.VideoUrl.weekly)
    Flowable<AllRec> getWeekly(@Query("start") int start,
                               @Query("num") int num);

    /**
     * 月排行
     *
     * @return
     */
    @GET(Constants.VideoUrl.monthly)
    Flowable<AllRec> getMonthly(@Query("start") int start,
                                @Query("num") int num);

    /**
     * 总排行
     *
     * @return
     */
    @GET(Constants.VideoUrl.historical)
    Flowable<AllRec> getHistorical(@Query("start") int start,
                                   @Query("num") int num);

    /**
     * 全部分类
     *
     * @return
     */
    @GET(Constants.VideoUrl.categories)
    Flowable<CategoriesBean> getCategories();

    /**
     * 相关推荐
     *
     * @param id
     * @return
     */
    @GET(Constants.VideoUrl.related)
    Flowable<AllRec> getRelated(@Query("id") String id);

    /**
     * 搜索
     *
     * @return
     */
    @GET(Constants.VideoUrl.search)
    Flowable<AllRec> getSearch(@Query("start") int start,
                               @Query("num") int num,
                               @Query("query") String query);


    /**
     * 福利
     *
     * @return
     */
    @GET(Constants.GankUrl.welfare)
    Flowable<Welfare> getWelfare();

}
