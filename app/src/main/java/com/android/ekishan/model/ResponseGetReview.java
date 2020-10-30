package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/21/2020.
 */
public class ResponseGetReview {
    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private GetReviewData data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public GetReviewData getData() {
        return data;
    }

    public void setData(GetReviewData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
