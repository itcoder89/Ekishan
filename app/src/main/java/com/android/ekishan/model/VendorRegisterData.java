package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorRegisterData {

    @SerializedName("vendors_firstname")
    private String vendors_firstname;
    @SerializedName("vendors_phone")
    private String vendors_phone;
    @SerializedName("vendors_email_address")
    private String vendors_email_address;
    @SerializedName("vendors_picture")
    private String vendors_picture;
    @SerializedName("vendors_aadhar_number")
    private String vendors_aadhar_number;
    @SerializedName("isActive")
    private String isActive;
    @SerializedName("otp")
    private String otp;
    @SerializedName("vendors_id")
    private String vendors_id;

    public String getVendors_firstname() {
        return vendors_firstname;
    }

    public void setVendors_firstname(String vendors_firstname) {
        this.vendors_firstname = vendors_firstname;
    }

    public String getVendors_phone() {
        return vendors_phone;
    }

    public void setVendors_phone(String vendors_phone) {
        this.vendors_phone = vendors_phone;
    }

    public String getVendors_email_address() {
        return vendors_email_address;
    }

    public void setVendors_email_address(String vendors_email_address) {
        this.vendors_email_address = vendors_email_address;
    }

    public String getVendors_picture() {
        return vendors_picture;
    }

    public void setVendors_picture(String vendors_picture) {
        this.vendors_picture = vendors_picture;
    }

    public String getVendors_aadhar_number() {
        return vendors_aadhar_number;
    }

    public void setVendors_aadhar_number(String vendors_aadhar_number) {
        this.vendors_aadhar_number = vendors_aadhar_number;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(String vendors_id) {
        this.vendors_id = vendors_id;
    }
}
