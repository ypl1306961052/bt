package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by function on 2017/10/9.
 */

public class IMsigData {
    /**
     * code : 100
     * msg : success
     * data : {"user_id":12143049,"sdkAppID":"1400011424","accountType":"5992","imsig":"eJxNjctugkAUht*FLU2dK5YmXdiKqQZDtaDEzWSEQY4GRBgppum7FygmPbvzf--l2-Ddz8cqPglZFBAbzwZmCCGMGWHGQw9VU0CphEy0KjvOOSetZaB9TEgtaNml77KGTPVmNObEptQedIhVriGBvyqCGUXszmQUna*5FvpWqH9VFRzab*kEb-NptLCaVfi0smZ1MD42zLESlNreZbfZac28hekethAepT*fgDMZhdnWrb34dqJEvi*DyDcvSbpPr-5sVG-2mb8Gc6pe1x-W18swVquygnPeDhKEOSYUdWf8-AK2PFb6"}
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
         * user_id : 12143049
         * sdkAppID : 1400011424
         * accountType : 5992
         * imsig : eJxNjctugkAUht*FLU2dK5YmXdiKqQZDtaDEzWSEQY4GRBgppum7FygmPbvzf--l2-Ddz8cqPglZFBAbzwZmCCGMGWHGQw9VU0CphEy0KjvOOSetZaB9TEgtaNml77KGTPVmNObEptQedIhVriGBvyqCGUXszmQUna*5FvpWqH9VFRzab*kEb-NptLCaVfi0smZ1MD42zLESlNreZbfZac28hekethAepT*fgDMZhdnWrb34dqJEvi*DyDcvSbpPr-5sVG-2mb8Gc6pe1x-W18swVquygnPeDhKEOSYUdWf8-AK2PFb6
         */

        private int user_id;
        private String sdkAppID;
        private String accountType;
        private String imsig;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getSdkAppID() {
            return sdkAppID;
        }

        public void setSdkAppID(String sdkAppID) {
            this.sdkAppID = sdkAppID;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getImsig() {
            return imsig;
        }

        public void setImsig(String imsig) {
            this.imsig = imsig;
        }
    }
}
