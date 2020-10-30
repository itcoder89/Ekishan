package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/23/2020.
 */
public class PickupData {

    @SerializedName("pickup_name")
    private String  pickup_name;
    @SerializedName("pickup_mobile")
    private String pickup_mobile;
    @SerializedName("pickup_company")
    private String  pickup_company;
    @SerializedName("pickup_street_address")
    private String pickup_street_address;
    @SerializedName("pickup_suburb")
    private String pickup_suburb;
    @SerializedName("pickup_city")
    private String pickup_city;
    @SerializedName("pickup_postcode")
    private String pickup_postcode;
    @SerializedName("pickup_state")
    private String pickup_state;
    @SerializedName("pickup_country")
    private String pickup_country;
    @SerializedName("pickup_lat")
    private String pickup_lat;
    @SerializedName("pickup_long")
    private String pickup_long;



    public String getPickup_mobile() {
        return pickup_mobile;
    }

    public void setPickup_mobile(String pickup_mobile) {
        this.pickup_mobile = pickup_mobile;
    }

    public String getPickup_name() {
        return pickup_name;
    }

    public void setPickup_name(String pickup_name) {
        this.pickup_name = pickup_name;
    }

    public String getPickup_company() {
        return pickup_company;
    }

    public void setPickup_company(String pickup_company) {
        this.pickup_company = pickup_company;
    }

    public String getPickup_street_address() {
        return pickup_street_address;
    }

    public void setPickup_street_address(String pickup_street_address) {
        this.pickup_street_address = pickup_street_address;
    }

    public String getPickup_suburb() {
        return pickup_suburb;
    }

    public void setPickup_suburb(String pickup_suburb) {
        this.pickup_suburb = pickup_suburb;
    }

    public String getPickup_city() {
        return pickup_city;
    }

    public void setPickup_city(String pickup_city) {
        this.pickup_city = pickup_city;
    }

    public String getPickup_postcode() {
        return pickup_postcode;
    }

    public void setPickup_postcode(String pickup_postcode) {
        this.pickup_postcode = pickup_postcode;
    }

    public String getPickup_state() {
        return pickup_state;
    }

    public void setPickup_state(String pickup_state) {
        this.pickup_state = pickup_state;
    }

    public String getPickup_country() {
        return pickup_country;
    }

    public void setPickup_country(String pickup_country) {
        this.pickup_country = pickup_country;
    }

    public String getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(String pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public String getPickup_long() {
        return pickup_long;
    }

    public void setPickup_long(String pickup_long) {
        this.pickup_long = pickup_long;
    }
}
