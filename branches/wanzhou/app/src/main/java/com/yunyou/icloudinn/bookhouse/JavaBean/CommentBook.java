package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class CommentBook {

    /**
     * id : 190
     * user_id : 12142969
     * user_name : 李俊宇
     * user_head_img : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0
     * source_id : 58
     * type : 2
     * action : 写了一个书评！
     * location : null
     * praise_num : 0
     * create_time : 1500601307
     * update_time : 1500601307
     * book : {"id":58,"model_id":18,"book_house_id":1,"price":25,"status":1,"read_num":897,"rent_num":0,"renting_user":"","create_time":"1496200858","comment":[{"id":163,"source_id":58,"type":2,"user_id":12142969,"user_name":"李俊宇","head_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0","recommend_exponent":3,"content":"吃醋吃饭","imgs":["/uploads/20170720/5a0736796bf576088026a949c252482f.jpg"],"praise_num":2,"location":null,"create_time":1500601307,"reply":[{"id":22,"comment_id":163,"user_name":"李俊宇","to_user_name":"李俊宇","reply":"哈哈哈","create_time":1500601338}]}]}
     * dynamic_comment : [{"id":170,"source_id":190,"type":3,"user_id":12142976,"user_name":"李俊宇","head_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0","recommend_exponent":null,"content":"评价自己","imgs":null,"praise_num":null,"location":null,"create_time":1500878538,"reply":[{"id":27,"comment_id":170,"user_name":"李俊宇","to_user_name":"李俊宇","reply":"再次评价","create_time":1500878564}]}]
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
    private BookBean book;
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

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public List<DynamicCommentBean> getDynamic_comment() {
        return dynamic_comment;
    }

    public void setDynamic_comment(List<DynamicCommentBean> dynamic_comment) {
        this.dynamic_comment = dynamic_comment;
    }

    public static class BookBean {
        /**
         * id : 58
         * model_id : 18
         * book_house_id : 1
         * price : 25
         * status : 1
         * read_num : 897
         * rent_num : 0
         * renting_user :
         * create_time : 1496200858
         * comment : [{"id":163,"source_id":58,"type":2,"user_id":12142969,"user_name":"李俊宇","head_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0","recommend_exponent":3,"content":"吃醋吃饭","imgs":["/uploads/20170720/5a0736796bf576088026a949c252482f.jpg"],"praise_num":2,"location":null,"create_time":1500601307,"reply":[{"id":22,"comment_id":163,"user_name":"李俊宇","to_user_name":"李俊宇","reply":"哈哈哈","create_time":1500601338}]}]
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
        private List<CommentBean> comment;

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

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            /**
             * id : 163
             * source_id : 58
             * type : 2
             * user_id : 12142969
             * user_name : 李俊宇
             * head_img : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0
             * recommend_exponent : 3
             * content : 吃醋吃饭
             * imgs : ["/uploads/20170720/5a0736796bf576088026a949c252482f.jpg"]
             * praise_num : 2
             * location : null
             * create_time : 1500601307
             * reply : [{"id":22,"comment_id":163,"user_name":"李俊宇","to_user_name":"李俊宇","reply":"哈哈哈","create_time":1500601338}]
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
            private Object location;
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

            public static class ReplyBean {
                /**
                 * id : 22
                 * comment_id : 163
                 * user_name : 李俊宇
                 * to_user_name : 李俊宇
                 * reply : 哈哈哈
                 * create_time : 1500601338
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

    public static class DynamicCommentBean {
        /**
         * id : 170
         * source_id : 190
         * type : 3
         * user_id : 12142976
         * user_name : 李俊宇
         * head_img : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuJs9C4ibzIngY4ibsmzhia5I8swe5VQc90XM93xK5wnUDwXKnOAdMpGIMV8BxW0OF7rDSKXquH6nfQ/0
         * recommend_exponent : null
         * content : 评价自己
         * imgs : null
         * praise_num : null
         * location : null
         * create_time : 1500878538
         * reply : [{"id":27,"comment_id":170,"user_name":"李俊宇","to_user_name":"李俊宇","reply":"再次评价","create_time":1500878564}]
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
        private List<ReplyBeanX> reply;

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

        public List<ReplyBeanX> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBeanX> reply) {
            this.reply = reply;
        }

        public static class ReplyBeanX {
            /**
             * id : 27
             * comment_id : 170
             * user_name : 李俊宇
             * to_user_name : 李俊宇
             * reply : 再次评价
             * create_time : 1500878564
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
