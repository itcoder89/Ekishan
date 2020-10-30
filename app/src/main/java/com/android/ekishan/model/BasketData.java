package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 5/25/2020.
 */
public class BasketData {
    @SerializedName("banners")
    private ArrayList<BasketBannerList> banners;
    @SerializedName("smallbasket")
    private ArrayList<SmallBasketList> smallbasket;
    @SerializedName("mediumbasket")
    private ArrayList<SmallBasketList> mediumbasket;
    @SerializedName("largebasket")
    private ArrayList<SmallBasketList> largebasket;

    public ArrayList<BasketBannerList> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BasketBannerList> banners) {
        this.banners = banners;
    }

    public ArrayList<SmallBasketList> getSmallbasket() {
        return smallbasket;
    }

    public void setSmallbasket(ArrayList<SmallBasketList> smallbasket) {
        this.smallbasket = smallbasket;
    }

    public ArrayList<SmallBasketList> getMediumbasket() {
        return mediumbasket;
    }

    public void setMediumbasket(ArrayList<SmallBasketList> mediumbasket) {
        this.mediumbasket = mediumbasket;
    }

    public ArrayList<SmallBasketList> getLargebasket() {
        return largebasket;
    }

    public void setLargebasket(ArrayList<SmallBasketList> largebasket) {
        this.largebasket = largebasket;
    }
}
