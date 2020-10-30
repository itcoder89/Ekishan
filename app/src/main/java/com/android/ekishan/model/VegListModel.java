package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class VegListModel {

    @SerializedName("id")
    String id;
    @SerializedName("products_image")
    String products_image;
    @SerializedName("products_price")
    String products_price;
    @SerializedName("products_name")
    String products_name;
    @SerializedName("products_description")
    String products_description;
    @SerializedName("variety_id")
    String variety_id;
    @SerializedName("farm_size")
    String farm_size;
    @SerializedName("cult_period")
    String cult_period;
    @SerializedName("remarks")
    String remarks;
    @SerializedName("image")
    String image;

    @SerializedName("unit_id")
    String unit_id;

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

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }

    public String getFarm_size() {
        return farm_size;
    }

    public void setFarm_size(String farm_size) {
        this.farm_size = farm_size;
    }

    public String getCult_period() {
        return cult_period;
    }

    public void setCult_period(String cult_period) {
        this.cult_period = cult_period;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }
}
