package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 5/25/2020.
 */
public class SmallBasketList {

    @SerializedName("products_id")
    private String products_id;
    @SerializedName("products_price")
    private String products_price;
    @SerializedName("description")
    private String description;

    @SerializedName("basket_product")
    private ArrayList<SmallBasketProductList> basket_product;

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getProducts_price() {
        return products_price;
    }

    public void setProducts_price(String products_price) {
        this.products_price = products_price;
    }

    public ArrayList<SmallBasketProductList> getBasket_product() {
        return basket_product;
    }

    public void setBasket_product(ArrayList<SmallBasketProductList> basket_product) {
        this.basket_product = basket_product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
