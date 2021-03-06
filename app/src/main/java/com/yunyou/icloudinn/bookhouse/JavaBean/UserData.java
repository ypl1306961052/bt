package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;

public class UserData implements Serializable {

    private int yunsu_id;
    private String account;
    private String nickname;
    private String head_img_url;
    private String sex;
    private String age;
    private String city;
    private String phone;
    private String access_token;
    private int point;
    private int rent_book_num;
    private int donate_book_num;
    private int check_in_hotel_num;
    private int is_concern;

    public int getYunsu_id() {
        return yunsu_id;
    }

    public void setYunsu_id(int yunsu_id) {
        this.yunsu_id = yunsu_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {return age;}

    public void setAge(String age) {this.age = age;}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRent_book_num() {
        return rent_book_num;
    }

    public void setRent_book_num(int rent_book_num) {
        this.rent_book_num = rent_book_num;
    }

    public int getDonate_book_num() {
        return donate_book_num;
    }

    public void setDonate_book_num(int donate_book_num) {
        this.donate_book_num = donate_book_num;
    }

    public int getCheck_in_hotel_num() {
        return check_in_hotel_num;
    }

    public void setCheck_in_hotel_num(int check_in_hotel_num) {
        this.check_in_hotel_num = check_in_hotel_num;
    }

    public int getIs_concern() {
        return is_concern;
    }

    public void setIs_concern(int is_concern) {
        this.is_concern = is_concern;
    }
}
