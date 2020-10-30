package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class OrderListModel {

    @SerializedName("orders_id")
    private String orders_id;

    @SerializedName("date_purchased")
    private String date_purchased;

    @SerializedName("order_price")
    private String order_price;

    @SerializedName("order_status")
    private String order_status;

    @SerializedName("products_count")
    private String products_count;

    @SerializedName("subscription_id")
    private String subscription_id;

    @SerializedName("preferred_delivery_date")
    private String preferred_delivery_date;

    @SerializedName("items")
    private String items;

    @SerializedName("total")
    private String total;
    @SerializedName("status")
    private String status;

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getDate_purchased() {
        return date_purchased;
    }

    public void setDate_purchased(String date_purchased) {
        this.date_purchased = date_purchased;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProducts_count() {
        return products_count;
    }

    public void setProducts_count(String products_count) {
        this.products_count = products_count;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getPreferred_delivery_date() {
        return preferred_delivery_date;
    }

    public void setPreferred_delivery_date(String preferred_delivery_date) {
        this.preferred_delivery_date = preferred_delivery_date;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
