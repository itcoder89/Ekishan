package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/23/2020.
 */
public class DropData {
    @SerializedName("drop_name")
    private String  drop_name;
    @SerializedName("drop_mobile")
    private String drop_mobile;
    @SerializedName("drop_company")
    private String  drop_company;
    @SerializedName("drop_street_address")
    private String drop_street_address;
    @SerializedName("drop_suburb")
    private String drop_suburb;
    @SerializedName("drop_city")
    private String drop_city;
    @SerializedName("drop_postcode")
    private String drop_postcode;
    @SerializedName("drop_state")
    private String drop_state;
    @SerializedName("drop_country")
    private String drop_country;
    @SerializedName("drop_lat")
    private String drop_lat;
    @SerializedName("drop_long")
    private String drop_long;
    @SerializedName("drop_googleLocation")
    private String drop_googleLocation;



    public String getDrop_mobile() {
        return drop_mobile;
    }

    public void setDrop_mobile(String drop_mobile) {
        this.drop_mobile = drop_mobile;
    }

    public String getDrop_name() {
        return drop_name;
    }

    public void setDrop_name(String drop_name) {
        this.drop_name = drop_name;
    }

    public String getDrop_company() {
        return drop_company;
    }

    public void setDrop_company(String drop_company) {
        this.drop_company = drop_company;
    }

    public String getDrop_street_address() {
        return drop_street_address;
    }

    public void setDrop_street_address(String drop_street_address) {
        this.drop_street_address = drop_street_address;
    }

    public String getDrop_suburb() {
        return drop_suburb;
    }

    public void setDrop_suburb(String drop_suburb) {
        this.drop_suburb = drop_suburb;
    }

    public String getDrop_city() {
        return drop_city;
    }

    public void setDrop_city(String drop_city) {
        this.drop_city = drop_city;
    }

    public String getDrop_postcode() {
        return drop_postcode;
    }

    public void setDrop_postcode(String drop_postcode) {
        this.drop_postcode = drop_postcode;
    }

    public String getDrop_state() {
        return drop_state;
    }

    public void setDrop_state(String drop_state) {
        this.drop_state = drop_state;
    }

    public String getDrop_country() {
        return drop_country;
    }

    public void setDrop_country(String drop_country) {
        this.drop_country = drop_country;
    }

    public String getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(String drop_lat) {
        this.drop_lat = drop_lat;
    }

    public String getDrop_long() {
        return drop_long;
    }

    public void setDrop_long(String drop_long) {
        this.drop_long = drop_long;
    }

    public String getDrop_googleLocation() {
        return drop_googleLocation;
    }

    public void setDrop_googleLocation(String drop_googleLocation) {
        this.drop_googleLocation = drop_googleLocation;
    }
}
