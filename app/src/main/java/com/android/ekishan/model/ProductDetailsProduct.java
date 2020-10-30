package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDetailsProduct {
    @SerializedName("product")
    ProductDetailsModel product;

    @SerializedName("categories")
    ArrayList<CategoryModel> categories;

    @SerializedName("produced_by")
    ArrayList<ProduceByModel> produced_by;

    public ProductDetailsModel getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsModel product) {
        this.product = product;
    }

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    public ArrayList<ProduceByModel> getProduced_by() {
        return produced_by;
    }

    public void setProduced_by(ArrayList<ProduceByModel> produced_by) {
        this.produced_by = produced_by;
    }
}
