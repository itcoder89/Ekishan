package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class CartDataModel {

    @SerializedName("price")
    private String price;
    @SerializedName("special_price")
    private String special_price;
    @SerializedName("total_item")
    private String total_item;
    @SerializedName("save_price")
    private String save_price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }

    public String getSave_price() {
        return save_price;
    }

    public void setSave_price(String save_price) {
        this.save_price = save_price;
    }
}
