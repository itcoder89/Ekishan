package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class GetProfileDataModel {

    @SerializedName("customers_id")
    String customers_id;
    @SerializedName("customers_firstname")
    String customers_firstname;
    @SerializedName("customers_newsletter")
    String customers_newsletter;
    @SerializedName("company_name")
    String company_name;
    @SerializedName("zip_code")
    String zip_code;
    @SerializedName("house_no")
    String house_no;
    @SerializedName("address")
    String address;
    @SerializedName("customers_email_address")
    String customers_email_address;
    @SerializedName("customers_picture")
    String customers_picture;

    @SerializedName("customers_telephone")
    String customers_telephone;

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getCustomers_firstname() {
        return customers_firstname;
    }

    public void setCustomers_firstname(String customers_firstname) {
        this.customers_firstname = customers_firstname;
    }

    public String getCustomers_newsletter() {
        return customers_newsletter;
    }

    public void setCustomers_newsletter(String customers_newsletter) {
        this.customers_newsletter = customers_newsletter;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getZip_code() {
        return zip_code==null?zip_code="":zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomers_email_address() {
        return customers_email_address;
    }

    public void setCustomers_email_address(String customers_email_address) {
        this.customers_email_address = customers_email_address;
    }

    public String getCustomers_telephone() {
        return customers_telephone;
    }

    public void setCustomers_telephone(String customers_telephone) {
        this.customers_telephone = customers_telephone;
    }

    public String getCustomers_picture() {
        return customers_picture;
    }

    public void setCustomers_picture(String customers_picture) {
        this.customers_picture = customers_picture;
    }
}
