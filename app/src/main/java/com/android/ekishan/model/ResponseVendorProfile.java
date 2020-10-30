package com.android.ekishan.model;

import com.google.gson.annotations.Expose;

/**
 * Created by ${} on 6/8/2020.
 */
public class ResponseVendorProfile {
    @Expose
    private String success;
    @Expose
    private VendorProfileData data;
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public VendorProfileData getData() {
        return data;
    }

    public void setData(VendorProfileData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

