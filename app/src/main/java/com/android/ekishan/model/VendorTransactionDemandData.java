package com.android.ekishan.model;

public class VendorTransactionDemandData {


    /**
     * success : 1
     * message : Transactions Details Get successfully!
     * data : {"vendors_demands_id":31,"custom_product_id":0,"vendors_id":0,"vendor_accept_status":"accepted","vendor_decline_comment":"","name":"Bean (फलियां)","quantity":"1001","available_quantity":"1000","price":1000,"status":"active","accept_status":"accepted","order_status":"1","acceptor_vendor_id":5,"quantity_unit":0,"p2":"","created_at":"1970-01-01 05:30:00","updated_at":"1970-01-01 06:03:40","orders_id":53,"customer_order_id":0,"agro_items_request_id":null,"vendors_sales_id":"","otp":3461,"payment_by":"Paytm","assign_status":5,"total_tax":"0","customers_id":0,"delivery_boy_id":2,"pickup_name":"","pickup_mobile":"","pickup_company":null,"pickup_street_address":"","pickup_suburb":null,"pickup_city":"","pickup_postcode":"","pickup_state":null,"pickup_country":"","pickup_lat":"0.00000000","pickup_long":"0.00000000","googleLocation":"","drop_name":"naveen","drop_mobile":"9024464212","drop_company":null,"drop_street_address":"","drop_suburb":null,"drop_city":"","drop_postcode":"","drop_state":null,"drop_country":"","drop_lat":"0.00000000","drop_long":"0.00000000","drop_googleLocation":"","payment_method":null,"last_modified":null,"date_purchased":null,"orders_date_finished":null,"currency":null,"order_price":null,"cash_amount":"1000.00","wallet_amount":"0.00","cashback_used":0,"kms":null,"category":null,"weight":null,"shipping_cost":null,"product_price":null,"order_information":"","image":null,"is_seen":0,"order_booking_time":null,"order_booking_date":"2020-10-06 05:15:20","id":2,"type":"collection staff","firstname":"naveen","lastname":"kumar","email_address":"naveendrivedigita4l@gmail.com","telephone":"9024464212","password":"2354567","wallet":0,"isActive":1,"picture":"","rc":"","driving_licence":"","adhaar_card":"","bike_no":"","lat":null,"lng":null}
     */

    private int success;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * vendors_demands_id : 31
         * custom_product_id : 0
         * vendors_id : 0
         * vendor_accept_status : accepted
         * vendor_decline_comment :
         * name : Bean (फलियां)
         * quantity : 1001
         * available_quantity : 1000
         * price : 1000
         * status : active
         * accept_status : accepted
         * order_status : 1
         * acceptor_vendor_id : 5
         * quantity_unit : 0
         * p2 :
         * created_at : 1970-01-01 05:30:00
         * updated_at : 1970-01-01 06:03:40
         * orders_id : 53
         * customer_order_id : 0
         * agro_items_request_id : null
         * vendors_sales_id :
         * otp : 3461
         * payment_by : Paytm
         * assign_status : 5
         * total_tax : 0
         * customers_id : 0
         * delivery_boy_id : 2
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
         * drop_name : naveen
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
         * cash_amount : 1000.00
         * wallet_amount : 0.00
         * cashback_used : 0
         * kms : null
         * category : null
         * weight : null
         * shipping_cost : null
         * product_price : null
         * order_information :
         * image : null
         * is_seen : 0
         * order_booking_time : null
         * order_booking_date : 2020-10-06 05:15:20
         * id : 2
         * type : collection staff
         * firstname : naveen
         * lastname : kumar
         * email_address : naveendrivedigita4l@gmail.com
         * telephone : 9024464212
         * password : 2354567
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

