package com.app.smt.shiper.data.model.order;

import java.io.Serializable;

public class ReasonChangeMoney implements Serializable {

    private String reason;

    private int oldMoney;

    private int newMoney;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(int oldMoney) {
        this.oldMoney = oldMoney;
    }

    public int getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(int newMoney) {
        this.newMoney = newMoney;
    }
}
