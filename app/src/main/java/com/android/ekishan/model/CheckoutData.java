package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CheckoutData {

    @SerializedName("address")
    public AddressData address;

    @SerializedName("delivery_charge")
    public String delivery_charge;

    @SerializedName("delivery_available")
    public String delivery_available;

    @SerializedName("timeslot")
    public ArrayList<TimeSlotData> timeslot;

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }

    public ArrayList<TimeSlotData> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(ArrayList<TimeSlotData> timeslot) {
        this.timeslot = timeslot;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getDelivery_available() {
        return delivery_available;
    }

    public void setDelivery_available(String delivery_available) {
        this.delivery_available = delivery_available;
    }
}
