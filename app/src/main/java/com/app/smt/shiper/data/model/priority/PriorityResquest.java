package com.app.smt.shiper.data.model.priority;

import java.util.ArrayList;

public class PriorityResquest {

    private ArrayList<String> saleOrderIds;

    private int priority;

    public PriorityResquest(String idOrder, int priority) {
        this.priority = priority;
        saleOrderIds = new ArrayList<>();
        saleOrderIds.add(idOrder);
    }

    public ArrayList<String> getSaleOrderIds() {
        return saleOrderIds;
    }

    public void setSaleOrderIds(ArrayList<String> saleOrderIds) {
        this.saleOrderIds = saleOrderIds;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
