package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${} on 6/23/2020.
 */
public class DeliveryOrderData {
    @SerializedName("pickup")
    private PickupData  pickup;
    @SerializedName("drop")
    private DropData drop;

    public PickupData getPickup() {
        return pickup;
    }

    public void setPickup(PickupData pickup) {
        this.pickup = pickup;
    }

    public DropData getDrop() {
        return drop;
    }

    public void setDrop(DropData drop) {
        this.drop = drop;
    }
}
