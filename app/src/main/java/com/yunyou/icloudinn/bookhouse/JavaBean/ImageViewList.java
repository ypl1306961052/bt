package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class ImageViewList {
    /**
     * code : 100
     * msg : success
     * data : {"total":7,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":1,"child_id":2,"name":"四海书屋","intro":"<p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a","open_time":"","lat":19.553354263306,"lng":109.65609741211,"location":"琼中县西河路","stutuse":1,"create_time":0,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":2,"child_id":2,"name":"书香阁","intro":"<p>好多美丽的书本&nbsp; &nbsp; &nbsp;&nbsp;<\/p>","open_time":null,"lat":19.553354263306,"lng":110.65026092529,"location":"琼中县","stutuse":null,"create_time":1495423982,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":3,"child_id":2,"name":"蔚蓝海书屋","intro":"<p>适当放松的方式 &nbsp; &nbsp; &nbsp;<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1495424270,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":4,"child_id":2,"name":"三味书屋","intro":"<p>啦啦啦啦绿<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1497854971,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":5,"child_id":3,"name":"吗哪书屋","intro":"<p>水深 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/p>","open_time":null,"lat":19.553354263306,"lng":110.68245697021,"location":"琼中县","stutuse":null,"create_time":1499242492,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":6,"child_id":3,"name":"水的书屋","intro":"<p>我去<\/p>","open_time":null,"lat":19.671039581299,"lng":110.61346435547,"location":"琼中县","stutuse":null,"create_time":1505266646,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":7,"child_id":3,"name":"ceshi111","intro":"<p>ceshiceshiceshiceshi111<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1514280070,"img_path":"http://p0stjxid6.bkt.clouddn.com/66153201712261736517357.png"}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 7
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":1,"child_id":2,"name":"四海书屋","intro":"<p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a","open_time":"","lat":19.553354263306,"lng":109.65609741211,"location":"琼中县西河路","stutuse":1,"create_time":0,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":2,"child_id":2,"name":"书香阁","intro":"<p>好多美丽的书本&nbsp; &nbsp; &nbsp;&nbsp;<\/p>","open_time":null,"lat":19.553354263306,"lng":110.65026092529,"location":"琼中县","stutuse":null,"create_time":1495423982,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":3,"child_id":2,"name":"蔚蓝海书屋","intro":"<p>适当放松的方式 &nbsp; &nbsp; &nbsp;<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1495424270,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":4,"child_id":2,"name":"三味书屋","intro":"<p>啦啦啦啦绿<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1497854971,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":5,"child_id":3,"name":"吗哪书屋","intro":"<p>水深 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/p>","open_time":null,"lat":19.553354263306,"lng":110.68245697021,"location":"琼中县","stutuse":null,"create_time":1499242492,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":6,"child_id":3,"name":"水的书屋","intro":"<p>我去<\/p>","open_time":null,"lat":19.671039581299,"lng":110.61346435547,"location":"琼中县","stutuse":null,"create_time":1505266646,"img_path":"http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg"},{"id":7,"child_id":3,"name":"ceshi111","intro":"<p>ceshiceshiceshiceshi111<\/p>","open_time":null,"lat":19.032262802124,"lng":109.65609741211,"location":"琼中县","stutuse":null,"create_time":1514280070,"img_path":"http://p0stjxid6.bkt.clouddn.com/66153201712261736517357.png"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * child_id : 2
             * name : 四海书屋
             * intro : <p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a
             * open_time :
             * lat : 19.553354263306
             * lng : 109.65609741211
             * location : 琼中县西河路
             * stutuse : 1
             * create_time : 0
             * img_path : http://p0stjxid6.bkt.clouddn.com/500217825_banner%20-%20%E5%89%AF%E6%9C%AC.jpg
             */

            private int id;
            private int child_id;
            private String name;
            private String intro;
            private String open_time;
            private double lat;
            private double lng;
            private String location;
            private int stutuse;
            private int create_time;
            private String img_path;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getChild_id() {
                return child_id;
            }

            public void setChild_id(int child_id) {
                this.child_id = child_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getStutuse() {
                return stutuse;
            }

            public void setStutuse(int stutuse) {
                this.stutuse = stutuse;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }
        }
    }
}
