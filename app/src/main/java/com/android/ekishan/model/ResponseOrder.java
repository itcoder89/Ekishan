package com.android.ekishan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseOrder {
    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private ArrayList<OrderListModel> data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<OrderListModel> getData() {
        return data;
    }

    public void setData(ArrayList<OrderListModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
