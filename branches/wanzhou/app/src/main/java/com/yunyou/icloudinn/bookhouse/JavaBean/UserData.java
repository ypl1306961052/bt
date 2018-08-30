package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.io.Serializable;

public class UserData implements Serializable {


    /**
     * code : 100
     * msg : success
     * data : {"yunsu_id":12142942,"account":"07485544","pwd":"","nickname":"李俊宇","head_img_url":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpESclOtolqU9qSwzbVAWQAtfs6Kb32EKia8fsZ5ric4JBxcicxTuER5ItDRic7icOKJnwxsOtl7zVWHQ/0","phone":"18976332761","email":null,"qq":null,"wx_unionid":"","wx_openid":"oRmgM0bVvPZ-rEHSuXJV5vNQSRoQ","sex":"1","age":null,"country":"CN","province":"Guangxi","city":"Guilin","point":0,"yun_coin":0,"login_num":null,"access_token":"N2NhMDlkOTIyZjZkN2Y3MjY4YmM2ODkwOTExNzUwNDMxNDk4NjE0MjIz","create_time":"2017-06-28 09:40:15"}
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

    public static class DataBean {
        /**
         * yunsu_id : 12142942
         * account : 07485544
         * pwd :
         * nickname : 李俊宇
         * head_img_url : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpESclOtolqU9qSwzbVAWQAtfs6Kb32EKia8fsZ5ric4JBxcicxTuER5ItDRic7icOKJnwxsOtl7zVWHQ/0
         * phone : 18976332761
         * email : null
         * qq : null
         * wx_unionid :
         * wx_openid : oRmgM0bVvPZ-rEHSuXJV5vNQSRoQ
         * sex : 1
         * age : null
         * country : CN
         * province : Guangxi
         * city : Guilin
         * point : 0
         * yun_coin : 0
         * login_num : null
         * access_token : N2NhMDlkOTIyZjZkN2Y3MjY4YmM2ODkwOTExNzUwNDMxNDk4NjE0MjIz
         * create_time : 2017-06-28 09:40:15
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
        private int yun_coin;
        private Object login_num;
        private String access_token;
        private String create_time;

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

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
