package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;
import java.util.List;

public class BookDetailData implements Serializable {

    /**
     * code : 100
     * msg : success
     * data : {"id":58,"model_id":18,"book_house_id":1,"price":25,"status":1,"read_num":777,"rent_num":0,"renting_user":"","create_time":"1496200858","is_collect":false,"model":{"id":18,"name":"人性的弱点","auther":" 戴尔·卡耐基 ","edition":"无","nationality":"[美]","cover_img":"/uploads/20170531/0e0b27e5938cbf59fb2367ee37cfd8c9.jpg","publisher":"中国发展出版社","publish_time":null,"category_id":5,"intro":"<p>适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1496200858,"update_time":1499161541},"comment":[{"id":58,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"好看","imgs":["/uploads/20170620/4811025e6176fae44e72de2f48cf980d.jpg"],"praise_num":2,"location":null,"create_time":1497961764,"reply":[{"id":45,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279490},{"id":46,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279541}]},{"id":59,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"好看","imgs":["/uploads/20170621/606e67c86e9216f820ad10e4af248eb2.gif"],"praise_num":1,"location":null,"create_time":1498006651,"reply":[]},{"id":60,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"真的好好看哦","imgs":["/uploads/20170621/37c1124c89fa968bc3e00cc7bd0e4e2f.png"],"praise_num":0,"location":"海南省海口市秀英区长滨东四街6海口市政府第二办公区17号北楼2楼","create_time":1498006763,"reply":[]},{"id":61,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"真的好好看哦","imgs":["/uploads/20170621/37c1124c89fa968bc3e00cc7bd0e4e2f.png"],"praise_num":2,"location":"海南省海口市秀英区长滨东四街6海口市政府第二办公区17号北楼2楼","create_time":1498006776,"reply":[]},{"id":70,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"没没","imgs":["/uploads/20170624/3044c5dc56934a44379f5177c1a72c2d.gif","/uploads/20170624/ae0c2ff6d6c6283a3c0e45427bd206fe.gif"],"praise_num":1,"location":null,"create_time":1498281214,"reply":[]},{"id":72,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"实时图片","imgs":["/uploads/20170625/795a43eddbec73b602b1a06b51b9890b.gif","/uploads/20170625/e9e7e21ab316ec37fe36bbb3b142bc8c.gif"],"praise_num":2,"location":null,"create_time":1498400753,"reply":[{"id":48,"comment_id":72,"user_name":"『～青～  』","to_user_name":"","reply":"图片模糊啦","create_time":1498441580}]},{"id":77,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"","imgs":["/uploads/20170626/584a83ad4d0c304cc9b9124f48b99caf.gif"],"praise_num":null,"location":null,"create_time":1498483783,"reply":[{"id":50,"comment_id":77,"user_name":"汪君相","to_user_name":"","reply":"2323232","create_time":1498695289}]}],"book_house":{"id":1,"child_id":2,"name":"四海书屋","intro":"<p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a","open_time":"","lat":0,"lng":0,"location":"琼中县西河路","stutuse":1,"create_time":0},"book_donate":null,"book_rent":[{"id":5,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497923271,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":6,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497943317,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":7,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497943326,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":9,"book_id":58,"user_id":12142932,"user_name":"汪君相","status":0,"operator":"","rent_time":1498010843,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIRYmb8O0nKj3Ks3U8DiaU4DFqQ7d4VUoq3NlAwqb2qC3By1pbhYq2fnytibl0YicEE6zbZjNG4p2icrw/0"},{"id":10,"book_id":58,"user_id":12142933,"user_name":"Spencer","status":0,"operator":"","rent_time":1498036521,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eom6Erof6WLbohbX8KEU2k46DmOSSD7xNT0qYOwVliaLT578gGicH8gicRSbN5sfpviaDxzib4tTcRXDOg/0"},{"id":45,"book_id":58,"user_id":12142941,"user_name":"『～青～  』","status":0,"operator":"","rent_time":1499300227,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0"},{"id":48,"book_id":58,"user_id":12142942,"user_name":"李俊宇","status":0,"operator":"","rent_time":1499735341,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpESclOtolqU9qSwzbVAWQAtfs6Kb32EKia8fsZ5ric4JBxcicxTuER5ItDRic7icOKJnwxsOtl7zVWHQ/0"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 58
         * model_id : 18
         * book_house_id : 1
         * price : 25
         * status : 1
         * read_num : 777
         * rent_num : 0
         * renting_user :
         * create_time : 1496200858
         * is_collect : false
         * model : {"id":18,"name":"人性的弱点","auther":" 戴尔·卡耐基 ","edition":"无","nationality":"[美]","cover_img":"/uploads/20170531/0e0b27e5938cbf59fb2367ee37cfd8c9.jpg","publisher":"中国发展出版社","publish_time":null,"category_id":5,"intro":"<p>适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1496200858,"update_time":1499161541}
         * comment : [{"id":58,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"好看","imgs":["/uploads/20170620/4811025e6176fae44e72de2f48cf980d.jpg"],"praise_num":2,"location":null,"create_time":1497961764,"reply":[{"id":45,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279490},{"id":46,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279541}]},{"id":59,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"好看","imgs":["/uploads/20170621/606e67c86e9216f820ad10e4af248eb2.gif"],"praise_num":1,"location":null,"create_time":1498006651,"reply":[]},{"id":60,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"真的好好看哦","imgs":["/uploads/20170621/37c1124c89fa968bc3e00cc7bd0e4e2f.png"],"praise_num":0,"location":"海南省海口市秀英区长滨东四街6海口市政府第二办公区17号北楼2楼","create_time":1498006763,"reply":[]},{"id":61,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"真的好好看哦","imgs":["/uploads/20170621/37c1124c89fa968bc3e00cc7bd0e4e2f.png"],"praise_num":2,"location":"海南省海口市秀英区长滨东四街6海口市政府第二办公区17号北楼2楼","create_time":1498006776,"reply":[]},{"id":70,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"没没","imgs":["/uploads/20170624/3044c5dc56934a44379f5177c1a72c2d.gif","/uploads/20170624/ae0c2ff6d6c6283a3c0e45427bd206fe.gif"],"praise_num":1,"location":null,"create_time":1498281214,"reply":[]},{"id":72,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":4,"content":"实时图片","imgs":["/uploads/20170625/795a43eddbec73b602b1a06b51b9890b.gif","/uploads/20170625/e9e7e21ab316ec37fe36bbb3b142bc8c.gif"],"praise_num":2,"location":null,"create_time":1498400753,"reply":[{"id":48,"comment_id":72,"user_name":"『～青～  』","to_user_name":"","reply":"图片模糊啦","create_time":1498441580}]},{"id":77,"source_id":58,"type":2,"user_id":12142941,"user_name":"『～青～  』","head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","recommend_exponent":3,"content":"","imgs":["/uploads/20170626/584a83ad4d0c304cc9b9124f48b99caf.gif"],"praise_num":null,"location":null,"create_time":1498483783,"reply":[{"id":50,"comment_id":77,"user_name":"汪君相","to_user_name":"","reply":"2323232","create_time":1498695289}]}]
         * book_house : {"id":1,"child_id":2,"name":"四海书屋","intro":"<p>路漫漫其修远兮... &nbsp; &nbsp; &nbsp; 也是看不懂 &nbsp; &a","open_time":"","lat":0,"lng":0,"location":"琼中县西河路","stutuse":1,"create_time":0}
         * book_donate : null
         * book_rent : [{"id":5,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497923271,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":6,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497943317,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":7,"book_id":58,"user_id":12142928,"user_name":"狂野小青年","status":0,"operator":"","rent_time":1497943326,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0"},{"id":9,"book_id":58,"user_id":12142932,"user_name":"汪君相","status":0,"operator":"","rent_time":1498010843,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIRYmb8O0nKj3Ks3U8DiaU4DFqQ7d4VUoq3NlAwqb2qC3By1pbhYq2fnytibl0YicEE6zbZjNG4p2icrw/0"},{"id":10,"book_id":58,"user_id":12142933,"user_name":"Spencer","status":0,"operator":"","rent_time":1498036521,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eom6Erof6WLbohbX8KEU2k46DmOSSD7xNT0qYOwVliaLT578gGicH8gicRSbN5sfpviaDxzib4tTcRXDOg/0"},{"id":45,"book_id":58,"user_id":12142941,"user_name":"『～青～  』","status":0,"operator":"","rent_time":1499300227,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0"},{"id":48,"book_id":58,"user_id":12142942,"user_name":"李俊宇","status":0,"operator":"","rent_time":1499735341,"head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpESclOtolqU9qSwzbVAWQAtfs6Kb32EKia8fsZ5ric4JBxcicxTuER5ItDRic7icOKJnwxsOtl7zVWHQ/0"}]
         */

