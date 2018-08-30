package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;
import java.util.List;

public class UserListData implements Serializable {

    /**
     * code : 100
     * msg : success
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

    public static class DataBeanX implements Serializable {
        /**
         * total : 432
         * per_page : 20
         * current_page : 1
         * last_page : 22
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

        public static class DataBean implements Serializable {
            /**
             * yunsu_id : 10195526
             * account : 15607683921
             * pwd : 14e1b600b1fd579f47433b88e8d85291
             * nickname : null
             * head_img_url : null
             * phone :
             * email :
             * qq : null
             * wx_unionid : null
             * wx_openid : null
             * sex : null
             * age : null
             * country : null
             * province : null
             * city : null
             * point : 0
             * yun_coin : 0
             * login_num : null
             * access_token : null
             * create_time : 2017-06-14 10:25:29
             * is_concern : 0
             * rent_book_num : 0
             * donate_book_num : 0
             * check_in_hotel_num : 0
             */

            private int yunsu_id;
            private String account;
            private String pwd;
            private String nickname;
            private Object head_img_url;
            private String phone;
            private String email;
            private Object qq;
            private Object wx_unionid;
            private Object wx_openid;
            private Object sex;
            private Object age;
            private Object country;
            private Object province;
            private Object city;
            private int point;
            private int yun_coin;
            private Object login_num;
            private Object access_token;
            private String create_time;
            private int is_concern;
            private int rent_book_num;
            private int donate_book_num;
            private int check_in_hotel_num;

            public int getYunsu_id() {
                return yunsu_id;
            }

            public void setYunsu_id(int yunsu_id) {
                this.yunsu_id = yunsu_id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getHead_img_url() {
                return head_img_url;
            }

            public void setHead_img_url(Object head_img_url) {
                this.head_img_url = head_img_url;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public Object getWx_unionid() {
                return wx_unionid;
            }

            public void setWx_unionid(Object wx_unionid) {
                this.wx_unionid = wx_unionid;
            }

            public Object getWx_openid() {
                return wx_openid;
            }

            public void setWx_openid(Object wx_openid) {
                this.wx_openid = wx_openid;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getCountry() {
                return country;
            }

            public void setCountry(Object country) {
                this.country = country;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getYun_coin() {
                return yun_coin;
            }

            public void setYun_coin(int yun_coin) {
                this.yun_coin = yun_coin;
            }

            public Object getLogin_num() {
                return login_num;
            }

            public void setLogin_num(Object login_num) {
                this.login_num = login_num;
            }

            public Object getAccess_token() {
                return access_token;
            }

            public void setAccess_token(Object access_token) {
                this.access_token = access_token;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getIs_concern() {
                return is_concern;
            }

            public void setIs_concern(int is_concern) {
                this.is_concern = is_concern;
            }

            public int getRent_book_num() {
                return rent_book_num;
            }

            public void setRent_book_num(int rent_book_num) {
                this.rent_book_num = rent_book_num;
            }

            public int getDonate_book_num() {
                return donate_book_num;
            }

            public void setDonate_book_num(int donate_book_num) {
                this.donate_book_num = donate_book_num;
            }

            public int getCheck_in_hotel_num() {
                return check_in_hotel_num;
            }

            public void setCheck_in_hotel_num(int check_in_hotel_num) {
                this.check_in_hotel_num = check_in_hotel_num;
            }
        }
    }
}
