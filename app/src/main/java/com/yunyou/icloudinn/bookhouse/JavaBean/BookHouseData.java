package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by chen on 2017/10/20.
 */

public class BookHouseData {

    /**
     * id : 1
     * child_id : 2
     * name : 四海书屋
     * intro : <p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a
     * open_time :
     * lat : 0
     * lng : 0
     * location : 琼中县西河路
     * stutuse : 1
     * create_time : 0
     */

    private int id;
    private int child_id;
    private String name;
    private String intro;
    private String open_time;
    private int lat;
    private int lng;
    private String location;
    private int stutuse;
    private int create_time;

    public void setId(int id) {
        this.id = id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStutuse(int stutuse) {
        this.stutuse = stutuse;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public int getChild_id() {
        return child_id;
    }

    public String getName() {
        return name;
    }

    public String getIntro() {
        return intro;
    }

    public String getOpen_time() {
        return open_time;
    }

    public int getLat() {
        return lat;
    }

    public int getLng() {
        return lng;
    }

    public String getLocation() {
        return location;
    }

    public int getStutuse() {
        return stutuse;
    }

    public int getCreate_time() {
        return create_time;
    }
}
