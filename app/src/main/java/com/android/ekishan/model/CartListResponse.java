package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartListResponse {

    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private CartDataModel data;
    @SerializedName("products")
    private ArrayList<CartListModel> products;
    @SerializedName("message")
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public CartDataModel getData() {
        return data;
    }

    public void setData(CartDataModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CartListModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<CartListModel> products) {
        this.products = products;
    }
}
