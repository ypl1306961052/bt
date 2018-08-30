package com.yunyou.icloudinn.bookhouse.JavaBean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by chen on 2017/10/11.
 */

public class BookData {

    /**
     * book_id : 68
     * name : 外婆的道歉信
     * cover_img : /uploads/20170621/03fd18eb4f9d0a7aabd09a60026c66d5.jpg
     * rent_num : 0
     * read_num : 135
     */

    @JSONField(name="book_id")
    private int bookId;
    private String name;
    @JSONField(name="cover_img")
    private String coverImg;
    @JSONField(name="rent_num")
    private int rentNum;
    @JSONField(name="read_num")
    private int readNum;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getRentNum() {
        return rentNum;
    }

    public void setRentNum(int rentNum) {
        this.rentNum = rentNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }
}
