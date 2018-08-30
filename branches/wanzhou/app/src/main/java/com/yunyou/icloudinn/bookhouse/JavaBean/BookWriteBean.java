package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;
import java.util.List;

public class BookWriteBean implements Serializable {

    private int id;
    private String name;
    private String cover;
    private String author;
    private String createTime;
    private List<BookWriteChapterBean> chapter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<BookWriteChapterBean> getChapter() {
        return chapter;
    }

    public void setChapter(List<BookWriteChapterBean> chapter) {
        this.chapter = chapter;
    }
}
