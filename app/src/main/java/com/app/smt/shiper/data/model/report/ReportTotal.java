package com.app.smt.shiper.data.model.report;

public class ReportTotal {

    private String totalMoneyStr = "0";

    private String feeShipStr = "0";

    private int countSaleOrder;

    public String getTotalMoneyStr() {
        return totalMoneyStr;
    }

    public void setTotalMoneyStr(String totalMoneyStr) {
        this.totalMoneyStr = totalMoneyStr;
    }

    public String getFeeShipStr() {
        return feeShipStr;
    }

    public void setFeeShipStr(String feeShipStr) {
        this.feeShipStr = feeShipStr;
    }

    public int getCountSaleOrder() {
        return countSaleOrder;
    }

    public void setCountSaleOrder(int countSaleOrder) {
        this.countSaleOrder = countSaleOrder;
    }
}
