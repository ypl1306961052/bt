package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class OrderListData {


    /**
     * code : 100
     * msg : success
     * data : {"total":1,"per_page":20,"current_page":1,"last_page":1,"data":[{"hotel_order_id":334,"user_id":12142970,"hotel_id":24,"room_id":null,"hotel_name":"鸭川临水阁","hotel_img":"/uploads/20170606/bfd9e7d9be4ce3ae6bc91b3a20e1d34e.jpg","total_money":4645.01,"pay_money":2322.01,"deduction_money":2323,"guaranty_money":0,"return_guaranty_money":null,"status":0,"describe":null,"arrive_time":1499731200,"leave_time":1499817600,"create_time":1500539045,"update_time":1500539045}]}
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
         * data : [{"hotel_order_id":334,"user_id":12142970,"hotel_id":24,"room_id":null,"hotel_name":"鸭川临水阁","hotel_img":"/uploads/20170606/bfd9e7d9be4ce3ae6bc91b3a20e1d34e.jpg","total_money":4645.01,"pay_money":2322.01,"deduction_money":2323,"guaranty_money":0,"return_guaranty_money":null,"status":0,"describe":null,"arrive_time":1499731200,"leave_time":1499817600,"create_time":1500539045,"update_time":1500539045}]
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
             * hotel_order_id : 334
             * user_id : 12142970
             * hotel_id : 24
             * room_id : null
             * hotel_name : 鸭川临水阁
             * hotel_img : /uploads/20170606/bfd9e7d9be4ce3ae6bc91b3a20e1d34e.jpg
             * total_money : 4645.01
             * pay_money : 2322.01
             * deduction_money : 2323
             * guaranty_money : 0
             * return_guaranty_money : null
             * status : 0
             * describe : null
             * arrive_time : 1499731200
             * leave_time : 1499817600
             * create_time : 1500539045
             * update_time : 1500539045
             */

            private int hotel_order_id;
            private int user_id;
            private int hotel_id;
            private Object room_id;
            private String hotel_name;
            private String hotel_img;
            private double total_money;
            private double pay_money;
            private int deduction_money;
            private int guaranty_money;
            private Object return_guaranty_money;
            private int status;
            private Object describe;
            private int arrive_time;
            private int leave_time;
            private int create_time;
            private int update_time;

            public int getHotel_order_id() {
                return hotel_order_id;
            }

            public void setHotel_order_id(int hotel_order_id) {
                this.hotel_order_id = hotel_order_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getHotel_id() {
                return hotel_id;
            }

            public void setHotel_id(int hotel_id) {
                this.hotel_id = hotel_id;
            }

            public Object getRoom_id() {
                return room_id;
            }

            public void setRoom_id(Object room_id) {
                this.room_id = room_id;
            }

            public String getHotel_name() {
                return hotel_name;
            }

            public void setHotel_name(String hotel_name) {
                this.hotel_name = hotel_name;
            }

            public String getHotel_img() {
                return hotel_img;
            }

            public void setHotel_img(String hotel_img) {
                this.hotel_img = hotel_img;
            }

            public double getTotal_money() {
                return total_money;
            }

            public void setTotal_money(double total_money) {
                this.total_money = total_money;
            }

            public double getPay_money() {
                return pay_money;
            }

            public void setPay_money(double pay_money) {
                this.pay_money = pay_money;
            }

            public int getDeduction_money() {
                return deduction_money;
            }

            public void setDeduction_money(int deduction_money) {
                this.deduction_money = deduction_money;
            }

            public int getGuaranty_money() {
                return guaranty_money;
            }

            public void setGuaranty_money(int guaranty_money) {
                this.guaranty_money = guaranty_money;
            }

            public Object getReturn_guaranty_money() {
                return return_guaranty_money;
            }

            public void setReturn_guaranty_money(Object return_guaranty_money) {
                this.return_guaranty_money = return_guaranty_money;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getDescribe() {
                return describe;
            }

            public void setDescribe(Object describe) {
                this.describe = describe;
            }

            public int getArrive_time() {
                return arrive_time;
            }

            public void setArrive_time(int arrive_time) {
                this.arrive_time = arrive_time;
            }

            public int getLeave_time() {
                return leave_time;
            }

            public void setLeave_time(int leave_time) {
                this.leave_time = leave_time;
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
