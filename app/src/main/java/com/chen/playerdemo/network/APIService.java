package com.chen.playerdemo.network;

import com.chen.playerdemo.Constants;
import com.chen.playerdemo.bean.gank.GankBean;
import com.chen.playerdemo.bean.music.BannerInfo;
import com.chen.playerdemo.bean.music.HighQuality;
import com.chen.playerdemo.bean.music.PlayListDetail;
import com.chen.playerdemo.bean.music.PlayListInfo;
import com.chen.playerdemo.bean.tools.ArticleData;
import com.chen.playerdemo.bean.tools.CalendarData;
import com.chen.playerdemo.bean.tools.CategoryData;
import com.chen.playerdemo.bean.tools.CityData;
import com.chen.playerdemo.bean.tools.DictionaryData;
import com.chen.playerdemo.bean.tools.HistoryData;
import com.chen.playerdemo.bean.tools.MobileData;
import com.chen.playerdemo.bean.tools.WeatherData;
import com.chen.playerdemo.bean.video.AllRec;
import com.chen.playerdemo.bean.video.CategoriesBean;
import com.chen.playerdemo.bean.video.FindBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chenbin
 * 2019-5-16
 **/
public interface APIService {

    /************************************* 网易云音乐api *****************************************/
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


    /************************************* 开眼api *****************************************/
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


    /************************************* Gank api *****************************************/
    /**
     * Gank
     *
     * @return
     */
    @GET("data/{type}/{count}/{pageIndex}")
    Flowable<GankBean> getGankData(@Path("type") String type,
                                   @Path("count") int count,
                                   @Path("pageIndex") int pageIndex);

    /**
     * Gank搜索
     *
     * @param input
     * @param type
     * @param pageIndex
     * @return
     */
    @GET("search/query/{input}/category/{type}/count/10/page/{pageIndex}")
    Flowable<GankBean> gankSearch(@Path("input") String input,
                                  @Path("type") String type,
                                  @Path("pageIndex") int pageIndex);


    /************************************* Mob api *****************************************/
    /**
     * 城市列表查询接口
     *
     * @param key
     * @param city
     * @param province
     * @return
     */
    @GET(Constants.Mob.citys)
    Flowable<CityData> getCitys(@Query("key") String key,
                                @Query("city") String city,
                                @Query("province") String province);

    /**
     * 获取天气信息
     *
     * @param key
     * @return
     */
    @GET(Constants.Mob.weather)
    Flowable<WeatherData> getWeather(@Query("key") String key);

    /**
     * 万年历查询
     *
     * @param key
     * @param date 2015-05-01
     * @return
     */
    @GET(Constants.Mob.calendar)
    Flowable<CalendarData> getCalendar(@Query("key") String key,
                                       @Query("date") String date);

    /**
     * 历史上今天
     *
     * @param key
     * @param day 0716
     * @return
     */
    @GET(Constants.Mob.history)
    Flowable<HistoryData> getHistory(@Query("key") String key,
                                     @Query("day") String day);

    /**
     * 手机号码归属地查询
     *
     * @param key
     * @param phone
     * @return
     */
    @GET(Constants.Mob.mobile)
    Flowable<MobileData> getMobile(@Query("key") String key,
                                   @Query("phone") String phone);

    /**
     * 新华字典查询
     *
     * @param key
     * @param name
     * @return
     */
    @GET(Constants.Mob.dictionary)
    Flowable<DictionaryData> getDictionary(@Query("key") String key,
                                           @Query("name") String name);

    /**
     * 微信精选分类查询
     *
     * @param key
     * @return
     */
    @GET(Constants.Mob.category)
    Flowable<CategoryData> getCategory(@Query("key") String key);

    /**
     * 微信精选列表查询
     *
     * @param key
     * @param cid
     * @param page
     * @param size
     * @return
     */
    @GET(Constants.Mob.article)
    Flowable<ArticleData> getArticle(@Query("key") String key,
                                     @Query("cid") String cid,
                                     @Query("page") int page,
                                     @Query("size") int size);
}
