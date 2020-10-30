package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeliveryOrders {
    @SerializedName("collect_amount")
    String collect_amount;
    @SerializedName("orders")
    private ArrayList<DeliveryOrderitems> orders;

    public ArrayList<DeliveryOrderitems> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<DeliveryOrderitems> orders) {
        this.orders = orders;
    }

    public String getCollect_amount() {
        return collect_amount;
    }

    public void setCollect_amount(String collect_amount) {
        this.collect_amount = collect_amount;
    }
}
