package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class HomeProductList {
    @SerializedName("id")
    private String id;
    @SerializedName("products_image")
    private String products_image;
    @SerializedName("products_price")
    private String products_price;
    @SerializedName("products_name")
    private String products_name;
    @SerializedName("products_description")
    private String products_description;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("variety_id")
    private String variety_id;
    @SerializedName("is_favourite")
    private boolean is_favourite;
    @SerializedName("variety_name")
    private String variety_name;
    @SerializedName("weight")
    private String weight;
    @SerializedName("out_of_stock")
    private String out_of_stock;

    public boolean isIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducts_image() {
        return products_image;
    }

    public void setProducts_image(String products_image) {
        this.products_image = products_image;
    }

    public String getProducts_price() {
        return products_price;
    }

    public void setProducts_price(String products_price) {
        this.products_price = products_price;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getProducts_description() {
        return products_description;
    }

    public void setProducts_description(String products_description) {
        this.products_description = products_description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }

    public String getVariety_name() {
        return variety_name==null?"Desi":variety_name.equalsIgnoreCase("")?"Desi":variety_name;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }

    public String getWeight() {
        return weight==null?"":weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOut_of_stock() {
        return out_of_stock;
    }

    public void setOut_of_stock(String out_of_stock) {
        this.out_of_stock = out_of_stock;
    }
}
