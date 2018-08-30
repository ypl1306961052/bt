package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by chen on 2017/10/20.
 */

public class HotelOrderData {

    /**
     * arrive_time : 1509062400
     * create_time : 1508483788
     * deduction_money : -348350
     * hotel_id : 21
     * hotel_img : /uploads/20170531/af7709516756721acd86f8631565d473.png
     * hotel_name : 曼陀罗客栈曼达听涛
     * hotel_order_id : 607
     * leave_time : 1509148800
     * pay_money : 700
     * total_money : -347650
     * update_time : 1508483788
     * user_id : 12143018
     */

    private String hotel_order_id;
    private String arrive_time;
    private int create_time;
    private int deduction_money;
    private int status;
    private String hotel_id;
    private String hotel_img;
    private String hotel_name;
    private String leave_time;
    private int pay_money;
    private int total_money;
    private int update_time;
    private int user_id;

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public void setDeduction_money(int deduction_money) {
        this.deduction_money = deduction_money;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public void setHotel_img(String hotel_img) {
        this.hotel_img = hotel_img;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public void setHotel_order_id(String hotel_order_id) {
        this.hotel_order_id = hotel_order_id;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public void setPay_money(int pay_money) {
        this.pay_money = pay_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public int getDeduction_money() {
        return deduction_money;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public String getHotel_img() {
        return hotel_img;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public String getHotel_order_id() {
        return hotel_order_id;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public int getPay_money() {
        return pay_money;
    }

    public int getTotal_money() {
        return total_money;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public int getUser_id() {
        return user_id;
    }
}
