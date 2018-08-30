package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class BookTypeData  {

    /**
     * code : 100
     * msg : success
     * data : {"total":5,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":5,"resource_type":2,"name":"成功励志"},{"id":6,"resource_type":2,"name":"美容塑身"},{"id":7,"resource_type":2,"name":"心理学"},{"id":8,"resource_type":2,"name":"经济管理"},{"id":9,"resource_type":2,"name":"其他"}]}
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
         * total : 5
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":5,"resource_type":2,"name":"成功励志"},{"id":6,"resource_type":2,"name":"美容塑身"},{"id":7,"resource_type":2,"name":"心理学"},{"id":8,"resource_type":2,"name":"经济管理"},{"id":9,"resource_type":2,"name":"其他"}]
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
             * id : 5
             * resource_type : 2
             * name : 成功励志
             */

            private int id;
            private int resource_type;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getResource_type() {
                return resource_type;
            }

            public void setResource_type(int resource_type) {
                this.resource_type = resource_type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
