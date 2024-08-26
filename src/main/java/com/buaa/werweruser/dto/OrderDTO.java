package com.buaa.werweruser.dto;

import com.buaa.werweruser.entity.Order;

import java.util.Date;

public class OrderDTO {
    private String oid;
    private String uid;
    private String orderStatus;
    private String orderType;
    private String billTime;
    private String cancelTime;

    private Double total;

    public OrderDTO(String oid, String uid, String billTime, Double total, String orderStatus, String orderType) {
        this.oid = oid;
        this.uid = uid;
        this.billTime = billTime;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
    }

    // Getters and Setters

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

