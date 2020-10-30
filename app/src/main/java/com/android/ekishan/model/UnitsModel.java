package com.android.ekishan.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UnitsModel {

    @SerializedName("unit")
    String unit;
    @SerializedName("products_options_values_id")
    String products_options_values_id;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProducts_options_values_id() {
        return products_options_values_id;
    }

    public void setProducts_options_values_id(String products_options_values_id) {
        this.products_options_values_id = products_options_values_id;
    }

    @NonNull
    @Override
    public String toString() {
        return unit;
    }
}
