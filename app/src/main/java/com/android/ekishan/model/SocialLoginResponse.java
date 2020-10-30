package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class SocialLoginResponse {
    @Expose
    private String success;
    @Expose
    private ArrayList<CustomerData> data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<CustomerData> getData() {
        return data;
    }

    public void setData(ArrayList<CustomerData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
