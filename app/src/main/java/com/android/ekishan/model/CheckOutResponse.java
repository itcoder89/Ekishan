package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

public class CheckOutResponse {
    @Expose
    private String success;
    @Expose
    private  CheckoutData data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public CheckoutData getData() {
        return data;
    }

    public void setData(CheckoutData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
