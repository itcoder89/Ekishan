package com.android.ekishan.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TimeSlotData {

    @SerializedName("time_slot")
    public String time_slot;
    @SerializedName("id")
    public String id;

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return time_slot;
    }
}
