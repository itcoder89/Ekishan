package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorWalletTransactionList {

    @SerializedName("vendors_transaction_id")
    private String vendors_transaction_id;
    @SerializedName("vendors_id")
    private String vendors_id;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("amount")
    private String amount;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("transaction_status_description")
    private String transaction_status_description;
    @SerializedName("transaction_id")
    private String transaction_id;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getVendors_transaction_id() {
        return vendors_transaction_id;
    }

    public void setVendors_transaction_id(String vendors_transaction_id) {
        this.vendors_transaction_id = vendors_transaction_id;
    }

    public String getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(String vendors_id) {
        this.vendors_id = vendors_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction_status_description() {
        return transaction_status_description;
    }

    public void setTransaction_status_description(String transaction_status_description) {
        this.transaction_status_description = transaction_status_description;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
