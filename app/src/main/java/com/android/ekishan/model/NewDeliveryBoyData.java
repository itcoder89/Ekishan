package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewDeliveryBoyData {


    /**
     * success : 1
     * message : Request Showed Successfully!
     * data : [{"vendors_sales_id":"40","orders_date":"2020-10-17 14:32:05","payment_status":null,"order_status":"Delivered","name":"Naveen kumar kumar","lat":"0.00000000","long":"0.00000000","mobile":"9024464212","subscription_id":0,"package_delivery_id":0,"orders_id":0,"order_type":"sale","agro_item_id":0},{"vendors_sales_id":43,"orders_date":"2020-10-17 13:45:20","payment_status":null,"order_status":"Delivered","name":"Naveen kumar kumar","lat":"0.00000000","long":"0.00000000","mobile":"9024464212","subscription_id":0,"package_delivery_id":0,"orders_id":0,"order_type":"demand","agro_item_id":0}]
     */

    private int success;
    private String message;
    private List<DataBean> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * vendors_sales_id : 40
         * orders_date : 2020-10-17 14:32:05
         * payment_status : null
         * order_status : Delivered
         * name : Naveen kumar kumar
         * lat : 0.00000000
         * long : 0.00000000
         * mobile : 9024464212
         * subscription_id : 0
         * package_delivery_id : 0
         * orders_id : 0
         * order_type : sale
         * agro_item_id : 0
         */

        private String vendors_sales_id;
        private String orders_date;
        private String payment_status;
        private String order_status;
        private String name;
        private String lat;
        @SerializedName("long")
        private String longX;
        private String mobile;
        private String subscription_id;
        private String package_delivery_id;
        private String orders_id;
        private String order_type;
        private String agro_item_id;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        private String distance;

        public String getVendors_sales_id() {
            return vendors_sales_id;
        }

        public void setVendors_sales_id(String vendors_sales_id) {
            this.vendors_sales_id = vendors_sales_id;
        }

        public String getOrders_date() {
            return orders_date;
        }

        public void setOrders_date(String orders_date) {
            this.orders_date = orders_date;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLongX() {
            return longX;
        }

        public void setLongX(String longX) {
            this.longX = longX;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(String subscription_id) {
            this.subscription_id = subscription_id;
        }

        public String getPackage_delivery_id() {
            return package_delivery_id;
        }

        public void setPackage_delivery_id(String package_delivery_id) {
            this.package_delivery_id = package_delivery_id;
        }

        public String getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(String orders_id) {
            this.orders_id = orders_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getAgro_item_id() {
            return agro_item_id;
        }

        public void setAgro_item_id(String agro_item_id) {
            this.agro_item_id = agro_item_id;
        }
    }
}
