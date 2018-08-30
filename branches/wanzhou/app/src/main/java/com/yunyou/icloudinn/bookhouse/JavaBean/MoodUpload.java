package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class MoodUpload  {

    /**
     * title : sdf
     * location : sdf
     * imgs : ["sdfsd","sdfsd"]
     */

    private String title;
    private String location;
    private List<String> imgs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
