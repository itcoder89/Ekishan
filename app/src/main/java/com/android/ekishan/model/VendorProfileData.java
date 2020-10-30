package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorProfileData {


    @SerializedName("vendors_firstname")
    private String vendors_firstname;
    @SerializedName("vendors_aadhar_number")
    private String vendors_aadhar_number;
    @SerializedName("vendors_email_address")
    private String vendors_email_address;
    @SerializedName("vendors_phone")
    private String vendors_phone;
    @SerializedName("image")
    private String image;
    @SerializedName("address")
    private String address;
    @SerializedName("postal_code")
    private String postal_code;

    public String getVendors_firstname() {
        return vendors_firstname;
    }

    public void setVendors_firstname(String vendors_firstname) {
        this.vendors_firstname = vendors_firstname;
    }

    public String getVendors_aadhar_number() {
        return vendors_aadhar_number;
    }

    public void setVendors_aadhar_number(String vendors_aadhar_number) {
        this.vendors_aadhar_number = vendors_aadhar_number;
    }

    public String getVendors_email_address() {
        return vendors_email_address;
    }

    public void setVendors_email_address(String vendors_email_address) {
        this.vendors_email_address = vendors_email_address;
    }

    public String getVendors_phone() {
        return vendors_phone;
    }

    public void setVendors_phone(String vendors_phone) {
        this.vendors_phone = vendors_phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