        private int vendors_demands_id;
        private int custom_product_id;
        private int vendors_id;
        private String vendor_accept_status;
        private String vendor_decline_comment;
        private String name;
        private String quantity;
        private String available_quantity;
        private int price;
        private String status;
        private String accept_status;
        private String order_status;
        private int acceptor_vendor_id;
        private int quantity_unit;
        private String p2;
        private String created_at;
        private String updated_at;
        private int orders_id;
        private int customer_order_id;
        private Object agro_items_request_id;
        private String vendors_sales_id;
        private int otp;
        private String payment_by;
        private int assign_status;
        private String total_tax;
        private int customers_id;
        private int delivery_boy_id;
        private String pickup_name;
        private String pickup_mobile;
        private Object pickup_company;
        private String pickup_street_address;
        private Object pickup_suburb;
        private String pickup_city;
        private String pickup_postcode;
        private Object pickup_state;
        private String pickup_country;
        private String pickup_lat;
        private String pickup_long;
        private String googleLocation;
        private String drop_name;
        private String drop_mobile;
        private Object drop_company;
        private String drop_street_address;
        private Object drop_suburb;
        private String drop_city;
        private String drop_postcode;
        private Object drop_state;
        private String drop_country;
        private String drop_lat;
        private String drop_long;
        private String drop_googleLocation;
        private Object payment_method;
        private Object last_modified;
        private Object date_purchased;
        private Object orders_date_finished;
        private Object currency;
        private Object order_price;
        private String cash_amount;
        private String wallet_amount;
        private int cashback_used;
        private Object kms;
        private Object category;
        private Object weight;
        private Object shipping_cost;
        private Object product_price;
        private String order_information;
        private Object image;
        private int is_seen;
        private Object order_booking_time;
        private String order_booking_date;
        private int id;
        private String type;
        private String firstname;
        private String lastname;
        private String email_address;
        private String telephone;
        private String password;
        private int wallet;
        private int isActive;
        private String picture;
        private String rc;
        private String driving_licence;
        private String adhaar_card;
        private String bike_no;
        private Object lat;
        private Object lng;

        public int getVendors_demands_id() {
            return vendors_demands_id;
        }

        public void setVendors_demands_id(int vendors_demands_id) {
            this.vendors_demands_id = vendors_demands_id;
        }

        public int getCustom_product_id() {
            return custom_product_id;
        }

        public void setCustom_product_id(int custom_product_id) {
            this.custom_product_id = custom_product_id;
        }

        public int getVendors_id() {
            return vendors_id;
        }

        public void setVendors_id(int vendors_id) {
            this.vendors_id = vendors_id;
        }

        public String getVendor_accept_status() {
            return vendor_accept_status;
        }

        public void setVendor_accept_status(String vendor_accept_status) {
            this.vendor_accept_status = vendor_accept_status;
        }

        public String getVendor_decline_comment() {
            return vendor_decline_comment;
        }

