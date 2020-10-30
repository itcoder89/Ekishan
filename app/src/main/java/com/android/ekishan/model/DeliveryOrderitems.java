package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryOrderitems {

    @SerializedName("products_name")
    String products_name;
    @SerializedName("products_quantity")
    String products_quantity;
    @SerializedName("price")
    String price;

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getProducts_quantity() {
        return products_quantity;
    }

    public void setProducts_quantity(String products_quantity) {
        this.products_quantity = products_quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
