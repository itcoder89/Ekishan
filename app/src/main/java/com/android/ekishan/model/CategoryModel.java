package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("variety_id")
    String variety_id;
    @SerializedName("variety_name")
    String variety_name;
    @SerializedName("variety_desc")
    String variety_desc;
    @SerializedName("image")
    String image;
    @SerializedName("products_price")
    String products_price;
    @SerializedName("isChecked")
    boolean isChecked;

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }

    public String getVariety_name() {
        return variety_name;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }

    public String getVariety_desc() {
        return variety_desc;
    }

    public void setVariety_desc(String variety_desc) {
        this.variety_desc = variety_desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProducts_price() {
        return products_price;
    }

    public void setProducts_price(String products_price) {
        this.products_price = products_price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
