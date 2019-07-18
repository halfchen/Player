package com.chen.playerdemo;

/**
 * Created by chenbin
 * 2019-4-8
 **/
public class Constants {

    public static final String WX_APPID = "wxe1e564e639c8dcfd";

    public static final String BASE_URL = "https://music.163.com/api/";

    public static final String HIGHQUALITY = "playlist/highquality/list";
    public static final String PLAYLIST = "playlist/list";
    public static final String BANNER = "v2/banner/get?clientType=pc";
    public static final String PERSONALIZED = "personalized/playlist";
    public static final String PLAYLISTDETAIL = "playlist/detail";

    public static final String PLAY_URL = "http://music.163.com/song/media/outer/url?id=";
    public static final String PLAY_END = ".mp3";

    // Route
    public static final String PATH_MAIN = "/dudu/video"; // 视频首页

    public static final String PATH_MUSICLIST = "/dudu/MusicList"; // 详情页
    public static final String PATH_MUSICDETAIL = "/dudu/MusicDetail"; // 详情播放页

    public static final String PATH_TV = "/dudu/tv";//直播

    public static final String PATH_RANK = "/dudu/rank"; // 开眼排行榜
    public static final String PATH_CLASSIFY = "/dudu/classify"; // 开眼 全部分类
    public static final String PATH_VIDEO_PLAY = "/dudu/videoplay";//视频播放
    public static final String PATH_WEB = "/dudu/web";//活动
    public static final String PATH_SEARCH = "/dudu/search";//搜索
    public static final String PATH_GANK_SEARCH = "/dudu/ganksearch";//Gank搜索

    public static final String PATH_TOOLS = "/dudu/tools";//工具
    public static final String PATH_HISTORY = "/dudu/history";//历史上的今天
    public static final String PATH_CALENDAR = "/dudu/calendar";//万年历
    public static final String PATH_MOBILE = "/dudu/mobile";//手机号码归属地查询
    public static final String PATH_WX_ARTICLE = "/dudu/wx_article";//微信精选

    public static final long ANIM_DURATION = 350;

    public static final String SAVE = "SavePlayList";//保存上次播放歌单

    /**
     * type, 用于区分播放类型, localSource
     * vidsts: vid类型
     * localSource: url类型
     */
    public static String PLAY_PARAM_TYPE = "localSource";
    public static String PLAY_PARAM_URL = "";

    public class Jump {
        public static final String JUMP_ID = "id";
    }

    public class VideoUrl {
        public static final String base_url = "http://baobab.kaiyanapp.com/api/";

        public static final String allrec = "v5/index/tab/allRec";//推荐
        public static final String feed = "v5/index/tab/feed";//日报
        public static final String discovery = "v7/index/tab/discovery";//发现

        public static final String search = "v3/search";//搜索

        public static final String related = "v4/video/related";//相关推荐

        //排行榜
        public static final String weekly = "v4/rankList/videos?strategy=weekly";//周排行
        public static final String monthly = "v4/rankList/videos?strategy=monthly";//月排行
        public static final String historical = "v4/rankList/videos?strategy=historical";//总排行

        public static final String categories = "v4/categories/all";//全部分类
        public static final String categories_videos = "v1/tag/videos?start=10&num=10&strategy=date&id=658";//分类推荐
        public static final String categories_dynamics = "v6/tag/dynamics?start=10&num=10&id=658";//分类广场
    }

    public static class GankUrl {
        public static final String base = "http://gank.io/api/";
    }

    public static class Mob {
        public static final String Mob_key = "2bbdbbc08f2c2";

        public static final String MOB_BASE = "http://apicloud.mob.com/";

        public static final String citys = "v1/weather/citys";//城市列表查询接口 需要参数：key, city, province
        public static final String weather = "v1/weather/query";//获取天气信息  需要参数：key
        public static final String calendar = "appstore/calendar/day";//万年历查询  需要参数：key, date[2015-05-01]
        public static final String mobile = "v1/mobile/address/query";//手机号码归属地查询  需要参数：key, phone

        public static final String category = "wx/article/category/query";//微信精选分类查询  需要参数：key (获取微信精选列表查询所需的cid参数)
        public static final String article = "wx/article/search";//微信精选列表查询  需要参数：key,
        // cid[1:时尚 2:热点 3:健康 5:百科 7:娱乐 8:美文 9:旅行 10:媒体达人 11:搞笑 12:影视音乐 13:互联网
        // 14:文史 15:金融 16:体育 17:游戏 18:两性 19:社交交友 20:女人 23:购物 24:美女 25:微信技巧
        // 26:星座 27:美食 28:教育 29:职场 30:酷品 31:母婴 32:摄影 33:创投 34:典藏 35:家装 36:汽车 37:段子], page, size

        public static final String history = "appstore/history/query";//历史上今天  需要参数：key, day[0716]
        public static final String dictionary = "appstore/dictionary/query";//新华字典查询  需要参数：key, name
    }

    public class DataType {
        public static final String Banner = "Banner";
        public static final String ItemCollection = "ItemCollection";
        public static final String VideoBeanForClient = "VideoBeanForClient";
        public static final String TextCardWithRightAndLeftTitle = "TextCardWithRightAndLeftTitle";
        public static final String TagBriefCard = "TagBriefCard";
        public static final String FollowCard = "FollowCard";
        public static final String TextCard = "TextCard";
        public static final String TextCardWithTagId = "TextCardWithTagId";
        public static final String HorizontalScrollCard = "HorizontalScrollCard";
        public static final String BriefCard = "BriefCard";
        public static final String AutoPlayVideoAdDetail = "AutoPlayVideoAdDetail";
        public static final String SquareCard = "SquareCard";
        public static final String RectangleCard = "RectangleCard";

        public static final String footer = "footer";
        public static final String squareCardCollection = "squareCardCollection";//推荐 ItemCollection
        public static final String specialSquareCardCollection = "specialSquareCardCollection"; //发现 ItemCollection
        public static final String columnCardList = "columnCardList"; //发现 ItemCollection 专题策划
    }
}
