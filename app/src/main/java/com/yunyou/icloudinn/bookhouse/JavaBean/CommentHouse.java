package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class CommentHouse {


    /**
     * id : 212
     * user_id : 12143003
     * user_name : 『～青～  』
     * user_head_img : https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0
     * source_id : 21
     * type : 1
     * action : 评论了民宿！
     * location : null
     * praise_num : 0
     * create_time : 1501062977
     * update_time : 1501062977
     * hotel : {"id":21,"name":"曼陀罗客栈曼达听涛","status":-1,"owner_id":4,"thumb":"/uploads/20170531/af7709516756721acd86f8631565d473.png","price":700,"house_type":"","room_num":1,"dwell_num":0,"check_in_time":"","check_out_time":"","intro":null,"recommend_reason":"曼陀罗客栈曼达听涛","describe":"<p><span style=\"font: 14px/28px &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Segoe UI&quot;, &quot;Microsoft Yahei&quot;, 微软雅黑, Tahoma, Arial, STHeiti, sans-serif; color: rgb(51, 51, 51); text-transform: none; text-indent: 0px; letter-spacing: normal; word-spacing: 0px; float: none; display: inline !important; widows: 1; font-size-adjust: none; font-stretch: normal; background-color: rgb(255, 255, 255); -webkit-text-stroke-width: 0px;\">曼陀罗客栈拥有多种不同主题风格的房型，客房温馨雅致、整洁舒适。客栈提供周到、热情的服务，使宾客远离城市的繁华与喧嚣，享受悠然与舒畅。<\/span><\/p>","lat":19.047828674316,"lng":109.84953308105,"addr":"海南省海口市琼中","create_time":"1498118891","comment":[{"id":172,"source_id":21,"type":1,"user_id":12143003,"user_name":"『～青～  』","head_img":"https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0","recommend_exponent":4,"content":"环境不错","imgs":["/uploads/20170726/19617bdde2b256ae6412f6d6856bc312.png"],"praise_num":null,"location":null,"create_time":1501062977,"reply":[]}]}
     * dynamic_comment : [{"id":173,"source_id":212,"type":3,"user_id":12143003,"user_name":"『～青～  』","head_img":"https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0","recommend_exponent":null,"content":"对对对","imgs":null,"praise_num":null,"location":null,"create_time":1501063015,"reply":[{"id":30,"comment_id":173,"user_name":"李俊宇","to_user_name":"『～青～  』","reply":"回复你一下","create_time":1501063175}]},{"id":175,"source_id":212,"type":3,"user_id":12143005,"user_name":"李俊宇","head_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0","recommend_exponent":null,"content":"我再评论一下","imgs":null,"praise_num":null,"location":null,"create_time":1501063282,"reply":[]}]
     */

    private int id;
    private int user_id;
    private String user_name;
    private String user_head_img;
    private int source_id;
    private int type;
    private String action;
    private String location;
    private int praise_num;
    private int create_time;
    private int update_time;
    private HotelBean hotel;
    private List<DynamicCommentBean> dynamic_comment;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public HotelBean getHotel() {
        return hotel;
    }

    public void setHotel(HotelBean hotel) {
        this.hotel = hotel;
    }

    public List<DynamicCommentBean> getDynamic_comment() {
        return dynamic_comment;
    }

    public void setDynamic_comment(List<DynamicCommentBean> dynamic_comment) {
        this.dynamic_comment = dynamic_comment;
    }

    public static class HotelBean {
        /**
         * id : 21
         * name : 曼陀罗客栈曼达听涛
         * status : -1
         * owner_id : 4
         * thumb : /uploads/20170531/af7709516756721acd86f8631565d473.png
         * price : 700
         * house_type :
         * room_num : 1
         * dwell_num : 0
         * check_in_time :
         * check_out_time :
         * intro : null
         * recommend_reason : 曼陀罗客栈曼达听涛
         * describe : <p><span style="font: 14px/28px &quot;Helvetica Neue&quot;, &quot;Hiragino Sans GB&quot;, &quot;Segoe UI&quot;, &quot;Microsoft Yahei&quot;, 微软雅黑, Tahoma, Arial, STHeiti, sans-serif; color: rgb(51, 51, 51); text-transform: none; text-indent: 0px; letter-spacing: normal; word-spacing: 0px; float: none; display: inline !important; widows: 1; font-size-adjust: none; font-stretch: normal; background-color: rgb(255, 255, 255); -webkit-text-stroke-width: 0px;">曼陀罗客栈拥有多种不同主题风格的房型，客房温馨雅致、整洁舒适。客栈提供周到、热情的服务，使宾客远离城市的繁华与喧嚣，享受悠然与舒畅。</span></p>
         * lat : 19.047828674316
         * lng : 109.84953308105
         * addr : 海南省海口市琼中
         * create_time : 1498118891
         * comment : [{"id":172,"source_id":21,"type":1,"user_id":12143003,"user_name":"『～青～  』","head_img":"https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0","recommend_exponent":4,"content":"环境不错","imgs":["/uploads/20170726/19617bdde2b256ae6412f6d6856bc312.png"],"praise_num":null,"location":null,"create_time":1501062977,"reply":[]}]
         */

        private int id;
        private String name;
        private int status;
        private int owner_id;
        private String thumb;
        private int price;
        private String house_type;
        private int room_num;
        private int dwell_num;
        private String check_in_time;
        private String check_out_time;
        private Object intro;
        private String recommend_reason;
        private String describe;
        private double lat;
        private double lng;
        private String addr;
        private String create_time;
        private List<CommentBean> comment;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getHouse_type() {
            return house_type;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public int getRoom_num() {
            return room_num;
        }

        public void setRoom_num(int room_num) {
            this.room_num = room_num;
        }

        public int getDwell_num() {
            return dwell_num;
        }

        public void setDwell_num(int dwell_num) {
            this.dwell_num = dwell_num;
        }

        public String getCheck_in_time() {
            return check_in_time;
        }

        public void setCheck_in_time(String check_in_time) {
            this.check_in_time = check_in_time;
        }

        public String getCheck_out_time() {
            return check_out_time;
        }

        public void setCheck_out_time(String check_out_time) {
            this.check_out_time = check_out_time;
        }

        public Object getIntro() {
            return intro;
        }

        public void setIntro(Object intro) {
            this.intro = intro;
        }

        public String getRecommend_reason() {
            return recommend_reason;
        }

        public void setRecommend_reason(String recommend_reason) {
            this.recommend_reason = recommend_reason;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            /**
             * id : 172
             * source_id : 21
             * type : 1
             * user_id : 12143003
             * user_name : 『～青～  』
             * head_img : https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0
             * recommend_exponent : 4
             * content : 环境不错
             * imgs : ["/uploads/20170726/19617bdde2b256ae6412f6d6856bc312.png"]
             * praise_num : null
             * location : null
             * create_time : 1501062977
             * reply : []
             */

            private int id;
            private int source_id;
            private int type;
            private int user_id;
            private String user_name;
            private String head_img;
            private int recommend_exponent;
            private String content;
            private Object praise_num;
            private String location;
            private int create_time;
            private List<String> imgs;
            private List<?> reply;

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

            public Object getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(Object praise_num) {
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

            public List<?> getReply() {
                return reply;
            }

            public void setReply(List<?> reply) {
                this.reply = reply;
            }
        }
    }

    public static class DynamicCommentBean {
        /**
         * id : 173
         * source_id : 212
         * type : 3
         * user_id : 12143003
         * user_name : 『～青～  』
         * head_img : https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0
         * recommend_exponent : null
         * content : 对对对
         * imgs : null
         * praise_num : null
         * location : null
         * create_time : 1501063015
         * reply : [{"id":30,"comment_id":173,"user_name":"李俊宇","to_user_name":"『～青～  』","reply":"回复你一下","create_time":1501063175}]
         */

        private int id;
        private int source_id;
        private int type;
        private int user_id;
        private String user_name;
        private String head_img;
        private Object recommend_exponent;
        private String content;
        private Object imgs;
        private Object praise_num;
        private Object location;
        private int create_time;
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

        public Object getRecommend_exponent() {
            return recommend_exponent;
        }

        public void setRecommend_exponent(Object recommend_exponent) {
            this.recommend_exponent = recommend_exponent;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getImgs() {
            return imgs;
        }

        public void setImgs(Object imgs) {
            this.imgs = imgs;
        }

        public Object getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(Object praise_num) {
            this.praise_num = praise_num;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class ReplyBean {
            /**
             * id : 30
             * comment_id : 173
             * user_name : 李俊宇
             * to_user_name : 『～青～  』
             * reply : 回复你一下
             * create_time : 1501063175
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
}
