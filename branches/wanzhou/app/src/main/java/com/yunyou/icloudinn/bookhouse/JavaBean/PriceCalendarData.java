package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class PriceCalendarData {


    /**
     * code : 100
     * msg : success
     * data : {"already_reservation":["2017-8-17","2017-8-18","2017-8-19","2017-8-20","2017-8-21","2017-8-22","2017-8-23","2017-8-24","2017-9-6","2017-9-14","2017-9-15","2017-8-2","2017-8-4"],"price_calendar":[{"id":63,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-1"},{"id":64,"house_id":19,"room_id":null,"type":1,"price":200,"day":"2017-8-2"},{"id":65,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-3"},{"id":66,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-4"},{"id":67,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-5"},{"id":68,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-6"},{"id":69,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-7"},{"id":70,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-8"},{"id":71,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-9"},{"id":72,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-10"},{"id":73,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-11"},{"id":74,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-12"},{"id":75,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-13"},{"id":76,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-14"},{"id":77,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-15"},{"id":78,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-16"},{"id":79,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-17"},{"id":80,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-18"},{"id":81,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-19"},{"id":82,"house_id":19,"room_id":null,"type":1,"price":100,"day":"2017-8-20"}]}
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
        private List<String> already_reservation;
        private List<PriceCalendarBean> price_calendar;

        public List<String> getAlready_reservation() {
            return already_reservation;
        }

        public void setAlready_reservation(List<String> already_reservation) {
            this.already_reservation = already_reservation;
        }

        public List<PriceCalendarBean> getPrice_calendar() {
            return price_calendar;
        }

        public void setPrice_calendar(List<PriceCalendarBean> price_calendar) {
            this.price_calendar = price_calendar;
        }

        public static class PriceCalendarBean {
            /**
             * id : 63
             * house_id : 19
             * room_id : null
             * type : 1
             * price : 100
             * day : 2017-8-1
             */

            private int id;
            private int house_id;
            private Object room_id;
            private int type;
            private int price;
            private String day;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getHouse_id() {
                return house_id;
            }

            public void setHouse_id(int house_id) {
                this.house_id = house_id;
            }

            public Object getRoom_id() {
                return room_id;
            }

            public void setRoom_id(Object room_id) {
                this.room_id = room_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }
        }
    }
}
