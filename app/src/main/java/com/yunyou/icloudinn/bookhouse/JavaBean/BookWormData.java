package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class BookWormData {

    /**
     * code : 100
     * msg : success
     * data : [{"user_id":12142941,"user_name":"『～青～  』","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0","donate_num":5,"rent_num":5,"is_concern":0},{"user_id":12142928,"user_name":"狂野小青年","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/Y9Qh7mYcBoLKYD6TaV0ElNQV07NrHhlJUeePvRJIRAicXH2n9U5PkTXmibS21KVnxRxTvevQs9NnkXwjicJkHCr0Q/0","donate_num":4,"rent_num":2,"is_concern":1},{"user_id":12142943,"user_name":"Captain","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoSKYK3QzEjjlVoM6XK5ks0o1Jw3Ar1nlgpXtITtTJMGYX568SQyn7aIwic67pZtF6mvBqPlRp0okQ/0","donate_num":1,"rent_num":0,"is_concern":0},{"user_id":12142933,"user_name":"Spencer","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eom6Erof6WLbohbX8KEU2k46DmOSSD7xNT0qYOwVliaLT578gGicH8gicRSbN5sfpviaDxzib4tTcRXDOg/0","donate_num":1,"rent_num":2,"is_concern":0},{"user_id":12142942,"user_name":"李俊宇","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJpESclOtolqU9qSwzbVAWQAtfs6Kb32EKia8fsZ5ric4JBxcicxTuER5ItDRic7icOKJnwxsOtl7zVWHQ/0","donate_num":1,"rent_num":0,"is_concern":1},{"user_id":12142932,"user_name":"汪君相","user_head_img":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIRYmb8O0nKj3Ks3U8DiaU4DFqQ7d4VUoq3NlAwqb2qC3By1pbhYq2fnytibl0YicEE6zbZjNG4p2icrw/0","donate_num":0,"rent_num":3,"is_concern":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 12142941
         * user_name : 『～青～  』
         * user_head_img : http://wx.qlogo.cn/mmopen/vi_32/nyPvrKDwpQRiaeCjqShppujH9yTHTBMwt1nLNnkSqYaXFbWkCTa9c8ZGQFGtGym3cKvFsPiaTpYdgBlnFFINQnibQ/0
         * donate_num : 5
         * rent_num : 5
         * is_concern : 0
         */

        private int user_id;
        private String user_name;
        private String user_head_img;
        private int donate_num;
        private int rent_num;
        private int is_concern;

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

        public int getDonate_num() {
            return donate_num;
        }

        public void setDonate_num(int donate_num) {
            this.donate_num = donate_num;
        }

        public int getRent_num() {
            return rent_num;
        }

        public void setRent_num(int rent_num) {
            this.rent_num = rent_num;
        }

        public int getIs_concern() {
            return is_concern;
        }

        public void setIs_concern(int is_concern) {
            this.is_concern = is_concern;
        }
    }
}
