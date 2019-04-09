package com.app.smt.shiper.data.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("userCode")
    private String userCode;

    @SerializedName("password")
    private String password;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
