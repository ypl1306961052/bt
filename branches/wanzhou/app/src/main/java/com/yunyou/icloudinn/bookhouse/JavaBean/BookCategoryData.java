package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class BookCategoryData {

    /**
     * code : 100
     * msg : success
     * data : [{"id":62,"name":"设计心理学","cover_img":"/uploads/20170531/e663a4c43541b6f7ba3f65ede7df3733.jpg"},{"id":63,"name":"情感化设计","cover_img":"/uploads/20170531/2498614ae5a975b77731ef12b1e71af5.jpg"},{"id":64,"name":"认识电影","cover_img":"/uploads/20170531/a3838df0e041b758414214116ca4b186.jpg"},{"id":66,"name":"封闭式车库","cover_img":"/uploads/20170531/6e321062889d3cb61d33ef161cb6e147.jpg"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 62
         * name : 设计心理学
         * cover_img : /uploads/20170531/e663a4c43541b6f7ba3f65ede7df3733.jpg
         */

        private int id;
        private String name;
        private String cover_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }
    }
}
