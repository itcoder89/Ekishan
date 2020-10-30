package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class ProfileData {

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
    @SerializedName("customers_dob")
    private String customers_dob;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("wallet")
    private String wallet;
    @SerializedName("customers_default_address_id")
    private String customers_default_address_id;
    @SerializedName("customers_telephone")
    private String customers_telephone;
    @SerializedName("customers_fax")
    private String customers_fax;
    @SerializedName("customers_password")
    private String customers_password;
    @SerializedName("customers_newsletter")
    private String customers_newsletter;
    @SerializedName("isActive")
    private int isActive;
    @SerializedName("fb_id")
    private int fb_id;
    @SerializedName("google_id")
    private int google_id;
    @SerializedName("is_seen")
    private int is_seen;

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

    public String getCustomers_dob() {
        return customers_dob;
    }

    public void setCustomers_dob(String customers_dob) {
        this.customers_dob = customers_dob;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getCustomers_default_address_id() {
        return customers_default_address_id;
    }

    public void setCustomers_default_address_id(String customers_default_address_id) {
        this.customers_default_address_id = customers_default_address_id;
    }

    public String getCustomers_telephone() {
        return customers_telephone;
    }

    public void setCustomers_telephone(String customers_telephone) {
        this.customers_telephone = customers_telephone;
    }

    public String getCustomers_fax() {
        return customers_fax;
    }

    public void setCustomers_fax(String customers_fax) {
        this.customers_fax = customers_fax;
    }

    public String getCustomers_password() {
        return customers_password;
    }

    public void setCustomers_password(String customers_password) {
        this.customers_password = customers_password;
    }

    public String getCustomers_newsletter() {
        return customers_newsletter;
    }

    public void setCustomers_newsletter(String customers_newsletter) {
        this.customers_newsletter = customers_newsletter;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getFb_id() {
        return fb_id;
    }

    public void setFb_id(int fb_id) {
        this.fb_id = fb_id;
    }

    public int getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(int google_id) {
        this.google_id = google_id;
    }

    public int getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(int is_seen) {
        this.is_seen = is_seen;
    }
}
