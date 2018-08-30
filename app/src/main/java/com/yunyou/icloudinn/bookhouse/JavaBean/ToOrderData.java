package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;

public class ToOrderData implements Serializable {
    private String name;
    private String type;
    private String picture;
    private String reComment;
    private int roomId;
    private int id;
    private int price;




    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getReComment() {
        return reComment;
    }

    public void setReComment(String reComment) {
        this.reComment = reComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
