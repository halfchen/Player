package com.chen.playerdemo.bean.tools;

/**
 * Created by chenbin
 * 2019-7-18
 **/
public class ToolsData {

    private int resId;
    private String name;

    public ToolsData(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
