package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class ApprovalDataModel {

    @SerializedName("is_approved")
    String is_approved;

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }
}
