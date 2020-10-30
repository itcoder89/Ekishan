package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class reponseHome {
    @Expose
    private String success;
    @Expose
    private  ResponseHomeMain data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResponseHomeMain getData() {
        return data;
    }

    public void setData(ResponseHomeMain data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
