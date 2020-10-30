package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private GetProfileDataModel data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public GetProfileDataModel getData() {
        return data;
    }

    public void setData(GetProfileDataModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