        private int id;
        private int model_id;
        private int book_house_id;
        private int price;
        private int status;
        private int read_num;
        private int rent_num;
        private String renting_user;
        private String create_time;
        private boolean is_collect;
        private ModelBean model;
        private BookHouseBean book_house;
        private String book_donate;
        private List<CommentBean> comment;
        private List<BookRentBean> book_rent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getModel_id() {
            return model_id;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

        public int getBook_house_id() {
            return book_house_id;
        }

        public void setBook_house_id(int book_house_id) {
            this.book_house_id = book_house_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
        }

        public int getRent_num() {
            return rent_num;
        }

        public void setRent_num(int rent_num) {
            this.rent_num = rent_num;
        }

        public String getRenting_user() {
            return renting_user;
        }

        public void setRenting_user(String renting_user) {
            this.renting_user = renting_user;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public boolean isIs_collect() {
            return is_collect;
        }

        public void setIs_collect(boolean is_collect) {
            this.is_collect = is_collect;
        }

        public ModelBean getModel() {
            return model;
        }

        public void setModel(ModelBean model) {
            this.model = model;
        }

        public BookHouseBean getBook_house() {
            return book_house;
        }

        public void setBook_house(BookHouseBean book_house) {
            this.book_house = book_house;
        }

        public String getBook_donate() {
            return book_donate;
        }

        public void setBook_donate(String book_donate) {
            this.book_donate = book_donate;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public List<BookRentBean> getBook_rent() {
            return book_rent;
        }

        public void setBook_rent(List<BookRentBean> book_rent) {
            this.book_rent = book_rent;
        }

        public static class ModelBean implements Serializable {
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
             * update_time : 1499161541
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

            public String getAuther() {
                return auther;
            }

            public void setAuther(String auther) {
                this.auther = auther;
            }

            public String getEdition() {
                return edition;
            }

            public void setEdition(String edition) {
                this.edition = edition;
            }

            public String getNationality() {
                return nationality;
            }

            public void setNationality(String nationality) {
                this.nationality = nationality;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
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
        }

        public static class BookHouseBean implements Serializable {
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

            public int getLat() {
                return lat;
            }

            public void setLat(int lat) {
                this.lat = lat;
            }

            public int getLng() {
                return lng;
            }

            public void setLng(int lng) {
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
        }

        public static class CommentBean implements Serializable {
            /**
             * id : 58
             * source_id : 58
             * type : 2
             * user_id : 12142941
             * user_name : 『～青～  』
             * head_img : http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0
             * recommend_exponent : 3
             * content : 好看
             * imgs : ["/uploads/20170620/4811025e6176fae44e72de2f48cf980d.jpg"]
             * praise_num : 2
             * location : null
             * create_time : 1497961764
             * reply : [{"id":45,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279490},{"id":46,"comment_id":58,"user_name":"『～青～  』","to_user_name":"","reply":"挺好看的","create_time":1498279541}]
             */

            private int id;
            private int source_id;
            private int type;
            private int user_id;
            private String user_name;
            private String head_img;
            private int recommend_exponent;
            private String content;
            private int praise_num;
            private String location;
            private int create_time;
            private List<String> imgs;
            private List<ReplyBean> reply;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public int getRecommend_exponent() {
                return recommend_exponent;
            }

            public void setRecommend_exponent(int recommend_exponent) {
                this.recommend_exponent = recommend_exponent;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(int praise_num) {
                this.praise_num = praise_num;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public List<ReplyBean> getReply() {
                return reply;
            }

            public void setReply(List<ReplyBean> reply) {
                this.reply = reply;
            }

            public static class ReplyBean implements Serializable {
                /**
                 * id : 45
                 * comment_id : 58
                 * user_name : 『～青～  』
                 * to_user_name :
                 * reply : 挺好看的
                 * create_time : 1498279490
                 */

                private int id;
                private int comment_id;
                private String user_name;
                private String to_user_name;
                private String reply;
                private int create_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getComment_id() {
                    return comment_id;
                }

                public void setComment_id(int comment_id) {
                    this.comment_id = comment_id;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getTo_user_name() {
                    return to_user_name;
                }

                public void setTo_user_name(String to_user_name) {
                    this.to_user_name = to_user_name;
                }

                public String getReply() {
                    return reply;
                }

                public void setReply(String reply) {
                    this.reply = reply;
                }

                public int getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(int create_time) {
                    this.create_time = create_time;
                }
            }
        }

        public static class BookRentBean implements Serializable {
            /**
             * id : 5
             * book_id : 58
             * user_id : 12142928
             * user_name : 狂野小青年
             * status : 0
             * operator :
             * rent_time : 1497923271
             * head_img : http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0
             */

            private int id;
            private int book_id;
            private int user_id;
            private String user_name;
            private int status;
            private String operator;
            private int rent_time;
            private String head_img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBook_id() {
                return book_id;
            }

            public void setBook_id(int book_id) {
                this.book_id = book_id;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public int getRent_time() {
                return rent_time;
            }

            public void setRent_time(int rent_time) {
                this.rent_time = rent_time;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }
        }
    }
}
