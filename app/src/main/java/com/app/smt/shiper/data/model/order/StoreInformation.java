package com.app.smt.shiper.data.model.order;

import java.io.Serializable;

public class StoreInformation implements Serializable {

    private String storeId;

    private String storeName;

    private String storeMobile;

    private String storeUserId;

    private String storeUserName;

    private String storeUserFullName;

    private AddressForDelivery address;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public String getStoreUserFullName() {
        return storeUserFullName;
    }

    public void setStoreUserFullName(String storeUserFullName) {
        this.storeUserFullName = storeUserFullName;
    }

    public String getStoreMobile() {
        return storeMobile;
    }

    public void setStoreMobile(String storeMobile) {
        this.storeMobile = storeMobile;
    }

    public AddressForDelivery getAddress() {
        return address;
    }

    public void setAddress(AddressForDelivery address) {
        this.address = address;
    }
}
