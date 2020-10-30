package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class GetVegResponse {

    @Expose
    private String success;
    @Expose
    private GetVegData data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public GetVegData getData() {
        return data;
    }

    public void setData(GetVegData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
