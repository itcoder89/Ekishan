package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 6/8/2020.
 */
public class ResponseVerifyOtp {
    @Expose
    private String success;
    @Expose
    private VendorVerifyOTP data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public VendorVerifyOTP getData() {
        return data;
    }

    public void setData(VendorVerifyOTP data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
