package com.app.smt.shiper.data.model.order;

import java.io.Serializable;

public class SaleOrderInformationCommon implements Serializable {

    private int totalMoney;

    private int totalMoneyFromProduct;

    private int feeShip;

    private String saleOrderCode;

    private int priority;

    private boolean isStatementMoneyForStore;

    private boolean isCustomerAlreadyPayment;

    private boolean isCustomerBlame;

    private long timeDelivery;

    private String changeType;

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalMoneyFromProduct() {
        return totalMoneyFromProduct;
    }

    public void setTotalMoneyFromProduct(int totalMoneyFromProduct) {
        this.totalMoneyFromProduct = totalMoneyFromProduct;
    }

    public int getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(int feeShip) {
        this.feeShip = feeShip;
    }

    public String getSaleOrderCode() {
        return saleOrderCode;
    }

    public void setSaleOrderCode(String saleOrderCode) {
        this.saleOrderCode = saleOrderCode;
    }

    public boolean isStatementMoneyForStore() {
        return isStatementMoneyForStore;
    }

    public void setStatementMoneyForStore(boolean statementMoneyForStore) {
        isStatementMoneyForStore = statementMoneyForStore;
    }

    public boolean isCustomerAlreadyPayment() {
        return isCustomerAlreadyPayment;
    }

    public void setCustomerAlreadyPayment(boolean customerAlreadyPayment) {
        isCustomerAlreadyPayment = customerAlreadyPayment;
    }

    public boolean isCustomerBlame() {
        return isCustomerBlame;
    }

    public void setCustomerBlame(boolean customerBlame) {
        isCustomerBlame = customerBlame;
    }

    public long getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(long timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
