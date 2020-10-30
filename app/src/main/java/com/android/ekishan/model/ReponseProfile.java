package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

public class ReponseProfile {
    @Expose
    private String success;
    @Expose
    private ProfileData data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ProfileData getData() {
        return data;
    }

    public void setData(ProfileData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}