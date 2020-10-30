package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorCollectionList {

    @SerializedName("vendors_demands_id")
    private String vendors_demands_id;
            @SerializedName("custom_product_id")
    private String custom_product_id;
            @SerializedName("quantity")
    private String quantity;
            @SerializedName("price")
    private String price;
            @SerializedName("accept_status")
    private String accept_status;
            @SerializedName("acceptor_vendor_id")
    private String acceptor_vendor_id;
            @SerializedName("quantity_unit")
    private String quantity_unit;
            @SerializedName("p2")
    private String p2;
            @SerializedName("products_name")
    private String products_name;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @SerializedName("created_at")
    private String created_at;

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getVendors_demands_id() {
        return vendors_demands_id;
    }

    public void setVendors_demands_id(String vendors_demands_id) {
        this.vendors_demands_id = vendors_demands_id;
    }

    public String getCustom_product_id() {
        return custom_product_id;
    }

    public void setCustom_product_id(String custom_product_id) {
        this.custom_product_id = custom_product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAccept_status() {
        return accept_status;
    }

    public void setAccept_status(String accept_status) {
        this.accept_status = accept_status;
    }

    public String getAcceptor_vendor_id() {
        return acceptor_vendor_id;
    }

    public void setAcceptor_vendor_id(String acceptor_vendor_id) {
        this.acceptor_vendor_id = acceptor_vendor_id;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }
}
