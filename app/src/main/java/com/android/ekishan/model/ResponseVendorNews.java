package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by ${} on 6/8/2020.
 */
public class ResponseVendorNews {
    @Expose
    private String success;
    @Expose
    private ArrayList<VendorNewsList> data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<VendorNewsList> getData() {
        return data;
    }

    public void setData(ArrayList<VendorNewsList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
