package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

public class GetProductDetailsResponse {
    @Expose
    private String success;
    @Expose
    private  ProductDetailsProduct data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ProductDetailsProduct getData() {
        return data;
    }

    public void setData(ProductDetailsProduct data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
