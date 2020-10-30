package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 5/21/2020.
 */
public class ReviewListModel {


    @SerializedName("reviews_id")
    private String reviews_id;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("products_id")
    private String products_id;
    @SerializedName("customers_id")
    private String customers_id;
    @SerializedName("reviews_rating")
    private String reviews_rating;
    @SerializedName("title")
    private String title;
    @SerializedName("review")
    private String review;
    @SerializedName("image")
    private String image;
    @SerializedName("reviews_status")
    private String reviews_status;
    @SerializedName("reviews_read")
    private String reviews_read;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public String getReviews_id() {
        return reviews_id;
    }

    public void setReviews_id(String reviews_id) {
        this.reviews_id = reviews_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getReviews_rating() {
        return reviews_rating;
    }

    public void setReviews_rating(String reviews_rating) {
        this.reviews_rating = reviews_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReviews_status() {
        return reviews_status;
    }

    public void setReviews_status(String reviews_status) {
        this.reviews_status = reviews_status;
    }

    public String getReviews_read() {
        return reviews_read;
    }

    public void setReviews_read(String reviews_read) {
        this.reviews_read = reviews_read;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
