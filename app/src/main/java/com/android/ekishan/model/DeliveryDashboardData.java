package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/23/2020.
 */
public class DeliveryDashboardData {

    @SerializedName("orders_id")
    private String orders_id;
    @SerializedName("subscription_id")
    private String subscription_id;
    @SerializedName("package_delivery_id")
    private String package_delivery_id;
    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String longs;

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("vendors_sales_id")
    private String vendors_sales_id;

    @SerializedName("order_status")
    private String order_status;
    @SerializedName("distance")
    private String distance;
    @SerializedName("payment_status")
    private String payment_status;
    @SerializedName("order_type")
    private String order_type;
    @SerializedName("agro_item_id")
    private String agro_item_id;

    public String getOrders_date() {
        return orders_date;
    }

    public void setOrders_date(String orders_date) {
        this.orders_date = orders_date;
    }

    @SerializedName("orders_date")
    private String orders_date;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getVendors_sales_id() {
        return vendors_sales_id;
    }

    public void setVendors_sales_id(String vendors_sales_id) {
        this.vendors_sales_id = vendors_sales_id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat==null?"0.0":lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLongs() {
        return longs==null?"0.0":longs;
    }

    public void setLongs(String longs) {
        this.longs = longs;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getPackage_delivery_id() {
        return package_delivery_id;
    }

    public void setPackage_delivery_id(String package_delivery_id) {
        this.package_delivery_id = package_delivery_id;
    }

    public String getOrder_status() {
        return order_status==null?"Pending":order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getAgro_item_id() {
        return agro_item_id;
    }

    public void setAgro_item_id(String agro_item_id) {
        this.agro_item_id = agro_item_id;
    }
}