        public void setVendor_decline_comment(String vendor_decline_comment) {
            this.vendor_decline_comment = vendor_decline_comment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getAvailable_quantity() {
            return available_quantity;
        }

        public void setAvailable_quantity(String available_quantity) {
            this.available_quantity = available_quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccept_status() {
            return accept_status;
        }

        public void setAccept_status(String accept_status) {
            this.accept_status = accept_status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public int getAcceptor_vendor_id() {
            return acceptor_vendor_id;
        }

        public void setAcceptor_vendor_id(int acceptor_vendor_id) {
            this.acceptor_vendor_id = acceptor_vendor_id;
        }

        public int getQuantity_unit() {
            return quantity_unit;
        }

        public void setQuantity_unit(int quantity_unit) {
            this.quantity_unit = quantity_unit;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
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

        public int getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(int orders_id) {
            this.orders_id = orders_id;
        }

        public int getCustomer_order_id() {
            return customer_order_id;
        }

        public void setCustomer_order_id(int customer_order_id) {
            this.customer_order_id = customer_order_id;
        }

        public Object getAgro_items_request_id() {
            return agro_items_request_id;
        }

        public void setAgro_items_request_id(Object agro_items_request_id) {
            this.agro_items_request_id = agro_items_request_id;
        }

        public String getVendors_sales_id() {
            return vendors_sales_id;
        }

        public void setVendors_sales_id(String vendors_sales_id) {
            this.vendors_sales_id = vendors_sales_id;
        }

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }

        public String getPayment_by() {
            return payment_by;
        }

        public void setPayment_by(String payment_by) {
            this.payment_by = payment_by;
        }

        public int getAssign_status() {
            return assign_status;
        }

        public void setAssign_status(int assign_status) {
            this.assign_status = assign_status;
        }

        public String getTotal_tax() {
            return total_tax;
        }

        public void setTotal_tax(String total_tax) {
            this.total_tax = total_tax;
        }

        public int getCustomers_id() {
            return customers_id;
        }

        public void setCustomers_id(int customers_id) {
            this.customers_id = customers_id;
        }

        public int getDelivery_boy_id() {
            return delivery_boy_id;
        }

        public void setDelivery_boy_id(int delivery_boy_id) {
            this.delivery_boy_id = delivery_boy_id;
        }

        public String getPickup_name() {
            return pickup_name;
        }

        public void setPickup_name(String pickup_name) {
            this.pickup_name = pickup_name;
        }

        public String getPickup_mobile() {
            return pickup_mobile;
        }

        public void setPickup_mobile(String pickup_mobile) {
            this.pickup_mobile = pickup_mobile;
        }

        public Object getPickup_company() {
            return pickup_company;
        }

        public void setPickup_company(Object pickup_company) {
            this.pickup_company = pickup_company;
        }

        public String getPickup_street_address() {
            return pickup_street_address;
        }

        public void setPickup_street_address(String pickup_street_address) {
            this.pickup_street_address = pickup_street_address;
        }

        public Object getPickup_suburb() {
            return pickup_suburb;
        }

        public void setPickup_suburb(Object pickup_suburb) {
            this.pickup_suburb = pickup_suburb;
        }

        public String getPickup_city() {
            return pickup_city;
        }

        public void setPickup_city(String pickup_city) {
            this.pickup_city = pickup_city;
        }

        public String getPickup_postcode() {
            return pickup_postcode;
        }

        public void setPickup_postcode(String pickup_postcode) {
            this.pickup_postcode = pickup_postcode;
        }

        public Object getPickup_state() {
            return pickup_state;
        }

        public void setPickup_state(Object pickup_state) {
            this.pickup_state = pickup_state;
        }

        public String getPickup_country() {
            return pickup_country;
        }

        public void setPickup_country(String pickup_country) {
            this.pickup_country = pickup_country;
        }

        public String getPickup_lat() {
            return pickup_lat;
        }

        public void setPickup_lat(String pickup_lat) {
            this.pickup_lat = pickup_lat;
        }

        public String getPickup_long() {
            return pickup_long;
        }

        public void setPickup_long(String pickup_long) {
            this.pickup_long = pickup_long;
        }

        public String getGoogleLocation() {
            return googleLocation;
        }

        public void setGoogleLocation(String googleLocation) {
            this.googleLocation = googleLocation;
        }

        public String getDrop_name() {
            return drop_name;
        }

        public void setDrop_name(String drop_name) {
            this.drop_name = drop_name;
        }

        public String getDrop_mobile() {
            return drop_mobile;
        }

        public void setDrop_mobile(String drop_mobile) {
            this.drop_mobile = drop_mobile;
        }

        public Object getDrop_company() {
            return drop_company;
        }

        public void setDrop_company(Object drop_company) {
            this.drop_company = drop_company;
        }

        public String getDrop_street_address() {
            return drop_street_address;
        }

        public void setDrop_street_address(String drop_street_address) {
            this.drop_street_address = drop_street_address;
        }

        public Object getDrop_suburb() {
            return drop_suburb;
        }

        public void setDrop_suburb(Object drop_suburb) {
            this.drop_suburb = drop_suburb;
        }

        public String getDrop_city() {
            return drop_city;
        }

        public void setDrop_city(String drop_city) {
            this.drop_city = drop_city;
        }

        public String getDrop_postcode() {
            return drop_postcode;
        }

        public void setDrop_postcode(String drop_postcode) {
            this.drop_postcode = drop_postcode;
        }

        public Object getDrop_state() {
            return drop_state;
        }

        public void setDrop_state(Object drop_state) {
            this.drop_state = drop_state;
        }

        public String getDrop_country() {
            return drop_country;
        }

        public void setDrop_country(String drop_country) {
            this.drop_country = drop_country;
        }

        public String getDrop_lat() {
            return drop_lat;
        }

        public void setDrop_lat(String drop_lat) {
            this.drop_lat = drop_lat;
        }

        public String getDrop_long() {
            return drop_long;
        }

        public void setDrop_long(String drop_long) {
            this.drop_long = drop_long;
        }

        public String getDrop_googleLocation() {
            return drop_googleLocation;
        }

        public void setDrop_googleLocation(String drop_googleLocation) {
            this.drop_googleLocation = drop_googleLocation;
        }

        public Object getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(Object payment_method) {
            this.payment_method = payment_method;
        }

        public Object getLast_modified() {
            return last_modified;
        }

        public void setLast_modified(Object last_modified) {
            this.last_modified = last_modified;
        }

        public Object getDate_purchased() {
            return date_purchased;
        }

        public void setDate_purchased(Object date_purchased) {
            this.date_purchased = date_purchased;
        }

        public Object getOrders_date_finished() {
            return orders_date_finished;
        }

        public void setOrders_date_finished(Object orders_date_finished) {
            this.orders_date_finished = orders_date_finished;
        }

        public Object getCurrency() {
            return currency;
        }

        public void setCurrency(Object currency) {
            this.currency = currency;
        }

        public Object getOrder_price() {
            return order_price;
        }

        public void setOrder_price(Object order_price) {
            this.order_price = order_price;
        }

        public String getCash_amount() {
            return cash_amount;
        }

        public void setCash_amount(String cash_amount) {
            this.cash_amount = cash_amount;
        }

        public String getWallet_amount() {
            return wallet_amount;
        }

        public void setWallet_amount(String wallet_amount) {
            this.wallet_amount = wallet_amount;
        }

        public int getCashback_used() {
            return cashback_used;
        }

        public void setCashback_used(int cashback_used) {
            this.cashback_used = cashback_used;
        }

        public Object getKms() {
            return kms;
        }

        public void setKms(Object kms) {
            this.kms = kms;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getShipping_cost() {
            return shipping_cost;
        }

        public void setShipping_cost(Object shipping_cost) {
            this.shipping_cost = shipping_cost;
        }

        public Object getProduct_price() {
            return product_price;
        }

        public void setProduct_price(Object product_price) {
            this.product_price = product_price;
        }

        public String getOrder_information() {
            return order_information;
        }

        public void setOrder_information(String order_information) {
            this.order_information = order_information;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public int getIs_seen() {
            return is_seen;
        }

        public void setIs_seen(int is_seen) {
            this.is_seen = is_seen;
        }

        public Object getOrder_booking_time() {
            return order_booking_time;
        }

        public void setOrder_booking_time(Object order_booking_time) {
            this.order_booking_time = order_booking_time;
        }

        public String getOrder_booking_date() {
            return order_booking_date;
        }

        public void setOrder_booking_date(String order_booking_date) {
            this.order_booking_date = order_booking_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail_address() {
            return email_address;
        }

        public void setEmail_address(String email_address) {
            this.email_address = email_address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getWallet() {
            return wallet;
        }

        public void setWallet(int wallet) {
            this.wallet = wallet;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getRc() {
            return rc;
        }

        public void setRc(String rc) {
            this.rc = rc;
        }

        public String getDriving_licence() {
            return driving_licence;
        }

        public void setDriving_licence(String driving_licence) {
            this.driving_licence = driving_licence;
        }

        public String getAdhaar_card() {
            return adhaar_card;
        }

        public void setAdhaar_card(String adhaar_card) {
            this.adhaar_card = adhaar_card;
        }

        public String getBike_no() {
            return bike_no;
        }

        public void setBike_no(String bike_no) {
            this.bike_no = bike_no;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }
    }
}
