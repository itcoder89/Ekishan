package com.android.ekishan.model;

import java.util.List;

public class VendorTransactionDetailData {


    /**
     * success : 1
     * message : Transactions Details Get successfully!
     * data : [{"vendors_sales_product_id":44,"sales_request_id":40,"vendors_id":0,"custom_product_id":118,"quantity":100,"unit_id":158,"price":"100.00","order_status":"","remarks":"","image":null,"status":"","created_at":"1970-01-01 05:30:00","updated_at":"1970-01-01 06:03:40","products_id":118,"language_id":1,"products_name":"Bean (फलियां)","products_description":"<p>Beans are &nbsp;rich source of folic acid and fiber. It also contains vitamin A, C, K and minerals.<\/p>\r\n\r\n<p>It helps to improve bone health, reduce the depression, improves the fetus health in pregnancy.<\/p>\r\n\r\n<p>It also contains chlorophyll which reduces the risk of cancer.<\/p>","how_to_grow":"<p>बीन्स की खेती के लिए उचित जल निकासी वाली उपजाऊ भूमि की जरूरत होती है. लेकिन अधिक पैदावार लेने के लिए इसे काली चिकनी मिट्टी में उगाना चाहिए. इसकी खेती के लिए भूमि का पी.एच. मान 5.5 से 6.5 के बीच होना चाहिए.इसकी खेती के लिए 15 से 25 डिग्री तक तापमान उपयुक्त होता है. इसके बीजों को अंकुरित होने के लिए शुरुआत में 20 डिग्री के आसपास तापमान की जरूरत होती है. बीन्स की खेती के लिए बीज की रोपाई समतल में की जाती है. समतल में इसके बीजों को पंक्तियों में ड्रिल के माध्यम से उगाया जाता है. बीज की रोपाई के दौरान प्रत्येक पंक्तियों के बीच की दूरी एक से डेढ़ फिट रखनी चाहिए. इसके पौधों को पानी की सामान्य जरूरत होती है. बीन्स की खेती के लिए शुरुआत में खेत की जुताई के वक्त 10 से 12 गाड़ी सड़ी हुई पुरानी गोबर की खाद को खेत में डालकर मिला दें. बीन्स की फलियों को पकने से पहले तोड़ लिया जाता है. इसके पौधे बीज रोपाई के लगभग 50 से 60 दिन बाद पैदावार देना शुरू कर देते हैं.<\/p>","products_url":null,"products_viewed":0,"id":10,"orders_id":64,"customer_order_id":0,"vendors_demands_id":null,"agro_items_request_id":null,"vendors_sales_id":"40","otp":0,"payment_by":"Paytm","assign_status":5,"total_tax":"0","customers_id":0,"delivery_boy_id":10,"pickup_name":"","pickup_mobile":"","pickup_company":null,"pickup_street_address":"","pickup_suburb":null,"pickup_city":"","pickup_postcode":"","pickup_state":null,"pickup_country":"","pickup_lat":"0.00000000","pickup_long":"0.00000000","googleLocation":"","drop_name":"Naveen kumar kumar","drop_mobile":"9024464212","drop_company":null,"drop_street_address":"","drop_suburb":null,"drop_city":"","drop_postcode":"","drop_state":null,"drop_country":"","drop_lat":"0.00000000","drop_long":"0.00000000","drop_googleLocation":"","payment_method":null,"last_modified":null,"date_purchased":null,"orders_date_finished":null,"currency":null,"order_price":null,"cash_amount":"2500.00","wallet_amount":"10000.00","cashback_used":0,"kms":null,"category":null,"weight":null,"shipping_cost":null,"product_price":null,"order_information":"","is_seen":0,"order_booking_time":null,"order_booking_date":"2020-10-17 14:32:05","type":"collection staff","firstname":"Suresh","lastname":"choudhary","email_address":"suresh@gmail.com","telephone":"7021878200","password":"123123","wallet":0,"isActive":1,"picture":"","rc":"","driving_licence":"","adhaar_card":"","bike_no":"","lat":null,"lng":null},{"vendors_sales_product_id":45,"sales_request_id":40,"vendors_id":0,"custom_product_id":143,"quantity":50,"unit_id":158,"price":"50.00","order_status":"","remarks":"","image":null,"status":"","created_at":"1970-01-01 05:30:00","updated_at":"1970-01-01 06:03:40","products_id":143,"language_id":1,"products_name":"Banana (केला)","products_description":"<p>Bananas contain fiber, vitamin C, potassium,&nbsp;<a href=\\\"https://www.medicalnewstoday.com/articles/287677.php\\\">foliate<\/a>, and antioxidants tryptophan<\/p>\r\n\r\n<p>It improves digestive health, preserve memory, regulate mood.<\/p>\r\n\r\n<p>It is also good for skin.<\/p>","how_to_grow":"","products_url":null,"products_viewed":0,"id":10,"orders_id":64,"customer_order_id":0,"vendors_demands_id":null,"agro_items_request_id":null,"vendors_sales_id":"40","otp":0,"payment_by":"Paytm","assign_status":5,"total_tax":"0","customers_id":0,"delivery_boy_id":10,"pickup_name":"","pickup_mobile":"","pickup_company":null,"pickup_street_address":"","pickup_suburb":null,"pickup_city":"","pickup_postcode":"","pickup_state":null,"pickup_country":"","pickup_lat":"0.00000000","pickup_long":"0.00000000","googleLocation":"","drop_name":"Naveen kumar kumar","drop_mobile":"9024464212","drop_company":null,"drop_street_address":"","drop_suburb":null,"drop_city":"","drop_postcode":"","drop_state":null,"drop_country":"","drop_lat":"0.00000000","drop_long":"0.00000000","drop_googleLocation":"","payment_method":null,"last_modified":null,"date_purchased":null,"orders_date_finished":null,"currency":null,"order_price":null,"cash_amount":"2500.00","wallet_amount":"10000.00","cashback_used":0,"kms":null,"category":null,"weight":null,"shipping_cost":null,"product_price":null,"order_information":"","is_seen":0,"order_booking_time":null,"order_booking_date":"2020-10-17 14:32:05","type":"collection staff","firstname":"Suresh","lastname":"choudhary","email_address":"suresh@gmail.com","telephone":"7021878200","password":"123123","wallet":0,"isActive":1,"picture":"","rc":"","driving_licence":"","adhaar_card":"","bike_no":"","lat":null,"lng":null}]
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
        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProducts_name() {
            return products_name;
        }

        public void setProducts_name(String products_name) {
            this.products_name = products_name;
        }

        public String getProducts_description() {
            return products_description;
        }

        public void setProducts_description(String products_description) {
            this.products_description = products_description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(int orders_id) {
            this.orders_id = orders_id;
        }

        public String getDrop_name() {
            return drop_name;
        }

        public void setDrop_name(String drop_name) {
            this.drop_name = drop_name;
        }

        public float getCash_amount() {
            return cash_amount;
        }

        public void setCash_amount(float cash_amount) {
            this.cash_amount = cash_amount;
        }

        public float getWallet_amount() {
            return wallet_amount;
        }

        public void setWallet_amount(float wallet_amount) {
            this.wallet_amount = wallet_amount;
        }

        public String getOrder_booking_date() {
            return order_booking_date;
        }

        public void setOrder_booking_date(String order_booking_date) {
            this.order_booking_date = order_booking_date;
        }

        /**
         * vendors_sales_product_id : 44
         * sales_request_id : 40
         * vendors_id : 0
         * custom_product_id : 118
         * quantity : 100
         * unit_id : 158
         * price : 100.00
         * order_status :
         * remarks :
         * image : null
         * status :
         * created_at : 1970-01-01 05:30:00
         * updated_at : 1970-01-01 06:03:40
         * products_id : 118
         * language_id : 1
         * products_name : Bean (फलियां)
         * products_description : <p>Beans are &nbsp;rich source of folic acid and fiber. It also contains vitamin A, C, K and minerals.</p>
         *
         * <p>It helps to improve bone health, reduce the depression, improves the fetus health in pregnancy.</p>
         *
         * <p>It also contains chlorophyll which reduces the risk of cancer.</p>
         * how_to_grow : <p>बीन्स की खेती के लिए उचित जल निकासी वाली उपजाऊ भूमि की जरूरत होती है. लेकिन अधिक पैदावार लेने के लिए इसे काली चिकनी मिट्टी में उगाना चाहिए. इसकी खेती के लिए भूमि का पी.एच. मान 5.5 से 6.5 के बीच होना चाहिए.इसकी खेती के लिए 15 से 25 डिग्री तक तापमान उपयुक्त होता है. इसके बीजों को अंकुरित होने के लिए शुरुआत में 20 डिग्री के आसपास तापमान की जरूरत होती है. बीन्स की खेती के लिए बीज की रोपाई समतल में की जाती है. समतल में इसके बीजों को पंक्तियों में ड्रिल के माध्यम से उगाया जाता है. बीज की रोपाई के दौरान प्रत्येक पंक्तियों के बीच की दूरी एक से डेढ़ फिट रखनी चाहिए. इसके पौधों को पानी की सामान्य जरूरत होती है. बीन्स की खेती के लिए शुरुआत में खेत की जुताई के वक्त 10 से 12 गाड़ी सड़ी हुई पुरानी गोबर की खाद को खेत में डालकर मिला दें. बीन्स की फलियों को पकने से पहले तोड़ लिया जाता है. इसके पौधे बीज रोपाई के लगभग 50 से 60 दिन बाद पैदावार देना शुरू कर देते हैं.</p>
         * products_url : null
         * products_viewed : 0
         * id : 10
         * orders_id : 64
         * customer_order_id : 0
         * vendors_demands_id : null
         * agro_items_request_id : null
         * vendors_sales_id : 40
         * otp : 0
         * payment_by : Paytm
         * assign_status : 5
         * total_tax : 0
         * customers_id : 0
         * delivery_boy_id : 10
         * pickup_name :
         * pickup_mobile :
         * pickup_company : null
         * pickup_street_address :
         * pickup_suburb : null
         * pickup_city :
         * pickup_postcode :
         * pickup_state : null
         * pickup_country :
         * pickup_lat : 0.00000000
         * pickup_long : 0.00000000
         * googleLocation :
         * drop_name : Naveen kumar kumar
         * drop_mobile : 9024464212
         * drop_company : null
         * drop_street_address :
         * drop_suburb : null
         * drop_city :
         * drop_postcode :
         * drop_state : null
         * drop_country :
         * drop_lat : 0.00000000
         * drop_long : 0.00000000
         * drop_googleLocation :
         * payment_method : null
         * last_modified : null
         * date_purchased : null
         * orders_date_finished : null
         * currency : null
         * order_price : null
         * cash_amount : 2500.00
         * wallet_amount : 10000.00
         * cashback_used : 0
         * kms : null
         * category : null
         * weight : null
         * shipping_cost : null
         * product_price : null
         * order_information :
         * is_seen : 0
         * order_booking_time : null
         * order_booking_date : 2020-10-17 14:32:05
         * type : collection staff
         * firstname : Suresh
         * lastname : choudhary
         * email_address : suresh@gmail.com
         * telephone : 7021878200
         * password : 123123
         * wallet : 0
         * isActive : 1
         * picture :
         * rc :
         * driving_licence :
         * adhaar_card :
         * bike_no :
         * lat : null
         * lng : null
         */

        private int quantity;
        private String price;
        private String products_name;
        private String products_description;
        private int id;
        private int orders_id;

        public int getSales_request_id() {
            return sales_request_id;
        }

        public void setSales_request_id(int sales_request_id) {
            this.sales_request_id = sales_request_id;
        }

        private int sales_request_id;
        private String drop_name;
        private float cash_amount;
        private float wallet_amount;
        private String order_booking_date;


    }
}
