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

    public static final String PATH_RANK = "/dudu/rank"; // 开眼排行榜
    public static final String PATH_CLASSIFY = "/dudu/classify"; // 开眼 全部分类
    public static final String PATH_VIDEO_PLAY = "/dudu/videoplay";//视频播放
    public static final String PATH_WEB = "/dudu/web";//活动
    public static final String PATH_SEARCH = "/dudu/search";//搜索

    public static final long ANIM_DURATION = 350;

    public static final String SAVE = "SavePlayList";//保存上次播放歌单

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

        public static final String welfare = "data/福利/1000/1";
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

    /**
     * 下载类型
     */
    public class DownloadType {
        public static final String IMAGE = "/image/";
        public static final String VIDEO = "/video/";
        public static final String MUSIC = "/music/";
    }
}
