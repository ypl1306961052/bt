package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;
import java.util.List;

public class AttentionData implements Serializable {

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
         * total : 3
         * per_page : 20
         * current_page : 1
         * last_page : 1
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
             * concern_id : 143
             * user_id : 12142941
             * concern_user_id : 12142943
             * concern_time : 1499331346
             * user : {"yunsu_id":12142943,"account":"07702998","pwd":"","nickname":"Captain","head_img_url":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoSKYK3QzEjjlVoM6XK5ks0o1Jw3Ar1nlgpXtITtTJMGYX568SQyn7aIwic67pZtF6mvBqPlRp0okQ/0","phone":"","email":null,"qq":null,"wx_unionid":"","wx_openid":"oRmgM0d1xnoUVHviN94qUUKuUhFE","sex":"1","age":null,"country":"CN","province":"","city":"","point":0,"login_num":null,"rent_num":0,"donate_num":1,"check_in_hotel_num":2}
             */

            private int concern_id;
            private int user_id;
            private int concern_user_id;
            private int concern_time;
            private UserBean user;

            public int getConcern_id() {
                return concern_id;
            }

            public void setConcern_id(int concern_id) {
                this.concern_id = concern_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getConcern_user_id() {
                return concern_user_id;
            }

            public void setConcern_user_id(int concern_user_id) {
                this.concern_user_id = concern_user_id;
            }

            public int getConcern_time() {
                return concern_time;
            }

            public void setConcern_time(int concern_time) {
                this.concern_time = concern_time;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean  implements Serializable {
                /**
                 * yunsu_id : 12142943
                 * account : 07702998
                 * pwd :
                 * nickname : Captain
                 * head_img_url : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoSKYK3QzEjjlVoM6XK5ks0o1Jw3Ar1nlgpXtITtTJMGYX568SQyn7aIwic67pZtF6mvBqPlRp0okQ/0
                 * phone :
                 * email : null
                 * qq : null
                 * wx_unionid :
                 * wx_openid : oRmgM0d1xnoUVHviN94qUUKuUhFE
                 * sex : 1
                 * age : null
                 * country : CN
                 * province :
                 * city :
                 * point : 0
                 * login_num : null
                 * rent_num : 0
                 * donate_num : 1
                 * check_in_hotel_num : 2
                 */

                private int yunsu_id;
                private String account;
                private String pwd;
                private String nickname;
                private String head_img_url;
                private String phone;
                private Object email;
                private Object qq;
                private String wx_unionid;
                private String wx_openid;
                private String sex;
                private Object age;
                private String country;
                private String province;
                private String city;
                private int point;
                private String login_num;
                private int rent_num;
                private int donate_num;
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

                public String getHead_img_url() {
                    return head_img_url;
                }

                public void setHead_img_url(String head_img_url) {
                    this.head_img_url = head_img_url;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public Object getEmail() {
                    return email;
                }

                public void setEmail(Object email) {
                    this.email = email;
                }

                public Object getQq() {
                    return qq;
                }

                public void setQq(Object qq) {
                    this.qq = qq;
                }

                public String getWx_unionid() {
                    return wx_unionid;
                }

                public void setWx_unionid(String wx_unionid) {
                    this.wx_unionid = wx_unionid;
                }

                public String getWx_openid() {
                    return wx_openid;
                }

                public void setWx_openid(String wx_openid) {
                    this.wx_openid = wx_openid;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public Object getAge() {
                    return age;
                }

                public void setAge(Object age) {
                    this.age = age;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public String getLogin_num() {
                    return login_num;
                }

                public void setLogin_num(String login_num) {
                    this.login_num = login_num;
                }

                public int getRent_num() {
                    return rent_num;
                }

                public void setRent_num(int rent_num) {
                    this.rent_num = rent_num;
                }

                public int getDonate_num() {
                    return donate_num;
                }

                public void setDonate_num(int donate_num) {
                    this.donate_num = donate_num;
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
}
