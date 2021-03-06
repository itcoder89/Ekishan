package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyAddressListResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private ArrayList<AddressListModel> data;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<AddressListModel> getData() {
        return data;
    }

    public void setData(ArrayList<AddressListModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
