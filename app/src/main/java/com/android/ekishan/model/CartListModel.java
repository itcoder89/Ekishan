package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

public class CartListModel {

    @SerializedName("customers_basket_id")
    private String customers_basket_id;
    @SerializedName("customers_id")
    private String customers_id;
    @SerializedName("products_id")
    private String products_id;
    @SerializedName("basket_id")
    private String basket_id;
    @SerializedName("customers_basket_quantity")
    private String customers_basket_quantity;
    @SerializedName("final_price")
    private String final_price;
    @SerializedName("customers_basket_date_added")
    private String customers_basket_date_added;
    @SerializedName("is_order")
    private String is_order;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("model")
    private String model;
    @SerializedName("products_name")
    private String products_name;
    @SerializedName("image")
    private String image;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("weight")
    private String weight;
    @SerializedName("product_type")
    private String product_type;
    @SerializedName("variety_id")
    private String variety_id;
    @SerializedName("special_price")
    private String special_price;


    public String getCustomers_basket_id() {
        return customers_basket_id;
    }

    public void setCustomers_basket_id(String customers_basket_id) {
        this.customers_basket_id = customers_basket_id;
    }

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getCustomers_basket_quantity() {
        return customers_basket_quantity;
    }

    public void setCustomers_basket_quantity(String customers_basket_quantity) {
        this.customers_basket_quantity = customers_basket_quantity;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getCustomers_basket_date_added() {
        return customers_basket_date_added;
    }

    public void setCustomers_basket_date_added(String customers_basket_date_added) {
        this.customers_basket_date_added = customers_basket_date_added;
    }

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }

    public String getBasket_id() {
        return basket_id;
    }

    public void setBasket_id(String basket_id) {
        this.basket_id = basket_id;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }
}
