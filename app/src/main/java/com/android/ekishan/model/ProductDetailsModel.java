package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsModel {

    @SerializedName("products_id")
    String products_id;

    @SerializedName("products_image")
    String products_image;

    @SerializedName("products_description")
    String products_description;

    @SerializedName("how_to_grow")
    String how_to_grow;

    @SerializedName("products_name")
    String products_name;

    @SerializedName("products_url")
    String products_url;

    @SerializedName("products_viewed")
    String products_viewed;

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getProducts_image() {
        return products_image;
    }

    public void setProducts_image(String products_image) {
        this.products_image = products_image;
    }

    public String getProducts_description() {
        return products_description;
    }

    public void setProducts_description(String products_description) {
        this.products_description = products_description;
    }

    public String getHow_to_grow() {
        return how_to_grow;
    }

    public void setHow_to_grow(String how_to_grow) {
        this.how_to_grow = how_to_grow;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getProducts_url() {
        return products_url;
    }

    public void setProducts_url(String products_url) {
        this.products_url = products_url;
    }

    public String getProducts_viewed() {
        return products_viewed;
    }

    public void setProducts_viewed(String products_viewed) {
        this.products_viewed = products_viewed;
    }
}
