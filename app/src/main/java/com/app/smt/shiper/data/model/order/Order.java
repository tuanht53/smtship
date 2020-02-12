package com.app.smt.shiper.data.model.order;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private String txtId;

    private CustomerInformation customerInformation;

    private AddressForDelivery addressForDelivery;

    private SaleOrderInformationCommon saleOrderInformationCommon;

    private ArrayList<Products> products;

    private Shipper shipperReceiver;

    private Shipper shipperDelivery;

    private int createBy;

    private String workflow;

    private int status;

    private StoreInformation storeInformation;

    private String note;

    private String reason;

    private long createDate;

    private long updateDate;

    private ReasonChangeMoney reasonChangeMoney;

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }

    public AddressForDelivery getAddressForDelivery() {
        return addressForDelivery;
    }

    public void setAddressForDelivery(AddressForDelivery addressForDelivery) {
        this.addressForDelivery = addressForDelivery;
    }

    public SaleOrderInformationCommon getSaleOrderInformationCommon() {
        return saleOrderInformationCommon;
    }

    public void setSaleOrderInformationCommon(SaleOrderInformationCommon saleOrderInformationCommon) {
        this.saleOrderInformationCommon = saleOrderInformationCommon;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public Shipper getShipperReceiver() {
        return shipperReceiver;
    }

    public void setShipperReceiver(Shipper shipperReceiver) {
        this.shipperReceiver = shipperReceiver;
    }

    public Shipper getShipperDelivery() {
        return shipperDelivery;
    }

    public void setShipperDelivery(Shipper shipperDelivery) {
        this.shipperDelivery = shipperDelivery;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StoreInformation getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation(StoreInformation storeInformation) {
        this.storeInformation = storeInformation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReasonChangeMoney getReasonChangeMoney() {
        return reasonChangeMoney;
    }

    public void setReasonChangeMoney(ReasonChangeMoney reasonChangeMoney) {
        this.reasonChangeMoney = reasonChangeMoney;
    }
}
