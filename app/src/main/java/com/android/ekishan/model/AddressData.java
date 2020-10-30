package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class AddressData {

    @SerializedName("address_book_id")
    public String address_book_id;
    @SerializedName("googleLocation")
    public String googleLocation;

    public String getAddress_book_id() {
        return address_book_id;
    }

    public void setAddress_book_id(String address_book_id) {
        this.address_book_id = address_book_id;
    }

    public String getGoogleLocation() {
        return googleLocation;
    }

    public void setGoogleLocation(String googleLocation) {
        this.googleLocation = googleLocation;
    }
}
