package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorAgroItemList {

    @SerializedName("agro_items_id")
    private String agro_items_id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;

    public String getAgro_items_id() {
        return agro_items_id;
    }

    public void setAgro_items_id(String agro_items_id) {
        this.agro_items_id = agro_items_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
