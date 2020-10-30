package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("customers_id")
    private String customers_id;
    @SerializedName("customers_gender")
    private String customers_gender;
    @SerializedName("customers_firstname")
    private String customers_firstname;
    @SerializedName("customers_lastname")
    private String customers_lastname;
    @SerializedName("customers_email_address")
    private String customers_email_address;
    @SerializedName("customers_picture")
    private String customers_picture;
    @SerializedName("otp")
    private String otp;
    @SerializedName("refercode")
    private String refercode;

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getCustomers_gender() {
        return customers_gender;
    }

    public void setCustomers_gender(String customers_gender) {
        this.customers_gender = customers_gender;
    }

    public String getCustomers_firstname() {
        return customers_firstname;
    }

    public void setCustomers_firstname(String customers_firstname) {
        this.customers_firstname = customers_firstname;
    }

    public String getCustomers_lastname() {
        return customers_lastname;
    }

    public void setCustomers_lastname(String customers_lastname) {
        this.customers_lastname = customers_lastname;
    }

    public String getCustomers_email_address() {
        return customers_email_address;
    }

    public void setCustomers_email_address(String customers_email_address) {
        this.customers_email_address = customers_email_address;
    }

    public String getCustomers_picture() {
        return customers_picture;
    }

    public void setCustomers_picture(String customers_picture) {
        this.customers_picture = customers_picture;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }
}
