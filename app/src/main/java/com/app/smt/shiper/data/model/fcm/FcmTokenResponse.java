package com.app.smt.shiper.data.model.fcm;

public class FcmTokenResponse {

    private String phone_type;

    private String user_uid;

    private String app_uid;

    private String registration_token;

    private String jwt_notification;

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getApp_uid() {
        return app_uid;
    }

    public void setApp_uid(String app_uid) {
        this.app_uid = app_uid;
    }

    public String getRegistration_token() {
        return registration_token;
    }

    public void setRegistration_token(String registration_token) {
        this.registration_token = registration_token;
    }

    public String getJwt_notification() {
        return jwt_notification;
    }

    public void setJwt_notification(String jwt_notification) {
        this.jwt_notification = jwt_notification;
    }
}
