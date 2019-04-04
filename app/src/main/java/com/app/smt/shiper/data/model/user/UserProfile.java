package com.app.smt.shiper.data.model.user;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String uid;

    private long created_at;

    private long upadted_at;

    private String status;

    private String email;

    private String phone;

    private String name;

    private boolean email_validate;

    private String avatar;

    private int booking_total;

    private float distance_total;

    private float rating;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpadted_at() {
        return upadted_at;
    }

    public void setUpadted_at(long upadted_at) {
        this.upadted_at = upadted_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmail_validate() {
        return email_validate;
    }

    public void setEmail_validate(boolean email_validate) {
        this.email_validate = email_validate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBooking_total() {
        return booking_total;
    }

    public void setBooking_total(int booking_total) {
        this.booking_total = booking_total;
    }

    public float getDistance_total() {
        return distance_total;
    }

    public void setDistance_total(float distance_total) {
        this.distance_total = distance_total;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
