package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class CommentReply {
    /**
     * id : 138
     * source_id : 188
     * type : 3
     * user_id : 12142967
     * user_name : 『～青～  』
     * head_img : https://wx.qlogo.cn/mmopen/vi_32/R0YJ1QQMd9NX50iaAnUK5kMCPc6sqPInCeGu8JEJbZWhxmFNpHvnrCq63jIsCtJtePSkLB5pU5UyFGG68rKmv0g/0
     * recommend_exponent : null
     * content : 评论一下
     * imgs : null
     * praise_num : 2
     * location : null
     * create_time : 1500522019
     * reply : [{"id":5,"comment_id":138,"user_name":"李俊宇","to_user_name":"『～青～  』","reply":"吃饭饭","create_time":1500522104},{"id":6,"comment_id":138,"user_name":"『～青～  』","to_user_name":"『～青～  』","reply":"评论～","create_time":1500522175},{"id":7,"comment_id":138,"user_name":"李俊宇","to_user_name":"『～青～  』","reply":"还好吧","create_time":1500522234},{"id":8,"comment_id":138,"user_name":"『～青～  』","to_user_name":"『～青～  』","reply":"回复","create_time":1500522447},{"id":9,"comment_id":138,"user_name":"『～青～  』","to_user_name":"『～青～  』","reply":"再回复","create_time":1500522458},{"id":17,"comment_id":138,"user_name":"『～青～  』","to_user_name":"『～青～  』","reply":"哈哈哈","create_time":1500531038},{"id":18,"comment_id":138,"user_name":"『～青～  』","to_user_name":"『～青～  』","reply":"解决解决军","create_time":1500531046}]
     */

    private String id;
    private int source_id;
    private int type;
    private int user_id;
    private String user_name;
    private String head_img;
    private Object recommend_exponent;
    private String content;
    private Object imgs;
    private int praise_num;
    private Object location;
    private int create_time;
    private List<CommentReply.ReplyBean> reply;

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
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

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
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

    public List<CommentReply.ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<CommentReply.ReplyBean> reply) {
        this.reply = reply;
    }

    public static class ReplyBean {
        /**
         * id : 5
         * comment_id : 138
         * user_name : 李俊宇
         * to_user_name : 『～青～  』
         * reply : 吃饭饭
         * create_time : 1500522104
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
