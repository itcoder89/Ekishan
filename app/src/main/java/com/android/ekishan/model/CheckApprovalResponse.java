package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class CheckApprovalResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ApprovalDataModel data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApprovalDataModel getData() {
        return data;
    }

    public void setData(ApprovalDataModel data) {
        this.data = data;
    }
}
