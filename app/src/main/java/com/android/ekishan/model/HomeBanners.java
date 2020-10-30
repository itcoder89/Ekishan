package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class HomeBanners {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("deal_product_id")
    private String deal_product_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeal_product_id() {
        return deal_product_id==null?"0":deal_product_id.equalsIgnoreCase("")?"0":deal_product_id;
    }

    public void setDeal_product_id(String deal_product_id) {
        this.deal_product_id = deal_product_id;
    }
}
