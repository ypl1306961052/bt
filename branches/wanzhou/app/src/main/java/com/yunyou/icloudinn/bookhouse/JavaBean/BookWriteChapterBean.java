package com.yunyou.icloudinn.bookhouse.JavaBean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class BookWriteChapterBean implements Serializable {

    private int id;

    @JSONField(name="book_write_id")
    private int bookWriteId;

    private String title;
    private int sequence;
    private String content;

    @JSONField (name = "create_time",format="yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @JSONField (name="update_time",format="yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookWriteId() {
        return bookWriteId;
    }

    public void setBookWriteId(int bookWriteId) {
        this.bookWriteId = bookWriteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
