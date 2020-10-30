package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class ProduceByModel {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("code")
    String code;
    @SerializedName("rating")
    String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
