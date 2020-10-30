package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetVegData {

    @SerializedName("vegetable")
    ArrayList<VegListModel> vegetable;
    @SerializedName("fruit")
    ArrayList<VegListModel> fruit;
    @SerializedName("product")
    ArrayList<VegListModel> product;
    @SerializedName("units")
    ArrayList<UnitsModel> units;
    @SerializedName("vendors_id")
    String vendors_id;
    @SerializedName("bank_name")
    String bank_name;
    @SerializedName("vendors_bank_branch_name")
    String vendors_bank_branch_name;
    @SerializedName("vendors_bank_acc_number")
    String vendors_bank_acc_number;
    @SerializedName("vendors_acc_holder_name")
    String vendors_acc_holder_name;
    @SerializedName("vendors_bank_ifsc")
    String vendors_bank_ifsc;
    @SerializedName("image")
    String image;
    @SerializedName("postal_code")
    String postal_code;
    @SerializedName("address")
    String address;

    public ArrayList<VegListModel> getVegetable() {
        return vegetable;
    }

    public void setVegetable(ArrayList<VegListModel> vegetable) {
        this.vegetable = vegetable;
    }

    public ArrayList<VegListModel> getFruit() {
        return fruit;
    }

    public void setFruit(ArrayList<VegListModel> fruit) {
        this.fruit = fruit;
    }

    public ArrayList<VegListModel> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<VegListModel> product) {
        this.product = product;
    }

    public ArrayList<UnitsModel> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<UnitsModel> units) {
        this.units = units;
    }

    public String getVendors_id() {
        return vendors_id;
    }

    public void setVendors_id(String vendors_id) {
        this.vendors_id = vendors_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getVendors_bank_branch_name() {
        return vendors_bank_branch_name;
    }

    public void setVendors_bank_branch_name(String vendors_bank_branch_name) {
        this.vendors_bank_branch_name = vendors_bank_branch_name;
    }

    public String getVendors_bank_acc_number() {
        return vendors_bank_acc_number;
    }

    public void setVendors_bank_acc_number(String vendors_bank_acc_number) {
        this.vendors_bank_acc_number = vendors_bank_acc_number;
    }

    public String getVendors_acc_holder_name() {
        return vendors_acc_holder_name;
    }

    public void setVendors_acc_holder_name(String vendors_acc_holder_name) {
        this.vendors_acc_holder_name = vendors_acc_holder_name;
    }

    public String getVendors_bank_ifsc() {
        return vendors_bank_ifsc;
    }

    public void setVendors_bank_ifsc(String vendors_bank_ifsc) {
        this.vendors_bank_ifsc = vendors_bank_ifsc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
