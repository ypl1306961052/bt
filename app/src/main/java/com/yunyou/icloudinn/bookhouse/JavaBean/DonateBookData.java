package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.ArrayList;
import java.util.List;

public class DonateBookData  {

    /**
     * code : 100
     * msg : success
     * data : {"total":1,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":73,"book_id":76,"book_name":"干活哈哈","cover":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","user_id":12143035,"user_name":"李俊宇","user_phone":2147483647,"status":1,"donate_time":1501604489,"operator":"小张","book":{"id":76,"model_id":40,"book_house_id":1,"price":12,"status":0,"read_num":0,"rent_num":0,"renting_user":"","create_time":"1501667677","book_model":{"id":40,"name":"干活哈哈","auther":"fwefwef","edition":"无","nationality":"fewfwe","cover_img":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","publisher":"efwef","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; dsfsdfdsfsdfs<\/p>","create_time":1501667677,"update_time":1501667677}}}]}
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
         * total : 1
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":73,"book_id":76,"book_name":"干活哈哈","cover":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","user_id":12143035,"user_name":"李俊宇","user_phone":2147483647,"status":1,"donate_time":1501604489,"operator":"小张","book":{"id":76,"model_id":40,"book_house_id":1,"price":12,"status":0,"read_num":0,"rent_num":0,"renting_user":"","create_time":"1501667677","book_model":{"id":40,"name":"干活哈哈","auther":"fwefwef","edition":"无","nationality":"fewfwe","cover_img":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","publisher":"efwef","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; dsfsdfdsfsdfs<\/p>","create_time":1501667677,"update_time":1501667677}}}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data=new ArrayList<DataBean>();

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
             * id : 73
             * book_id : 76
             * book_name : 干活哈哈
             * cover : /uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg
             * user_id : 12143035
             * user_name : 李俊宇
             * user_phone : 2147483647
             * status : 1
             * donate_time : 1501604489
             * operator : 小张
             * book : {"id":76,"model_id":40,"book_house_id":1,"price":12,"status":0,"read_num":0,"rent_num":0,"renting_user":"","create_time":"1501667677","book_model":{"id":40,"name":"干活哈哈","auther":"fwefwef","edition":"无","nationality":"fewfwe","cover_img":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","publisher":"efwef","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; dsfsdfdsfsdfs<\/p>","create_time":1501667677,"update_time":1501667677}}
             */

            private int id;
            private int book_id;
            private String book_name;
            private String cover;
            private int user_id;
            private String user_name;
            private String user_phone;
            private int status;
            private int donate_time;
            private String operator;
            private BookBean book;

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

            public String getBook_name() {
                return book_name;
            }

            public void setBook_name(String book_name) {
                this.book_name = book_name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
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

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getDonate_time() {
                return donate_time;
            }

            public void setDonate_time(int donate_time) {
                this.donate_time = donate_time;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public static class BookBean {
                /**
                 * id : 76
                 * model_id : 40
                 * book_house_id : 1
                 * price : 12
                 * status : 0
                 * read_num : 0
                 * rent_num : 0
                 * renting_user :
                 * create_time : 1501667677
                 * book_model : {"id":40,"name":"干活哈哈","auther":"fwefwef","edition":"无","nationality":"fewfwe","cover_img":"/uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg","publisher":"efwef","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; dsfsdfdsfsdfs<\/p>","create_time":1501667677,"update_time":1501667677}
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
                private BookModelBean book_model;

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

                public BookModelBean getBook_model() {
                    return book_model;
                }

                public void setBook_model(BookModelBean book_model) {
                    this.book_model = book_model;
                }

                public static class BookModelBean {
                    /**
                     * id : 40
                     * name : 干活哈哈
                     * auther : fwefwef
                     * edition : 无
                     * nationality : fewfwe
                     * cover_img : /uploads/20170802/4ab0ac76fe45149865534676d1c26cdf.jpg
                     * publisher : efwef
                     * publish_time : null
                     * category_id : 5
                     * intro : <p>商品简介 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; dsfsdfdsfsdfs</p>
                     * create_time : 1501667677
                     * update_time : 1501667677
                     */

                    private int id;
                    private String name;
                    private String auther;
                    private String edition;
                    private String nationality;
                    private String cover_img;
                    private String publisher;
                    private Object publish_time;
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

                    public Object getPublish_time() {
                        return publish_time;
                    }

                    public void setPublish_time(Object publish_time) {
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
            }
        }
    }
}
