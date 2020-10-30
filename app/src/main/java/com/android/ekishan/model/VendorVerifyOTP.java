package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorVerifyOTP {
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;

    @SerializedName("vendors_id")
    private String vendors_id;

    @SerializedName("is_approved")
    private String is_approved;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(String vendors_id) {
        this.vendors_id = vendors_id;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
