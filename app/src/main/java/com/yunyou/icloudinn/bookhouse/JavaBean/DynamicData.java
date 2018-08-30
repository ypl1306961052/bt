package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by chen on 2017/10/12.
 */

public class DynamicData {

    /**
     * id : 261
     * user_id : 12143049
     * user_name :
     * user_head_img : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRgRWWDAvicT9ywuytQzXFba8z4ljoo0X3MlUkwoGuChE6HV5yWuyxD3KMPyH22h0PDcM6r15dJ1A/0
     * source_id : 76
     * type : 4
     * action : 租了一本书：《Angular JS》
     * location : null
     * praise_num : 0
     * create_time : 1503307558
     * update_time : 1503307558
     * book_donate : null
     */

    private int id;
    private int user_id;
    private String user_name;
    private String user_head_img;
    private int source_id;
    private int type;
    private String action;
    private Object location;
    private int praise_num;
    private int create_time;
    private int update_time;
    private Object book_donate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
        this.praise_num = praise_num;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public Object getBook_donate() {
        return book_donate;
    }

    public void setBook_donate(Object book_donate) {
        this.book_donate = book_donate;
    }
}
