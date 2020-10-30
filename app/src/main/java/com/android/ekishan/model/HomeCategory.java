package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeCategory {

    @SerializedName("id")
    private String id;
    @SerializedName("categories_image")
    private String categories_image;
    @SerializedName("categories_icon")
    private String categories_icon;
    @SerializedName("display_name")
    private String display_name;
    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategories_image() {
        return categories_image;
    }

    public void setCategories_image(String categories_image) {
        this.categories_image = categories_image;
    }

    public String getCategories_icon() {
        return categories_icon;
    }

    public void setCategories_icon(String categories_icon) {
        this.categories_icon = categories_icon;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
