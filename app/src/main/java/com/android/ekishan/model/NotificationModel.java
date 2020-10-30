package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationModel {

    @SerializedName("alerts")
    private ArrayList<AlertNotificationModel> alerts;

    @SerializedName("offers")
    private ArrayList<OffersNotificationModel> offers;

    public ArrayList<AlertNotificationModel> getAlert() {
        return alerts;
    }

    public void setAlert(ArrayList<AlertNotificationModel> alert) {
        this.alerts = alert;
    }

    public ArrayList<OffersNotificationModel> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<OffersNotificationModel> offers) {
        this.offers = offers;
    }
}
