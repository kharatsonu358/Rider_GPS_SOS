package com.avinash.rider_gps_sos.Model;

public class FeedBacks {
    String UserPhone,FeedType,FeedBackMsg;

    public FeedBacks(String userPhone, String feedType, String feedBackMsg) {
        UserPhone = userPhone;
        FeedType = feedType;
        FeedBackMsg = feedBackMsg;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getFeedType() {
        return FeedType;
    }

    public void setFeedType(String feedType) {
        FeedType = feedType;
    }

    public String getFeedBackMsg() {
        return FeedBackMsg;
    }

    public void setFeedBackMsg(String feedBackMsg) {
        FeedBackMsg = feedBackMsg;
    }
}
