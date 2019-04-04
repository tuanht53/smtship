package com.app.smt.shiper.data.model;

public class TokenRequest {

    private String third_party = "FACEBOOK";

    private String access_token;

    private int ttl = 2592000; // 30 ngay = 86400 x 30

    public String getThird_party() {
        return third_party;
    }

    public void setThird_party(String third_party) {
        this.third_party = third_party;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
