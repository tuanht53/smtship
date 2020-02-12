package com.app.smt.shiper.data.model.order;

import java.io.Serializable;

public class CustomerInformation implements Serializable {

    private String custUserId;

    private String custUserName;

    private String custUserFullName;

    private String custUserMobile;

    private String custUserNote;

    private String custUserAddress;

    public String getCustUserId() {
        return custUserId;
    }

    public void setCustUserId(String custUserId) {
        this.custUserId = custUserId;
    }

    public String getCustUserName() {
        return custUserName;
    }

    public void setCustUserName(String custUserName) {
        this.custUserName = custUserName;
    }

    public String getCustUserFullName() {
        return custUserFullName;
    }

    public void setCustUserFullName(String custUserFullName) {
        this.custUserFullName = custUserFullName;
    }

    public String getCustUserMobile() {
        return custUserMobile;
    }

    public void setCustUserMobile(String custUserMobile) {
        this.custUserMobile = custUserMobile;
    }

    public String getCustUserNote() {
        return custUserNote;
    }

    public void setCustUserNote(String custUserNote) {
        this.custUserNote = custUserNote;
    }

    public String getCustUserAddress() {
        return custUserAddress;
    }

    public void setCustUserAddress(String custUserAddress) {
        this.custUserAddress = custUserAddress;
    }
}
