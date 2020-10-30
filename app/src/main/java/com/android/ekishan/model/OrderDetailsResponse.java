package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderDetailsResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private OrderDetailsModel data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public OrderDetailsModel getData() {
        return data;
    }

    public void setData(OrderDetailsModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
