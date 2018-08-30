package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by chen on 2017/10/20.
 */

public class BookModelData {

    /**
     * id : 18
     * name : 人性的弱点
     * auther :  戴尔·卡耐基
     * edition : 无
     * nationality : [美]
     * cover_img : /uploads/20170531/0e0b27e5938cbf59fb2367ee37cfd8c9.jpg
     * publisher : 中国发展出版社
     * publish_time : null
     * category_id : 5
     * intro : <p>适当放松的方式的 &nbsp; &nbsp; &nbsp;</p>
     * create_time : 1496200858
     * update_time : 1501810668
     */

    private int id;
    private String name;
    private String auther;
    private String edition;
    private String nationality;
    private String cover_img;
    private String publisher;
    private String publish_time;
    private int category_id;
    private String intro;
    private int create_time;
    private int update_time;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuther() {
        return auther;
    }

    public String getEdition() {
        return edition;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCover_img() {
        return cover_img;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getIntro() {
        return intro;
    }

    public int getCreate_time() {
        return create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }
}
