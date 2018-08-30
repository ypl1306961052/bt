package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class DonateJson {
    String phone;
    List<DonateBookItem>list;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<DonateBookItem> getList() {
        return list;
    }

    public void setList(List<DonateBookItem> list) {
        this.list = list;
    }
}
