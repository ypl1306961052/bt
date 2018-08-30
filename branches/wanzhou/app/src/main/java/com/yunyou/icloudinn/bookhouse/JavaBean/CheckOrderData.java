package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class CheckOrderData {

    /**
     * code : 100
     * msg : 下单成功
     * data : {"user_id":12142928,"user_name":"狂野小青年","type":"1","pay_money":"34","arrive_time":"232","leave_time":"23","describe":"花舍电影坊","create_time":1498208172,"update_time":1498208172,"id":"203","item":[{"id":145,"order_id":203,"source_id":20,"type":0,"source_name":"花舍电影坊","img":"/uploads/20170531/31dbfe3412c4169296aabfc929962189.jpg","price":999,"num":1,"total_money":999}]}
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
         * user_id : 12142928
         * user_name : 狂野小青年
         * type : 1
         * pay_money : 34
         * arrive_time : 232
         * leave_time : 23
         * describe : 花舍电影坊
         * create_time : 1498208172
         * update_time : 1498208172
         * id : 203
         * item : [{"id":145,"order_id":203,"source_id":20,"type":0,"source_name":"花舍电影坊","img":"/uploads/20170531/31dbfe3412c4169296aabfc929962189.jpg","price":999,"num":1,"total_money":999}]
         */

        private int user_id;
        private String user_name;
        private String type;
        private String pay_money;
        private String arrive_time;
        private String leave_time;
        private String describe;
        private int create_time;
        private int update_time;
        private String id;
        private List<ItemBean> item;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getLeave_time() {
            return leave_time;
        }

        public void setLeave_time(String leave_time) {
            this.leave_time = leave_time;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 145
             * order_id : 203
             * source_id : 20
             * type : 0
             * source_name : 花舍电影坊
             * img : /uploads/20170531/31dbfe3412c4169296aabfc929962189.jpg
             * price : 999
             * num : 1
             * total_money : 999
             */

            private int id;
            private int order_id;
            private int source_id;
            private int type;
            private String source_name;
            private String img;
            private int price;
            private int num;
            private int total_money;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
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

            public String getSource_name() {
                return source_name;
            }

            public void setSource_name(String source_name) {
                this.source_name = source_name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getTotal_money() {
                return total_money;
            }

            public void setTotal_money(int total_money) {
                this.total_money = total_money;
            }
        }
    }
}
