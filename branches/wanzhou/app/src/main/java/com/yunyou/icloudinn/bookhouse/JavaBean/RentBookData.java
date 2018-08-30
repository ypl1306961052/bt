package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class RentBookData {

    /**
     * code : 100
     * msg : success
     * data : {"total":2,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":75,"book_id":69,"user_id":12143010,"user_name":"『～青～  』","guaranty_money":34,"return_guaranty_money":0,"status":1,"operator":"","rent_time":1501227126,"rent_user":"李俊宇","rent_num":"1","book":{"id":69,"model_id":30,"book_house_id":1,"price":34,"status":1,"read_num":41,"rent_num":0,"renting_user":"","create_time":"1497952512","model":{"id":30,"name":"外婆家的汤","auther":"李转清","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/8b021160e696b2899e3caedfde0ae02a.jpg","publisher":"时代科技","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; 适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1497952512,"update_time":1500288030}}},{"id":84,"book_id":68,"user_id":12143009,"user_name":"狂野小青年","guaranty_money":24,"return_guaranty_money":0,"status":1,"operator":"","rent_time":1501490755,"rent_user":"李俊宇","rent_num":"1","book":{"id":68,"model_id":29,"book_house_id":1,"price":24,"status":1,"read_num":68,"rent_num":0,"renting_user":"","create_time":"1497952047","model":{"id":29,"name":"外婆的道歉信","auther":"韩寒","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/03fd18eb4f9d0a7aabd09a60026c66d5.jpg","publisher":"撒旦撒旦","publish_time":null,"category_id":5,"intro":"<p>我也是看不懂了<\/p><p>rerr<\/p>","create_time":1497952047,"update_time":1500288085}}}]}
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
         * total : 2
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":75,"book_id":69,"user_id":12143010,"user_name":"『～青～  』","guaranty_money":34,"return_guaranty_money":0,"status":1,"operator":"","rent_time":1501227126,"rent_user":"李俊宇","rent_num":"1","book":{"id":69,"model_id":30,"book_house_id":1,"price":34,"status":1,"read_num":41,"rent_num":0,"renting_user":"","create_time":"1497952512","model":{"id":30,"name":"外婆家的汤","auther":"李转清","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/8b021160e696b2899e3caedfde0ae02a.jpg","publisher":"时代科技","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; 适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1497952512,"update_time":1500288030}}},{"id":84,"book_id":68,"user_id":12143009,"user_name":"狂野小青年","guaranty_money":24,"return_guaranty_money":0,"status":1,"operator":"","rent_time":1501490755,"rent_user":"李俊宇","rent_num":"1","book":{"id":68,"model_id":29,"book_house_id":1,"price":24,"status":1,"read_num":68,"rent_num":0,"renting_user":"","create_time":"1497952047","model":{"id":29,"name":"外婆的道歉信","auther":"韩寒","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/03fd18eb4f9d0a7aabd09a60026c66d5.jpg","publisher":"撒旦撒旦","publish_time":null,"category_id":5,"intro":"<p>我也是看不懂了<\/p><p>rerr<\/p>","create_time":1497952047,"update_time":1500288085}}}]
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
             * id : 75
             * book_id : 69
             * user_id : 12143010
             * user_name : 『～青～  』
             * guaranty_money : 34
             * return_guaranty_money : 0
             * status : 1
             * operator :
             * rent_time : 1501227126
             * rent_user : 李俊宇
             * rent_num : 1
             * book : {"id":69,"model_id":30,"book_house_id":1,"price":34,"status":1,"read_num":41,"rent_num":0,"renting_user":"","create_time":"1497952512","model":{"id":30,"name":"外婆家的汤","auther":"李转清","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/8b021160e696b2899e3caedfde0ae02a.jpg","publisher":"时代科技","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; 适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1497952512,"update_time":1500288030}}
             */

            private int id;
            private int book_id;
            private int user_id;
            private String user_name;
            private int guaranty_money;
            private int return_guaranty_money;
            private int status;
            private String operator;
            private int rent_time;
            private String rent_user;
            private String rent_num;
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

            public int getGuaranty_money() {
                return guaranty_money;
            }

            public void setGuaranty_money(int guaranty_money) {
                this.guaranty_money = guaranty_money;
            }

            public int getReturn_guaranty_money() {
                return return_guaranty_money;
            }

            public void setReturn_guaranty_money(int return_guaranty_money) {
                this.return_guaranty_money = return_guaranty_money;
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

            public String getRent_user() {
                return rent_user;
            }

            public void setRent_user(String rent_user) {
                this.rent_user = rent_user;
            }

            public String getRent_num() {
                return rent_num;
            }

            public void setRent_num(String rent_num) {
                this.rent_num = rent_num;
            }

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public static class BookBean {
                /**
                 * id : 69
                 * model_id : 30
                 * book_house_id : 1
                 * price : 34
                 * status : 1
                 * read_num : 41
                 * rent_num : 0
                 * renting_user :
                 * create_time : 1497952512
                 * model : {"id":30,"name":"外婆家的汤","auther":"李转清","edition":"无","nationality":"中国","cover_img":"/uploads/20170621/8b021160e696b2899e3caedfde0ae02a.jpg","publisher":"时代科技","publish_time":null,"category_id":5,"intro":"<p>商品简介 &nbsp; &nbsp; &nbsp; 适当放松的方式的 &nbsp; &nbsp; &nbsp;<\/p>","create_time":1497952512,"update_time":1500288030}
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
                private ModelBean model;

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

                public ModelBean getModel() {
                    return model;
                }

                public void setModel(ModelBean model) {
                    this.model = model;
                }

                public static class ModelBean {
                    /**
                     * id : 30
                     * name : 外婆家的汤
                     * auther : 李转清
                     * edition : 无
                     * nationality : 中国
                     * cover_img : /uploads/20170621/8b021160e696b2899e3caedfde0ae02a.jpg
                     * publisher : 时代科技
                     * publish_time : null
                     * category_id : 5
                     * intro : <p>商品简介 &nbsp; &nbsp; &nbsp; 适当放松的方式的 &nbsp; &nbsp; &nbsp;</p>
                     * create_time : 1497952512
                     * update_time : 1500288030
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
