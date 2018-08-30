package com.yunyou.icloudinn.bookhouse.JavaBean;


import java.util.List;

public class CalendarSelectorData {

    private List<CalendarSelectorData.DataBean>monthSelector;

    public List<DataBean> getMonthSelector() {
        return monthSelector;
    }

    public void setMonthSelector(List<DataBean> monthSelector) {
        this.monthSelector = monthSelector;
    }

    public static  class DataBean{
        private int type;
        private int mCommentPrice;
        private int price;
        private String day;
        private String month;

        public int getmCommentPrice() {
            return mCommentPrice;
        }

        public void setmCommentPrice(int mCommentPrice) {
            this.mCommentPrice = mCommentPrice;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "type=" + type +
                    ", mCommentPrice=" + mCommentPrice +
                    ", price=" + price +
                    ", day='" + day + '\'' +
                    ", month='" + month + '\'' +
                    '}';
        }
    }


}

