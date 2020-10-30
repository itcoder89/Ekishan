package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseHomeMain {
    @SerializedName("banners_top")
    private ArrayList<HomeBanners> banners;

    @SerializedName("banners_bottom")
    private ArrayList<HomeBanners> banners_bottom;

    @SerializedName("categories")
    private ArrayList<HomeCategory> categories;
    @SerializedName("products")
    private ArrayList<HomeProductList> products;

    @SerializedName("cart_quantity")
    String cart_quantity;

    public ArrayList<HomeProductList> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HomeProductList> products) {
        this.products = products;
    }

    public ArrayList<HomeBanners> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<HomeBanners> banners) {
        this.banners = banners;
    }

    public ArrayList<HomeCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<HomeCategory> categories) {
        this.categories = categories;
    }

    public String getCart_quantity() {
        return cart_quantity==null?"0":cart_quantity.equalsIgnoreCase("")?"0":cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public ArrayList<HomeBanners> getBanners_bottom() {
        return banners_bottom;
    }

    public void setBanners_bottom(ArrayList<HomeBanners> banners_bottom) {
        this.banners_bottom = banners_bottom;
    }
}
