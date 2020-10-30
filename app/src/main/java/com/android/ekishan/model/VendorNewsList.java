package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/8/2020.
 */
public class VendorNewsList {

    @SerializedName("news_id")
    private String news_id;
    @SerializedName("news_image")
    private String news_image;
    @SerializedName("news_date_added")
    private String news_date_added;
    @SerializedName("news_status")
    private String news_status;
    @SerializedName("is_feature")
    private String is_feature;
    @SerializedName("language_id")
    private String language_id;
    @SerializedName("news_name")
    private String news_name;
    @SerializedName("news_description")
    private String news_description;
    @SerializedName("news_url")
    private String news_url;
    @SerializedName("news_viewed")
    private String news_viewed;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_date_added() {
        return news_date_added;
    }

    public void setNews_date_added(String news_date_added) {
        this.news_date_added = news_date_added;
    }

    public String getNews_status() {
        return news_status;
    }

    public void setNews_status(String news_status) {
        this.news_status = news_status;
    }

    public String getIs_feature() {
        return is_feature;
    }

    public void setIs_feature(String is_feature) {
        this.is_feature = is_feature;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_viewed() {
        return news_viewed;
    }

    public void setNews_viewed(String news_viewed) {
        this.news_viewed = news_viewed;
    }
}
