package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class CustomerData {

    @SerializedName("customers_id")
    String customers_id;
    @SerializedName("customers_phone")
    String customers_phone;
    @SerializedName("refercode")
    String refercode;

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getCustomers_phone() {
        return customers_phone;
    }

    public void setCustomers_phone(String customers_phone) {
        this.customers_phone = customers_phone;
    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }
}
