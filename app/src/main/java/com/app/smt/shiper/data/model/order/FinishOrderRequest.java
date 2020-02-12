package com.app.smt.shiper.data.model.order;

import java.util.ArrayList;

public class FinishOrderRequest {

    private ArrayList<String> saleOrderIds;

    public ArrayList<String> getSaleOrderIds() {
        return saleOrderIds;
    }

    public void setSaleOrderIds(ArrayList<String> saleOrderIds) {
        this.saleOrderIds = saleOrderIds;
    }
}
