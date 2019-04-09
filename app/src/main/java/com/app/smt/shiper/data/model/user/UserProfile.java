package com.app.smt.shiper.data.model.user;

import com.app.smt.shiper.data.prefs.ModelPreference;

public class UserProfile implements ModelPreference {

    private String txtId;

    private String userName;

    private String fullName;

    private int status;

    private AddressInfo addressInfo;

    private String fcmDeviceId;

    private boolean isAdmin;

    private long createDate;

    private long updateDate;

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getFcmDeviceId() {
        return fcmDeviceId;
    }

    public void setFcmDeviceId(String fcmDeviceId) {
        this.fcmDeviceId = fcmDeviceId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
