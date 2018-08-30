package com.yunyou.icloudinn.bookhouse.JavaBean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2017/10/11.
 */

public class BookData implements Serializable {

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
    private String price;
    @JSONField(name="cover_img")
    private String coverImg;
    @JSONField(name="rent_num")
    private int rentNum;
    @JSONField(name="read_num")
    private int readNum;
    private boolean is_collect;
    private BookModelData model;
    private BookHouseData book_house;
    private List<CommentBook> comment;
    private List<BookRentData> book_rent;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public boolean is_collect() {
        return is_collect;
    }

    public void setIs_collect(boolean is_collect) {
        this.is_collect = is_collect;
    }

    public BookModelData getModel() {
        return model;
    }

    public void setModel(BookModelData model) {
        this.model = model;
    }

    public BookHouseData getBook_house() {
        return book_house;
    }

    public void setBook_house(BookHouseData book_house) {
        this.book_house = book_house;
    }

    public List<CommentBook> getComment() {
        return comment;
    }

    public void setComment(List<CommentBook> comment) {
        this.comment = comment;
    }

    public List<BookRentData> getBook_rent() {
        return book_rent;
    }

    public void setBook_rent(List<BookRentData> book_rent) {
        this.book_rent = book_rent;
    }
}
