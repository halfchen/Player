package com.chen.playerdemo.bean.video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbin
 * 2019-5-29
 **/
public class AllRec {

    private int count;
    private int total;
    private String nextPageUrl;
    private boolean adExist;
    private List<ItemListBeanX> itemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl == null ? "" : nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isAdExist() {
        return adExist;
    }

    public void setAdExist(boolean adExist) {
        this.adExist = adExist;
    }

    public List<ItemListBeanX> getItemList() {
        if (itemList == null) {
            return new ArrayList<>();
        }
        return itemList;
    }

    public void setItemList(List<ItemListBeanX> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBeanX {

        private String type;
        private String text;
        private DataBeanXX data;
        private String tag;
        private String id;

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBeanXX getData() {
            return data;
        }

        public void setData(DataBeanXX data) {
            this.data = data;
        }

        public String getTag() {
            return tag == null ? "" : tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class DataBeanXX {

            private String id;
            private String type;
            private String dataType;
            private HeaderBean header;
            private int count;
            private int duration;
            private String footer;
            private String title;
            private String actionUrl;
            private String playUrl;
            private String text;
            private String rightText;
            private String description;
            private String category;
            private String icon;
            private String image;
            private DetailBean detail;
            private ItemListBean.DataBeanX.ContentBean.DataBean.CoverBean cover;
            private ItemListBean.DataBeanX.ContentBean content;
            private ItemListBean.DataBeanX.ContentBean.DataBean.AuthorBean author;
            private List<ItemListBean> itemList;

            public String getId() {
                return id == null ? "" : id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public ItemListBean.DataBeanX.ContentBean.DataBean.AuthorBean getAuthor() {
                return author;
            }

            public void setAuthor(ItemListBean.DataBeanX.ContentBean.DataBean.AuthorBean author) {
                this.author = author;
            }

            public String getActionUrl() {
                return actionUrl == null ? "" : actionUrl;
            }

            public void setActionUrl(String actionUrl) {
                this.actionUrl = actionUrl;
            }

            public String getPlayUrl() {
                return playUrl == null ? "" : playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public String getDescription() {
                return description == null ? "" : description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon == null ? "" : icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public ItemListBean.DataBeanX.ContentBean.DataBean.CoverBean getCover() {
                return cover;
            }

            public void setCover(ItemListBean.DataBeanX.ContentBean.DataBean.CoverBean cover) {
                this.cover = cover;
            }

            public ItemListBean.DataBeanX.ContentBean getContent() {
                return content;
            }

            public void setContent(ItemListBean.DataBeanX.ContentBean content) {
                this.content = content;
            }

            public String getCategory() {
                return category == null ? "" : category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getDataType() {
                return dataType == null ? "" : dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text == null ? "" : text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getRightText() {
                return rightText == null ? "" : rightText;
            }

            public void setRightText(String rightText) {
                this.rightText = rightText;
            }

            public String getImage() {
                return image == null ? "" : image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public HeaderBean getHeader() {
                return header;
            }

            public void setHeader(HeaderBean header) {
                this.header = header;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getFooter() {
                return footer == null ? "" : footer;
            }

            public void setFooter(String footer) {
                this.footer = footer;
            }

            public List<ItemListBean> getItemList() {
                if (itemList == null) {
                    return new ArrayList<>();
                }
                return itemList;
            }

            public void setItemList(List<ItemListBean> itemList) {
                this.itemList = itemList;
            }

            public static class DetailBean {

                private String id;
                private String icon;
                private String title;
                private String description;
                private String url;
                private String adaptiveUrls;
                private String actionUrl;
                private String iosActionUrl;
                private String imageUrl;
                private String adaptiveImageUrls;
                private int displayTimeDuration;
                private int displayCount;
                private boolean showImage;
                private int showImageTime;
                private int loadingMode;
                private boolean countdown;
                private boolean canSkip;
                private int timeBeforeSkip;
                private boolean showActionButton;
                private String videoType;
                private String videoAdType;
                private int categoryId;
                private int position;

                public String getId() {
                    return id == null ? "" : id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getIcon() {
                    return icon == null ? "" : icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getTitle() {
                    return title == null ? "" : title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDescription() {
                    return description == null ? "" : description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getUrl() {
                    return url == null ? "" : url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getAdaptiveUrls() {
                    return adaptiveUrls == null ? "" : adaptiveUrls;
                }

                public void setAdaptiveUrls(String adaptiveUrls) {
                    this.adaptiveUrls = adaptiveUrls;
                }

                public String getActionUrl() {
                    return actionUrl == null ? "" : actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }

                public String getIosActionUrl() {
                    return iosActionUrl == null ? "" : iosActionUrl;
                }

                public void setIosActionUrl(String iosActionUrl) {
                    this.iosActionUrl = iosActionUrl;
                }

                public String getImageUrl() {
                    return imageUrl == null ? "" : imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getAdaptiveImageUrls() {
                    return adaptiveImageUrls == null ? "" : adaptiveImageUrls;
                }

                public void setAdaptiveImageUrls(String adaptiveImageUrls) {
                    this.adaptiveImageUrls = adaptiveImageUrls;
                }

                public int getDisplayTimeDuration() {
                    return displayTimeDuration;
                }

                public void setDisplayTimeDuration(int displayTimeDuration) {
                    this.displayTimeDuration = displayTimeDuration;
                }

                public int getDisplayCount() {
                    return displayCount;
                }

                public void setDisplayCount(int displayCount) {
                    this.displayCount = displayCount;
                }

                public boolean isShowImage() {
                    return showImage;
                }

                public void setShowImage(boolean showImage) {
                    this.showImage = showImage;
                }

                public int getShowImageTime() {
                    return showImageTime;
                }

                public void setShowImageTime(int showImageTime) {
                    this.showImageTime = showImageTime;
                }

                public int getLoadingMode() {
                    return loadingMode;
                }

                public void setLoadingMode(int loadingMode) {
                    this.loadingMode = loadingMode;
                }

                public boolean isCountdown() {
                    return countdown;
                }

                public void setCountdown(boolean countdown) {
                    this.countdown = countdown;
                }

                public boolean isCanSkip() {
                    return canSkip;
                }

                public void setCanSkip(boolean canSkip) {
                    this.canSkip = canSkip;
                }

                public int getTimeBeforeSkip() {
                    return timeBeforeSkip;
                }

                public void setTimeBeforeSkip(int timeBeforeSkip) {
                    this.timeBeforeSkip = timeBeforeSkip;
                }

                public boolean isShowActionButton() {
                    return showActionButton;
                }

                public void setShowActionButton(boolean showActionButton) {
                    this.showActionButton = showActionButton;
                }

                public String getVideoType() {
                    return videoType == null ? "" : videoType;
                }

                public void setVideoType(String videoType) {
                    this.videoType = videoType;
                }

                public String getVideoAdType() {
                    return videoAdType == null ? "" : videoAdType;
                }

                public void setVideoAdType(String videoAdType) {
                    this.videoAdType = videoAdType;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public int getPosition() {
                    return position;
                }

                public void setPosition(int position) {
                    this.position = position;
                }
            }

            public static class HeaderBean {
                /**
                 * id : 5
                 * title : 开眼编辑精选
                 * font : bigBold
                 * subTitle : WEDNESDAY, MAY 29
                 * subTitleFont : lobster
                 * textAlign : left
                 * cover : null
                 * label : null
                 * actionUrl : eyepetizer://homepage/selected?tabIndex=2&newTabIndex=-3
                 * labelList : null
                 * rightText : 查看往期
                 */

                private String id;
                private String title;
                private String font;
                private String subTitle;
                private String subTitleFont;
                private String textAlign;
                private String cover;
                private String actionUrl;
                private List<LabelListBean> labelList;
                private String rightText;

                public String getId() {
                    return id == null ? "" : id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title == null ? "" : title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getFont() {
                    return font == null ? "" : font;
                }

                public void setFont(String font) {
                    this.font = font;
                }

                public String getSubTitle() {
                    return subTitle == null ? "" : subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getSubTitleFont() {
                    return subTitleFont == null ? "" : subTitleFont;
                }

                public void setSubTitleFont(String subTitleFont) {
                    this.subTitleFont = subTitleFont;
                }

                public String getTextAlign() {
                    return textAlign == null ? "" : textAlign;
                }

                public void setTextAlign(String textAlign) {
                    this.textAlign = textAlign;
                }

                public String getCover() {
                    return cover == null ? "" : cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getActionUrl() {
                    return actionUrl == null ? "" : actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }

                public List<LabelListBean> getLabelList() {
                    if (labelList == null) {
                        return new ArrayList<>();
                    }
                    return labelList;
                }

                public void setLabelList(List<LabelListBean> labelList) {
                    this.labelList = labelList;
                }

                public String getRightText() {
                    return rightText == null ? "" : rightText;
                }

                public void setRightText(String rightText) {
                    this.rightText = rightText;
                }

                public static class LabelListBean {
                    /**
                     * text : 360°全景
                     * actionUrl : null
                     */

                    private String text;
                    private String actionUrl;

                    public String getText() {
                        return text == null ? "" : text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getActionUrl() {
                        return actionUrl == null ? "" : actionUrl;
                    }

                    public void setActionUrl(String actionUrl) {
                        this.actionUrl = actionUrl;
                    }
                }
            }

            public static class ItemListBean {

                private String type;
                private DataBeanX data;
                private String tag;
                private String id;
                private int adIndex;

                public String getType() {
                    return type == null ? "" : type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public DataBeanX getData() {
                    return data;
                }

                public void setData(DataBeanX data) {
                    this.data = data;
                }

                public String getTag() {
                    return tag == null ? "" : tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public String getId() {
                    return id == null ? "" : id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getAdIndex() {
                    return adIndex;
                }

                public void setAdIndex(int adIndex) {
                    this.adIndex = adIndex;
                }

                public static class DataBeanX {

                    private String dataType;
                    private HeaderBeanX header;
                    private ContentBean content;
                    private String title;
                    private String image;

                    public String getTitle() {
                        return title == null ? "" : title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getImage() {
                        return image == null ? "" : image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public String getDataType() {
                        return dataType == null ? "" : dataType;
                    }

                    public void setDataType(String dataType) {
                        this.dataType = dataType;
                    }

                    public HeaderBeanX getHeader() {
                        return header;
                    }

                    public void setHeader(HeaderBeanX header) {
                        this.header = header;
                    }

                    public ContentBean getContent() {
                        return content;
                    }

                    public void setContent(ContentBean content) {
                        this.content = content;
                    }

                    public static class HeaderBeanX {

                        private String id;
                        private String title;
                        private String font;
                        private String subTitle;
                        private String subTitleFont;
                        private String textAlign;
                        private String cover;
                        private String label;
                        private String actionUrl;
                        private List<LabelListBean> labelList;
                        private String rightText;
                        private String icon;
                        private String iconType;
                        private String description;
                        private long time;
                        private boolean showHateVideo;

                        public static class LabelListBean {
                        }

                        public String getId() {
                            return id == null ? "" : id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getTitle() {
                            return title == null ? "" : title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getFont() {
                            return font == null ? "" : font;
                        }

                        public void setFont(String font) {
                            this.font = font;
                        }

                        public String getSubTitle() {
                            return subTitle == null ? "" : subTitle;
                        }

                        public void setSubTitle(String subTitle) {
                            this.subTitle = subTitle;
                        }

                        public String getSubTitleFont() {
                            return subTitleFont == null ? "" : subTitleFont;
                        }

                        public void setSubTitleFont(String subTitleFont) {
                            this.subTitleFont = subTitleFont;
                        }

                        public String getTextAlign() {
                            return textAlign == null ? "" : textAlign;
                        }

                        public void setTextAlign(String textAlign) {
                            this.textAlign = textAlign;
                        }

                        public String getCover() {
                            return cover == null ? "" : cover;
                        }

                        public void setCover(String cover) {
                            this.cover = cover;
                        }

                        public String getLabel() {
                            return label == null ? "" : label;
                        }

                        public void setLabel(String label) {
                            this.label = label;
                        }

                        public String getActionUrl() {
                            return actionUrl == null ? "" : actionUrl;
                        }

                        public void setActionUrl(String actionUrl) {
                            this.actionUrl = actionUrl;
                        }

                        public List<LabelListBean> getLabelList() {
                            if (labelList == null) {
                                return new ArrayList<>();
                            }
                            return labelList;
                        }

                        public void setLabelList(List<LabelListBean> labelList) {
                            this.labelList = labelList;
                        }

                        public String getRightText() {
                            return rightText == null ? "" : rightText;
                        }

                        public void setRightText(String rightText) {
                            this.rightText = rightText;
                        }

                        public String getIcon() {
                            return icon == null ? "" : icon;
                        }

                        public void setIcon(String icon) {
                            this.icon = icon;
                        }

                        public String getIconType() {
                            return iconType == null ? "" : iconType;
                        }

                        public void setIconType(String iconType) {
                            this.iconType = iconType;
                        }

                        public String getDescription() {
                            return description == null ? "" : description;
                        }

                        public void setDescription(String description) {
                            this.description = description;
                        }

                        public long getTime() {
                            return time;
                        }

                        public void setTime(long time) {
                            this.time = time;
                        }

                        public boolean isShowHateVideo() {
                            return showHateVideo;
                        }

                        public void setShowHateVideo(boolean showHateVideo) {
                            this.showHateVideo = showHateVideo;
                        }
                    }

                    public static class ContentBean {

                        private String type;
                        private DataBean data;
                        private String tag;
                        private String id;
                        private int adIndex;

                        public String getType() {
                            return type == null ? "" : type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public DataBean getData() {
                            return data;
                        }

                        public void setData(DataBean data) {
                            this.data = data;
                        }

                        public String getTag() {
                            return tag == null ? "" : tag;
                        }

                        public void setTag(String tag) {
                            this.tag = tag;
                        }

                        public String getId() {
                            return id == null ? "" : id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public int getAdIndex() {
                            return adIndex;
                        }

                        public void setAdIndex(int adIndex) {
                            this.adIndex = adIndex;
                        }

                        public static class DataBean {

                            private String dataType;
                            private String id;
                            private String title;
                            private String description;
                            private String library;
                            private ConsumptionBean consumption;
                            private String resourceType;
                            private String slogan;
                            private ProviderBean provider;
                            private String category;
                            private AuthorBean author;
                            private CoverBean cover;
                            private String playUrl;
                            private String thumbPlayUrl;
                            private int duration;
                            private WebUrlBean webUrl;
                            private long releaseTime;
                            private String campaign;
                            private String waterMarks;
                            private boolean ad;
                            private String type;
                            private String titlePgc;
                            private String descriptionPgc;
                            private String remark;
                            private boolean ifLimitVideo;
                            private int searchWeight;
                            private int idx;
                            private String shareAdTrack;
                            private String favoriteAdTrack;
                            private String webAdTrack;
                            private long date;
                            private String promotion;
                            private LabelBean label;
                            private String descriptionEditor;
                            private boolean collected;
                            private boolean played;
                            private String lastViewTime;
                            private String playlists;
                            private String src;
                            private OwnerBean owner;
                            private List<TagsBean> tags;
                            private List<PlayInfoBean> playInfo;
                            private List<LabelListBean> labelList;
                            private List<String> subtitles;

                            public static class LabelListBean {
                                private String text;
                                private String actionUrl;

                                public String getText() {
                                    return text == null ? "" : text;
                                }

                                public void setText(String text) {
                                    this.text = text;
                                }

                                public String getActionUrl() {
                                    return actionUrl == null ? "" : actionUrl;
                                }

                                public void setActionUrl(String actionUrl) {
                                    this.actionUrl = actionUrl;
                                }
                            }

                            public OwnerBean getOwner() {
                                return owner;
                            }

                            public void setOwner(OwnerBean owner) {
                                this.owner = owner;
                            }

                            public String getDataType() {
                                return dataType == null ? "" : dataType;
                            }

                            public void setDataType(String dataType) {
                                this.dataType = dataType;
                            }

                            public String getId() {
                                return id == null ? "" : id;
                            }

                            public void setId(String id) {
                                this.id = id;
                            }

                            public String getTitle() {
                                return title == null ? "" : title;
                            }

                            public void setTitle(String title) {
                                this.title = title;
                            }

                            public String getDescription() {
                                return description == null ? "" : description;
                            }

                            public void setDescription(String description) {
                                this.description = description;
                            }

                            public String getLibrary() {
                                return library == null ? "" : library;
                            }

                            public void setLibrary(String library) {
                                this.library = library;
                            }

                            public ConsumptionBean getConsumption() {
                                return consumption;
                            }

                            public void setConsumption(ConsumptionBean consumption) {
                                this.consumption = consumption;
                            }

                            public String getResourceType() {
                                return resourceType == null ? "" : resourceType;
                            }

                            public void setResourceType(String resourceType) {
                                this.resourceType = resourceType;
                            }

                            public String getSlogan() {
                                return slogan == null ? "" : slogan;
                            }

                            public void setSlogan(String slogan) {
                                this.slogan = slogan;
                            }

                            public ProviderBean getProvider() {
                                return provider;
                            }

                            public void setProvider(ProviderBean provider) {
                                this.provider = provider;
                            }

                            public String getCategory() {
                                return category == null ? "" : category;
                            }

                            public void setCategory(String category) {
                                this.category = category;
                            }

                            public AuthorBean getAuthor() {
                                return author;
                            }

                            public void setAuthor(AuthorBean author) {
                                this.author = author;
                            }

                            public CoverBean getCover() {
                                return cover;
                            }

                            public void setCover(CoverBean cover) {
                                this.cover = cover;
                            }

                            public String getPlayUrl() {
                                return playUrl == null ? "" : playUrl;
                            }

                            public void setPlayUrl(String playUrl) {
                                this.playUrl = playUrl;
                            }

                            public String getThumbPlayUrl() {
                                return thumbPlayUrl == null ? "" : thumbPlayUrl;
                            }

                            public void setThumbPlayUrl(String thumbPlayUrl) {
                                this.thumbPlayUrl = thumbPlayUrl;
                            }

                            public int getDuration() {
                                return duration;
                            }

                            public void setDuration(int duration) {
                                this.duration = duration;
                            }

                            public WebUrlBean getWebUrl() {
                                return webUrl;
                            }

                            public void setWebUrl(WebUrlBean webUrl) {
                                this.webUrl = webUrl;
                            }

                            public long getReleaseTime() {
                                return releaseTime;
                            }

                            public void setReleaseTime(long releaseTime) {
                                this.releaseTime = releaseTime;
                            }

                            public String getCampaign() {
                                return campaign == null ? "" : campaign;
                            }

                            public void setCampaign(String campaign) {
                                this.campaign = campaign;
                            }

                            public String getWaterMarks() {
                                return waterMarks == null ? "" : waterMarks;
                            }

                            public void setWaterMarks(String waterMarks) {
                                this.waterMarks = waterMarks;
                            }

                            public boolean isAd() {
                                return ad;
                            }

                            public void setAd(boolean ad) {
                                this.ad = ad;
                            }

                            public String getType() {
                                return type == null ? "" : type;
                            }

                            public void setType(String type) {
                                this.type = type;
                            }

                            public String getTitlePgc() {
                                return titlePgc == null ? "" : titlePgc;
                            }

                            public void setTitlePgc(String titlePgc) {
                                this.titlePgc = titlePgc;
                            }

                            public String getDescriptionPgc() {
                                return descriptionPgc == null ? "" : descriptionPgc;
                            }

                            public void setDescriptionPgc(String descriptionPgc) {
                                this.descriptionPgc = descriptionPgc;
                            }

                            public String getRemark() {
                                return remark == null ? "" : remark;
                            }

                            public void setRemark(String remark) {
                                this.remark = remark;
                            }

                            public boolean isIfLimitVideo() {
                                return ifLimitVideo;
                            }

                            public void setIfLimitVideo(boolean ifLimitVideo) {
                                this.ifLimitVideo = ifLimitVideo;
                            }

                            public int getSearchWeight() {
                                return searchWeight;
                            }

                            public void setSearchWeight(int searchWeight) {
                                this.searchWeight = searchWeight;
                            }

                            public int getIdx() {
                                return idx;
                            }

                            public void setIdx(int idx) {
                                this.idx = idx;
                            }

                            public String getShareAdTrack() {
                                return shareAdTrack == null ? "" : shareAdTrack;
                            }

                            public void setShareAdTrack(String shareAdTrack) {
                                this.shareAdTrack = shareAdTrack;
                            }

                            public String getFavoriteAdTrack() {
                                return favoriteAdTrack == null ? "" : favoriteAdTrack;
                            }

                            public void setFavoriteAdTrack(String favoriteAdTrack) {
                                this.favoriteAdTrack = favoriteAdTrack;
                            }

                            public String getWebAdTrack() {
                                return webAdTrack == null ? "" : webAdTrack;
                            }

                            public void setWebAdTrack(String webAdTrack) {
                                this.webAdTrack = webAdTrack;
                            }

                            public long getDate() {
                                return date;
                            }

                            public void setDate(long date) {
                                this.date = date;
                            }

                            public String getPromotion() {
                                return promotion == null ? "" : promotion;
                            }

                            public void setPromotion(String promotion) {
                                this.promotion = promotion;
                            }

                            public LabelBean getLabel() {
                                return label;
                            }

                            public void setLabel(LabelBean label) {
                                this.label = label;
                            }

                            public String getDescriptionEditor() {
                                return descriptionEditor == null ? "" : descriptionEditor;
                            }

                            public void setDescriptionEditor(String descriptionEditor) {
                                this.descriptionEditor = descriptionEditor;
                            }

                            public boolean isCollected() {
                                return collected;
                            }

                            public void setCollected(boolean collected) {
                                this.collected = collected;
                            }

                            public boolean isPlayed() {
                                return played;
                            }

                            public void setPlayed(boolean played) {
                                this.played = played;
                            }

                            public String getLastViewTime() {
                                return lastViewTime == null ? "" : lastViewTime;
                            }

                            public void setLastViewTime(String lastViewTime) {
                                this.lastViewTime = lastViewTime;
                            }

                            public String getPlaylists() {
                                return playlists == null ? "" : playlists;
                            }

                            public void setPlaylists(String playlists) {
                                this.playlists = playlists;
                            }

                            public String getSrc() {
                                return src == null ? "" : src;
                            }

                            public void setSrc(String src) {
                                this.src = src;
                            }

                            public List<TagsBean> getTags() {
                                if (tags == null) {
                                    return new ArrayList<>();
                                }
                                return tags;
                            }

                            public void setTags(List<TagsBean> tags) {
                                this.tags = tags;
                            }

                            public List<PlayInfoBean> getPlayInfo() {
                                if (playInfo == null) {
                                    return new ArrayList<>();
                                }
                                return playInfo;
                            }

                            public void setPlayInfo(List<PlayInfoBean> playInfo) {
                                this.playInfo = playInfo;
                            }

                            public List<LabelListBean> getLabelList() {
                                if (labelList == null) {
                                    return new ArrayList<>();
                                }
                                return labelList;
                            }

                            public void setLabelList(List<LabelListBean> labelList) {
                                this.labelList = labelList;
                            }

                            public List<String> getSubtitles() {
                                if (subtitles == null) {
                                    return new ArrayList<>();
                                }
                                return subtitles;
                            }

                            public void setSubtitles(List<String> subtitles) {
                                this.subtitles = subtitles;
                            }

                            public static class ConsumptionBean {
                                /**
                                 * collectionCount : 1064
                                 * shareCount : 479
                                 * replyCount : 33
                                 */

                                private int collectionCount;
                                private int shareCount;
                                private int replyCount;

                                public int getCollectionCount() {
                                    return collectionCount;
                                }

                                public void setCollectionCount(int collectionCount) {
                                    this.collectionCount = collectionCount;
                                }

                                public int getShareCount() {
                                    return shareCount;
                                }

                                public void setShareCount(int shareCount) {
                                    this.shareCount = shareCount;
                                }

                                public int getReplyCount() {
                                    return replyCount;
                                }

                                public void setReplyCount(int replyCount) {
                                    this.replyCount = replyCount;
                                }
                            }

                            public static class ProviderBean {
                                /**
                                 * name : YouTube
                                 * alias : youtube
                                 * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                                 */

                                private String name;
                                private String alias;
                                private String icon;

                                public String getName() {
                                    return name == null ? "" : name;
                                }

                                public void setName(String name) {
                                    this.name = name;
                                }

                                public String getAlias() {
                                    return alias == null ? "" : alias;
                                }

                                public void setAlias(String alias) {
                                    this.alias = alias;
                                }

                                public String getIcon() {
                                    return icon == null ? "" : icon;
                                }

                                public void setIcon(String icon) {
                                    this.icon = icon;
                                }
                            }

                            public static class OwnerBean {
                                private String nickname;
                                private String avatar;

                                public String getNickname() {
                                    return nickname == null ? "" : nickname;
                                }

                                public void setNickname(String nickname) {
                                    this.nickname = nickname;
                                }

                                public String getAvatar() {
                                    return avatar == null ? "" : avatar;
                                }

                                public void setAvatar(String avatar) {
                                    this.avatar = avatar;
                                }
                            }

                            public static class AuthorBean {
                                /**
                                 * id : 4485
                                 * icon : http://img.kaiyanapp.com/bc0e2735e3da488efa7fbab5492a168d.png?imageMogr2/quality/60/format/jpg
                                 * name : TheGaroStudios
                                 * description : 油管影视混剪博主
                                 * link : https://www.youtube.com/user/TheGaroStudios/videos
                                 * latestReleaseTime : 1559091601000
                                 * videoNum : 9
                                 * follow : {"itemType":"author","itemId":4485,"followed":false}
                                 * shield : {"itemType":"author","itemId":4485,"shielded":false}
                                 * approvedNotReadyVideoCount : 0
                                 * ifPgc : true
                                 * recSort : 0
                                 * expert : false
                                 */

                                private String id;
                                private String icon;
                                private String name;
                                private String description;
                                private String link;
                                private long latestReleaseTime;
                                private int videoNum;
                                private FollowBean follow;
                                private ShieldBean shield;
                                private int approvedNotReadyVideoCount;
                                private boolean ifPgc;
                                private int recSort;
                                private boolean expert;

                                public String getId() {
                                    return id == null ? "" : id;
                                }

                                public void setId(String id) {
                                    this.id = id;
                                }

                                public String getIcon() {
                                    return icon == null ? "" : icon;
                                }

                                public void setIcon(String icon) {
                                    this.icon = icon;
                                }

                                public String getName() {
                                    return name == null ? "" : name;
                                }

                                public void setName(String name) {
                                    this.name = name;
                                }

                                public String getDescription() {
                                    return description == null ? "" : description;
                                }

                                public void setDescription(String description) {
                                    this.description = description;
                                }

                                public String getLink() {
                                    return link == null ? "" : link;
                                }

                                public void setLink(String link) {
                                    this.link = link;
                                }

                                public long getLatestReleaseTime() {
                                    return latestReleaseTime;
                                }

                                public void setLatestReleaseTime(long latestReleaseTime) {
                                    this.latestReleaseTime = latestReleaseTime;
                                }

                                public int getVideoNum() {
                                    return videoNum;
                                }

                                public void setVideoNum(int videoNum) {
                                    this.videoNum = videoNum;
                                }

                                public FollowBean getFollow() {
                                    return follow;
                                }

                                public void setFollow(FollowBean follow) {
                                    this.follow = follow;
                                }

                                public ShieldBean getShield() {
                                    return shield;
                                }

                                public void setShield(ShieldBean shield) {
                                    this.shield = shield;
                                }

                                public int getApprovedNotReadyVideoCount() {
                                    return approvedNotReadyVideoCount;
                                }

                                public void setApprovedNotReadyVideoCount(int approvedNotReadyVideoCount) {
                                    this.approvedNotReadyVideoCount = approvedNotReadyVideoCount;
                                }

                                public boolean isIfPgc() {
                                    return ifPgc;
                                }

                                public void setIfPgc(boolean ifPgc) {
                                    this.ifPgc = ifPgc;
                                }

                                public int getRecSort() {
                                    return recSort;
                                }

                                public void setRecSort(int recSort) {
                                    this.recSort = recSort;
                                }

                                public boolean isExpert() {
                                    return expert;
                                }

                                public void setExpert(boolean expert) {
                                    this.expert = expert;
                                }

                                public static class FollowBean {
                                    /**
                                     * itemType : author
                                     * itemId : 4485
                                     * followed : false
                                     */

                                    private String itemType;
                                    private int itemId;
                                    private boolean followed;

                                    public String getItemType() {
                                        return itemType == null ? "" : itemType;
                                    }

                                    public void setItemType(String itemType) {
                                        this.itemType = itemType;
                                    }

                                    public int getItemId() {
                                        return itemId;
                                    }

                                    public void setItemId(int itemId) {
                                        this.itemId = itemId;
                                    }

                                    public boolean isFollowed() {
                                        return followed;
                                    }

                                    public void setFollowed(boolean followed) {
                                        this.followed = followed;
                                    }
                                }

                                public static class ShieldBean {
                                    /**
                                     * itemType : author
                                     * itemId : 4485
                                     * shielded : false
                                     */

                                    private String itemType;
                                    private int itemId;
                                    private boolean shielded;

                                    public String getItemType() {
                                        return itemType == null ? "" : itemType;
                                    }

                                    public void setItemType(String itemType) {
                                        this.itemType = itemType;
                                    }

                                    public int getItemId() {
                                        return itemId;
                                    }

                                    public void setItemId(int itemId) {
                                        this.itemId = itemId;
                                    }

                                    public boolean isShielded() {
                                        return shielded;
                                    }

                                    public void setShielded(boolean shielded) {
                                        this.shielded = shielded;
                                    }
                                }
                            }

                            public static class CoverBean {
                                /**
                                 * feed : http://img.kaiyanapp.com/a324c1e8607a78871fd01ae14cad91b5.jpeg?imageMogr2/quality/60/format/jpg
                                 * detail : http://img.kaiyanapp.com/a324c1e8607a78871fd01ae14cad91b5.jpeg?imageMogr2/quality/60/format/jpg
                                 * blurred : http://img.kaiyanapp.com/5c8934d4d4b256b47651b8ae9ce592d9.jpeg?imageMogr2/quality/60/format/jpg
                                 * sharing : null
                                 * homepage : http://img.kaiyanapp.com/a324c1e8607a78871fd01ae14cad91b5.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                                 */

                                private String feed;
                                private String detail;
                                private String blurred;
                                private String sharing;
                                private String homepage;

                                public String getFeed() {
                                    return feed == null ? "" : feed;
                                }

                                public void setFeed(String feed) {
                                    this.feed = feed;
                                }

                                public String getDetail() {
                                    return detail == null ? "" : detail;
                                }

                                public void setDetail(String detail) {
                                    this.detail = detail;
                                }

                                public String getBlurred() {
                                    return blurred == null ? "" : blurred;
                                }

                                public void setBlurred(String blurred) {
                                    this.blurred = blurred;
                                }

                                public String getSharing() {
                                    return sharing == null ? "" : sharing;
                                }

                                public void setSharing(String sharing) {
                                    this.sharing = sharing;
                                }

                                public String getHomepage() {
                                    return homepage == null ? "" : homepage;
                                }

                                public void setHomepage(String homepage) {
                                    this.homepage = homepage;
                                }
                            }

                            public static class WebUrlBean {
                                /**
                                 * raw : http://www.eyepetizer.net/detail.html?vid=158108
                                 * forWeibo : http://www.eyepetizer.net/detail.html?vid=158108&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                                 */

                                private String raw;
                                private String forWeibo;

                                public String getRaw() {
                                    return raw == null ? "" : raw;
                                }

                                public void setRaw(String raw) {
                                    this.raw = raw;
                                }

                                public String getForWeibo() {
                                    return forWeibo == null ? "" : forWeibo;
                                }

                                public void setForWeibo(String forWeibo) {
                                    this.forWeibo = forWeibo;
                                }
                            }

                            public static class LabelBean {
                                /**
                                 * text : 360°全景
                                 * card : 360°全景
                                 * detail : 360°全景
                                 */

                                private String text;
                                private String card;
                                private String detail;

                                public String getText() {
                                    return text;
                                }

                                public void setText(String text) {
                                    this.text = text;
                                }

                                public String getCard() {
                                    return card;
                                }

                                public void setCard(String card) {
                                    this.card = card;
                                }

                                public String getDetail() {
                                    return detail;
                                }

                                public void setDetail(String detail) {
                                    this.detail = detail;
                                }
                            }

                            public static class TagsBean {
                                /**
                                 * id : 796
                                 * name : 迷影放映室
                                 * actionUrl : eyepetizer://tag/796/?title=%E8%BF%B7%E5%BD%B1%E6%94%BE%E6%98%A0%E5%AE%A4
                                 * desc : 电影、剧集、戏剧抢先看
                                 * bgPicture : http://img.kaiyanapp.com/64f2b2ed039bd92c3be10d003d6041bf.jpeg?imageMogr2/quality/60/format/jpg
                                 * headerImage : http://img.kaiyanapp.com/56a8818adb038c59ab04ffc781db2f50.jpeg?imageMogr2/quality/60/format/jpg
                                 * tagRecType : IMPORTANT
                                 * childTagList : null
                                 * childTagIdList : null
                                 * communityIndex : 0
                                 */

                                private String id;
                                private String name;
                                private String actionUrl;
                                private String desc;
                                private String bgPicture;
                                private String headerImage;
                                private String tagRecType;
                                private int communityIndex;

                                public String getId() {
                                    return id == null ? "" : id;
                                }

                                public void setId(String id) {
                                    this.id = id;
                                }

                                public String getName() {
                                    return name == null ? "" : name;
                                }

                                public void setName(String name) {
                                    this.name = name;
                                }

                                public String getActionUrl() {
                                    return actionUrl == null ? "" : actionUrl;
                                }

                                public void setActionUrl(String actionUrl) {
                                    this.actionUrl = actionUrl;
                                }

                                public String getDesc() {
                                    return desc == null ? "" : desc;
                                }

                                public void setDesc(String desc) {
                                    this.desc = desc;
                                }

                                public String getBgPicture() {
                                    return bgPicture == null ? "" : bgPicture;
                                }

                                public void setBgPicture(String bgPicture) {
                                    this.bgPicture = bgPicture;
                                }

                                public String getHeaderImage() {
                                    return headerImage == null ? "" : headerImage;
                                }

                                public void setHeaderImage(String headerImage) {
                                    this.headerImage = headerImage;
                                }

                                public String getTagRecType() {
                                    return tagRecType == null ? "" : tagRecType;
                                }

                                public void setTagRecType(String tagRecType) {
                                    this.tagRecType = tagRecType;
                                }

                                public int getCommunityIndex() {
                                    return communityIndex;
                                }

                                public void setCommunityIndex(int communityIndex) {
                                    this.communityIndex = communityIndex;
                                }
                            }

                            public static class PlayInfoBean {
                                /**
                                 * height : 270
                                 * width : 480
                                 * urlList : [{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=158108&resourceType=video&editionType=low&source=aliyun&playUrlType=url_oss","size":6533315},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=158108&resourceType=video&editionType=low&source=qcloud&playUrlType=url_oss","size":6533315},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=158108&resourceType=video&editionType=low&source=ucloud&playUrlType=url_oss","size":6533315}]
                                 * name : 流畅
                                 * type : low
                                 * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=158108&resourceType=video&editionType=low&source=aliyun&playUrlType=url_oss
                                 */

                                private int height;
                                private int width;
                                private String name;
                                private String type;
                                private String url;
                                private List<UrlListBean> urlList;

                                public int getHeight() {
                                    return height;
                                }

                                public void setHeight(int height) {
                                    this.height = height;
                                }

                                public int getWidth() {
                                    return width;
                                }

                                public void setWidth(int width) {
                                    this.width = width;
                                }

                                public String getName() {
                                    return name == null ? "" : name;
                                }

                                public void setName(String name) {
                                    this.name = name;
                                }

                                public String getType() {
                                    return type == null ? "" : type;
                                }

                                public void setType(String type) {
                                    this.type = type;
                                }

                                public String getUrl() {
                                    return url == null ? "" : url;
                                }

                                public void setUrl(String url) {
                                    this.url = url;
                                }

                                public List<UrlListBean> getUrlList() {
                                    if (urlList == null) {
                                        return new ArrayList<>();
                                    }
                                    return urlList;
                                }

                                public void setUrlList(List<UrlListBean> urlList) {
                                    this.urlList = urlList;
                                }

                                public static class UrlListBean {
                                    /**
                                     * name : aliyun
                                     * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=158108&resourceType=video&editionType=low&source=aliyun&playUrlType=url_oss
                                     * size : 6533315
                                     */

                                    private String name;
                                    private String url;
                                    private int size;

                                    public String getName() {
                                        return name == null ? "" : name;
                                    }

                                    public void setName(String name) {
                                        this.name = name;
                                    }

                                    public String getUrl() {
                                        return url == null ? "" : url;
                                    }

                                    public void setUrl(String url) {
                                        this.url = url;
                                    }

                                    public int getSize() {
                                        return size;
                                    }

                                    public void setSize(int size) {
                                        this.size = size;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
