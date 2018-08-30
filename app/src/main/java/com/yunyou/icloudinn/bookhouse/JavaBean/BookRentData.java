package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by chen on 2017/10/20.
 */

public class BookRentData {

    /**
     * book_id : 59
     * user_id : 12143018
     * user_name : 无昵称
     * guaranty_money : 0.1
     * rent_time : 1508493731
     * id : 269
     */

    private String book_id;
    private int user_id;
    private String user_name;
    private double guaranty_money;
    private int rent_time;
    private String id;
    private String head_img;

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setGuaranty_money(double guaranty_money) {
        this.guaranty_money = guaranty_money;
    }

    public void setRent_time(int rent_time) {
        this.rent_time = rent_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_id() {
        return book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public double getGuaranty_money() {
        return guaranty_money;
    }

    public int getRent_time() {
        return rent_time;
    }

    public String getId() {
        return id;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
