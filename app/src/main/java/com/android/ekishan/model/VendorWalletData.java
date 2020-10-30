package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorWalletData {
    @SerializedName("vendors_id")
    private String vendors_id;
    @SerializedName("balance")
    private String balance;
    @SerializedName("transactions")
    private ArrayList<VendorWalletTransactionList> transactions;

    public String getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(String vendors_id) {
        this.vendors_id = vendors_id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ArrayList<VendorWalletTransactionList> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<VendorWalletTransactionList> transactions) {
        this.transactions = transactions;
    }
}
