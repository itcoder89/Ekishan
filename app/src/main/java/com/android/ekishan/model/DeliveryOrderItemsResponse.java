package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeliveryOrderItemsResponse {
    @SerializedName("success")
    private int  success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DeliveryOrders data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeliveryOrders getData() {
        return data;
    }

    public void setData(DeliveryOrders data) {
        this.data = data;
    }
}
