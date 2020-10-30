package com.android.ekishan.model;

import com.android.ekishan.MyApplication;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderDetailsModel {

    @SerializedName("orders_id")
    private String orders_id;
    @SerializedName("order_status")
    private String order_status;
    @SerializedName("order_booking_date")
    private String order_booking_date;
    @SerializedName("order_booking_time")
    private String order_booking_time;
    @SerializedName("delivery_name")
    private String delivery_name;
    @SerializedName("delivery_street_address")
    private String delivery_street_address;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("delivery_suburb")
    private String delivery_suburb;
    @SerializedName("delivery_city")
    private String delivery_city;
    @SerializedName("delivery_postcode")
    private String delivery_postcode;
    @SerializedName("customers_telephone")
    private String customers_telephone;
    @SerializedName("payment_method")
    private String payment_method;
    @SerializedName("products_count")
    private String products_count;
    @SerializedName("name")
    private String name;
    @SerializedName("order_price")
    private String order_price;
    @SerializedName("package_delivery_date")
    private String package_delivery_date;
    @SerializedName("package_number_of_deliveries")
    private String package_number_of_deliveries;
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("package_status")
    private String package_status;

    @SerializedName("products")
    private ArrayList<OrderDetailsProductListModel> products;

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_booking_date() {
        return order_booking_date;
    }

    public void setOrder_booking_date(String order_booking_date) {
        this.order_booking_date = order_booking_date;
    }

    public String getOrder_booking_time() {
        return order_booking_time;
    }

    public void setOrder_booking_time(String order_booking_time) {
        this.order_booking_time = order_booking_time;
    }

    public String getDelivery_name() {
        return MyApplication.isFrom.equalsIgnoreCase("ORDER")?delivery_name:name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getDelivery_suburb() {
        return delivery_suburb;
    }

    public void setDelivery_suburb(String delivery_suburb) {
        this.delivery_suburb = delivery_suburb;
    }

    public String getDelivery_city() {
        return delivery_city;
    }

    public void setDelivery_city(String delivery_city) {
        this.delivery_city = delivery_city;
    }

    public String getDelivery_postcode() {
        return delivery_postcode;
    }

    public void setDelivery_postcode(String delivery_postcode) {
        this.delivery_postcode = delivery_postcode;
    }

    public String getCustomers_telephone() {
        return MyApplication.isFrom.equalsIgnoreCase("ORDER")?customers_telephone:phone;
    }

    public void setCustomers_telephone(String customers_telephone) {
        this.customers_telephone = customers_telephone;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getProducts_count() {
        return products_count;
    }

    public void setProducts_count(String products_count) {
        this.products_count = products_count;
    }

    public ArrayList<OrderDetailsProductListModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<OrderDetailsProductListModel> products) {
        this.products = products;
    }

    public String getDelivery_street_address() {
        return MyApplication.isFrom.equalsIgnoreCase("ORDER")?delivery_street_address:address;
    }

    public void setDelivery_street_address(String delivery_street_address) {
        this.delivery_street_address = delivery_street_address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getPackage_delivery_date() {
        return package_delivery_date;
    }

    public void setPackage_delivery_date(String package_delivery_date) {
        this.package_delivery_date = package_delivery_date;
    }

    public String getPackage_number_of_deliveries() {
        return package_number_of_deliveries;
    }

    public void setPackage_number_of_deliveries(String package_number_of_deliveries) {
        this.package_number_of_deliveries = package_number_of_deliveries;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_status() {
        return package_status;
    }

    public void setPackage_status(String package_status) {
        this.package_status = package_status;
    }
}
