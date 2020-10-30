package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 5/21/2020.
 */
public class ReviewedList {
    @SerializedName("orders_products_id")
    private String orders_products_id;
    @SerializedName("orders_id")
    private String orders_id;
    @SerializedName("products_id")
    private String products_id;
    @SerializedName("products_model")
    private String products_model;
    @SerializedName("products_name")
    private String products_name;
    @SerializedName("products_image")
    private String products_image;
    @SerializedName("products_description")
    private String products_description;
    @SerializedName("product_categories")
    private String product_categories;
    @SerializedName("products_price")
    private String products_price;
    @SerializedName("final_price")
    private String final_price;
    @SerializedName("products_tax")
    private String products_tax;
    @SerializedName("products_quantity")
    private String products_quantity;
    @SerializedName("review_id")
    private String review_id;
    @SerializedName("update")
    private String update;
    @SerializedName("review")
    private ReviewListModel review;

    public ReviewListModel getReview() {
        return review;
    }

    public void setReview(ReviewListModel review) {
        this.review = review;
    }

    public String getOrders_products_id() {
        return orders_products_id;
    }

    public void setOrders_products_id(String orders_products_id) {
        this.orders_products_id = orders_products_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getProducts_model() {
        return products_model;
    }

    public void setProducts_model(String products_model) {
        this.products_model = products_model;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
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

    public String getProduct_categories() {
        return product_categories;
    }

    public void setProduct_categories(String product_categories) {
        this.product_categories = product_categories;
    }

    public String getProducts_price() {
        return products_price;
    }

    public void setProducts_price(String products_price) {
        this.products_price = products_price;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getProducts_tax() {
        return products_tax;
    }

    public void setProducts_tax(String products_tax) {
        this.products_tax = products_tax;
    }

    public String getProducts_quantity() {
        return products_quantity;
    }

    public void setProducts_quantity(String products_quantity) {
        this.products_quantity = products_quantity;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
