package com.app.smt.shiper.data.model.order;

import java.util.ArrayList;

public class SaleOrderCallLog {

    private String saleOrderId;

    private ArrayList<CallLog> callLog;

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public ArrayList<CallLog> getCallLog() {
        return callLog;
    }

    public void setCallLog(ArrayList<CallLog> callLog) {
        this.callLog = callLog;
    }
}
