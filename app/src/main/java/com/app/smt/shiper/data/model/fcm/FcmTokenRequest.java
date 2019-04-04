package com.app.smt.shiper.data.model.fcm;

public class FcmTokenRequest {

    private String registration_token;

    public FcmTokenRequest(String token) {
        this.registration_token = token;
    }

    public String getRegistration_token() {
        return registration_token;
    }

    public void setRegistration_token(String registration_token) {
        this.registration_token = registration_token;
    }

}
