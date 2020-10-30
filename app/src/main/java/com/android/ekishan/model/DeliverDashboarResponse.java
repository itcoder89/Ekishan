package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 6/23/2020.
 */
public class DeliverDashboarResponse {
    @SerializedName("success")
    private int  success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<DeliveryDashboardData> data;

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

    public ArrayList<DeliveryDashboardData> getData() {
        return data;
    }

    public void setData(ArrayList<DeliveryDashboardData> data) {
        this.data = data;
    }
}
