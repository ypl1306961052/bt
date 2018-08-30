package com.yunyou.icloudinn.bookhouse.JavaBean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 关注数据表
 * Created by chen on 2017/10/11.
 */

public class ConcernData implements Serializable {


    /**
     * concern_id : 616
     * user_id : 12143049
     * concern_user_id : 12142509
     * concern_time : 1507618517
     * user :
     */

    @JSONField(name = "concern_id")
    private int concernId;

    @JSONField(name = "user_id")
    private int userId;

    @JSONField(name = "concern_user_id")
    private int concernUserId;

    @JSONField(name = "concern_time",format = "yyyy-MM-dd HH:mm:ss")
    private int concernTime;

    private UserData user;

    public int getConcernId() {
        return concernId;
    }

    public void setConcernId(int concernId) {
        this.concernId = concernId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getConcernUserId() {
        return concernUserId;
    }

    public void setConcernUserId(int concernUserId) {
        this.concernUserId = concernUserId;
    }

    public int getConcernTime() {
        return concernTime;
    }

    public void setConcernTime(int concernTime) {
        this.concernTime = concernTime;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
}
