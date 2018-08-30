package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class CircleThoughtData {



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
            private HeartfeelingBean heartfeeling;
            private Object hotel;
            private BookBean book;
            private List<?> dynamic_comment;

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

            public HeartfeelingBean getHeartfeeling() {
                return heartfeeling;
            }

            public void setHeartfeeling(HeartfeelingBean heartfeeling) {
                this.heartfeeling = heartfeeling;
            }

            public Object getHotel() {
                return hotel;
            }

            public void setHotel(Object hotel) {
                this.hotel = hotel;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public List<?> getDynamic_comment() {
                return dynamic_comment;
            }

            public void setDynamic_comment(List<?> dynamic_comment) {
                this.dynamic_comment = dynamic_comment;
            }

            public static class HeartfeelingBean {
                /**
                 * id : 72
                 * user_id : 12142941
                 * type : null
                 * title : 感汉号
                 * imgs : ["/uploads/20170703/dccf1055dc925f3bbe7529b46f533545.png"]
                 * create_time : 1499067201
                 */

                private int id;
                private int user_id;
                private Object type;
                private String title;
                private int create_time;
                private List<String> imgs;

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

                public Object getType() {
                    return type;
                }

                public void setType(Object type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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
            }

            public static class BookBean {



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


                    private int id;
                    private int source_id;
                    private int type;
                    private int user_id;
                    private String user_name;
                    private String head_img;
                    private int recommend_exponent;
                    private String content;
                    private Object imgs;
                    private int praise_num;
                    private Object location;
                    private int create_time;
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

                    public List<?> getReply() {
                        return reply;
                    }

                    public void setReply(List<?> reply) {
                        this.reply = reply;
                    }
                }
            }
        }
    }
}
