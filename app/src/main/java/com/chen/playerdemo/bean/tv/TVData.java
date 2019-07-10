package com.chen.playerdemo.bean.tv;

/**
 * Created by chenbin
 * 2019-6-12
 **/
public class TVData {

    private String name;
    private String url;
    private int colorId;

    public TVData(String name, String url, int colorId) {
        this.name = name;
        this.url = url;
        this.colorId = colorId;
    }

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

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
