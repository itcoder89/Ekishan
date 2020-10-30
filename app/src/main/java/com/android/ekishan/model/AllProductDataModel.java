package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class AllProductDataModel {

    @SerializedName("products_type")
    String products_type;
    @SerializedName("products_id")
    String products_id;
    @SerializedName("products_name")
    String products_name;
    @SerializedName("products_cult_period")
    String products_cult_period;
    @SerializedName("products_farm_size")
    String products_farm_size;
    @SerializedName("products_image")
    String products_image;
    @SerializedName("products_remarks")
    String products_remarks;
    @SerializedName("unit_id")
    String unit_id;
    @SerializedName("variety_id")
    String variety_id;

    public String getProducts_type() {
        return products_type;
    }

    public void setProducts_type(String products_type) {
        this.products_type = products_type;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getProducts_cult_period() {
        return products_cult_period;
    }

    public void setProducts_cult_period(String products_cult_period) {
        this.products_cult_period = products_cult_period;
    }

    public String getProducts_farm_size() {
        return products_farm_size;
    }

    public void setProducts_farm_size(String products_farm_size) {
        this.products_farm_size = products_farm_size;
    }

    public String getProducts_image() {
        return products_image;
    }

    public void setProducts_image(String products_image) {
        this.products_image = products_image;
    }

    public String getProducts_remarks() {
        return products_remarks;
    }

    public void setProducts_remarks(String products_remarks) {
        this.products_remarks = products_remarks;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }
}
