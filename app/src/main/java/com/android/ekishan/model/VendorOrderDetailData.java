package com.android.ekishan.model;

import java.util.List;

public class VendorOrderDetailData {


    /**
     * success : 1
     * message : Order Details Get successfully!
     * data : {"orders_id":39,"invoice":"1598246722","order_booking_time":"10am-1pm","delivery_name":"Naveen","delivery_street_address":"vijay central academy school jaipur","delivery_suburb":null,"delivery_city":"","order_booking_date":"2020-08-28","delivery_postcode":"302013","order_price":"148.00","customers_telephone":"9024464212","payment_method":"CASH","products_count":3,"order_status":"Pending","products":[{"orders_products_id":269,"orders_id":39,"products_id":125,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Beetroot (चुकंदर) 500gm","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1598100842.beetroot.jpg","products_description":"It is loaded with high concentration of nitrates, fiber, potassium, vitamins and minerals, and low in calories and fat.It helps to control blood pressure, improve muscle power, protect risk of heart attacks, heart failure and stroke.It is good for digestive health, increase blood flow to the brain.","product_categories":"Fresh Vegetables","products_price":"10.00","final_price":"20.00","products_tax":"0","products_quantity":2,"review_id":null,"updated_at":"-0001-11-30 00:00:00"},{"orders_products_id":270,"orders_id":39,"products_id":199,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Broccoli (ब्रोकली) 250 gm","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1580799803.brokali.jpg","products_description":"Broccoliis a great source of vitamins K and C, a good source of folate (folic acid) and also provides potassium, fiber. Vitamin C &ndash; builds collagen, which forms body tissue and bone, and helps cuts and wounds heal. Vitamin C is a powerful antioxidant and protects the body from damaging free radicals.","product_categories":"Fresh Vegetables","products_price":"115.00","final_price":"115.00","products_tax":"0","products_quantity":1,"review_id":null,"updated_at":"-0001-11-30 00:00:00"},{"orders_products_id":271,"orders_id":39,"products_id":126,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Bitter Guard (करेला)","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1598242354.bitter gourd.jpg","products_description":"Bitter gourd is used for various medicinal purposesit is loaded with fiber, vitamin C, vitamin A, foliate, calcium, beta-cerotene, potassium.It helps to control blood sugar, Lowers bad cholesterol levels.It helps to boost immune system, eye health, weight loss, skin glow.","product_categories":"Fresh Vegetables","products_price":"1.00","final_price":"1.00","products_tax":"0","products_quantity":1,"review_id":null,"updated_at":"-0001-11-30 00:00:00"}]}
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
         * orders_id : 39
         * invoice : 1598246722
         * order_booking_time : 10am-1pm
         * delivery_name : Naveen
         * delivery_street_address : vijay central academy school jaipur
         * delivery_suburb : null
         * delivery_city :
         * order_booking_date : 2020-08-28
         * delivery_postcode : 302013
         * order_price : 148.00
         * customers_telephone : 9024464212
         * payment_method : CASH
         * products_count : 3
         * order_status : Pending
         * products : [{"orders_products_id":269,"orders_id":39,"products_id":125,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Beetroot (चुकंदर) 500gm","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1598100842.beetroot.jpg","products_description":"It is loaded with high concentration of nitrates, fiber, potassium, vitamins and minerals, and low in calories and fat.It helps to control blood pressure, improve muscle power, protect risk of heart attacks, heart failure and stroke.It is good for digestive health, increase blood flow to the brain.","product_categories":"Fresh Vegetables","products_price":"10.00","final_price":"20.00","products_tax":"0","products_quantity":2,"review_id":null,"updated_at":"-0001-11-30 00:00:00"},{"orders_products_id":270,"orders_id":39,"products_id":199,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Broccoli (ब्रोकली) 250 gm","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1580799803.brokali.jpg","products_description":"Broccoliis a great source of vitamins K and C, a good source of folate (folic acid) and also provides potassium, fiber. Vitamin C &ndash; builds collagen, which forms body tissue and bone, and helps cuts and wounds heal. Vitamin C is a powerful antioxidant and protects the body from damaging free radicals.","product_categories":"Fresh Vegetables","products_price":"115.00","final_price":"115.00","products_tax":"0","products_quantity":1,"review_id":null,"updated_at":"-0001-11-30 00:00:00"},{"orders_products_id":271,"orders_id":39,"products_id":126,"basket_id":0,"baskets_products":"","products_model":null,"products_name":"Bitter Guard (करेला)","products_image":"https://recipetipswithrubia.in/resources/assets/images/product_images/1598242354.bitter gourd.jpg","products_description":"Bitter gourd is used for various medicinal purposesit is loaded with fiber, vitamin C, vitamin A, foliate, calcium, beta-cerotene, potassium.It helps to control blood sugar, Lowers bad cholesterol levels.It helps to boost immune system, eye health, weight loss, skin glow.","product_categories":"Fresh Vegetables","products_price":"1.00","final_price":"1.00","products_tax":"0","products_quantity":1,"review_id":null,"updated_at":"-0001-11-30 00:00:00"}]
         */

        private int orders_id;
        private String invoice;
        private String order_booking_time;
        private String delivery_name;
        private String delivery_street_address;
        private Object delivery_suburb;
        private String delivery_city;
        private String order_booking_date;
        private String delivery_postcode;
        private String order_price;
        private String customers_telephone;
        private String payment_method;
        private int products_count;
        private String order_status;
        private List<ProductsBean> products;

        public int getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(int orders_id) {
            this.orders_id = orders_id;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getOrder_booking_time() {
            return order_booking_time;
        }

        public void setOrder_booking_time(String order_booking_time) {
            this.order_booking_time = order_booking_time;
        }

        public String getDelivery_name() {
            return delivery_name;
        }

        public void setDelivery_name(String delivery_name) {
            this.delivery_name = delivery_name;
        }

        public String getDelivery_street_address() {
            return delivery_street_address;
        }

        public void setDelivery_street_address(String delivery_street_address) {
            this.delivery_street_address = delivery_street_address;
        }

        public Object getDelivery_suburb() {
            return delivery_suburb;
        }

        public void setDelivery_suburb(Object delivery_suburb) {
            this.delivery_suburb = delivery_suburb;
        }

        public String getDelivery_city() {
            return delivery_city;
        }

        public void setDelivery_city(String delivery_city) {
            this.delivery_city = delivery_city;
        }

        public String getOrder_booking_date() {
            return order_booking_date;
        }

        public void setOrder_booking_date(String order_booking_date) {
            this.order_booking_date = order_booking_date;
        }

        public String getDelivery_postcode() {
            return delivery_postcode;
        }

        public void setDelivery_postcode(String delivery_postcode) {
            this.delivery_postcode = delivery_postcode;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getCustomers_telephone() {
            return customers_telephone;
        }

        public void setCustomers_telephone(String customers_telephone) {
            this.customers_telephone = customers_telephone;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public int getProducts_count() {
            return products_count;
        }

        public void setProducts_count(int products_count) {
            this.products_count = products_count;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * orders_products_id : 269
             * orders_id : 39
             * products_id : 125
             * basket_id : 0
             * baskets_products :
             * products_model : null
             * products_name : Beetroot (चुकंदर) 500gm
             * products_image : https://recipetipswithrubia.in/resources/assets/images/product_images/1598100842.beetroot.jpg
             * products_description : It is loaded with high concentration of nitrates, fiber, potassium, vitamins and minerals, and low in calories and fat.It helps to control blood pressure, improve muscle power, protect risk of heart attacks, heart failure and stroke.It is good for digestive health, increase blood flow to the brain.
             * product_categories : Fresh Vegetables
             * products_price : 10.00
             * final_price : 20.00
             * products_tax : 0
             * products_quantity : 2
             * review_id : null
             * updated_at : -0001-11-30 00:00:00
             */

            private int orders_products_id;
            private int orders_id;
            private int products_id;
            private int basket_id;
            private String baskets_products;
            private Object products_model;
            private String products_name;
            private String products_image;
            private String products_description;
            private String product_categories;
            private String products_price;
            private String final_price;
            private String products_tax;
            private int products_quantity;
            private Object review_id;
            private String updated_at;

            public int getOrders_products_id() {
                return orders_products_id;
            }

            public void setOrders_products_id(int orders_products_id) {
                this.orders_products_id = orders_products_id;
            }

            public int getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(int orders_id) {
                this.orders_id = orders_id;
            }

            public int getProducts_id() {
                return products_id;
            }

            public void setProducts_id(int products_id) {
                this.products_id = products_id;
            }

            public int getBasket_id() {
                return basket_id;
            }

            public void setBasket_id(int basket_id) {
                this.basket_id = basket_id;
            }

            public String getBaskets_products() {
                return baskets_products;
            }

            public void setBaskets_products(String baskets_products) {
                this.baskets_products = baskets_products;
            }

            public Object getProducts_model() {
                return products_model;
            }

            public void setProducts_model(Object products_model) {
                this.products_model = products_model;
            }

            public String getProducts_name() {
                return products_name;
            }

            public void setProducts_name(String products_name) {
                this.products_name = products_name;
            }

            public String getProducts_image() {
                return products_image;
            }

            public void setProducts_image(String products_image) {
                this.products_image = products_image;
            }

            public String getProducts_description() {
                return products_description;
            }

            public void setProducts_description(String products_description) {
                this.products_description = products_description;
            }

            public String getProduct_categories() {
                return product_categories;
            }

            public void setProduct_categories(String product_categories) {
                this.product_categories = product_categories;
            }

            public String getProducts_price() {
                return products_price;
            }

            public void setProducts_price(String products_price) {
                this.products_price = products_price;
            }

            public String getFinal_price() {
                return final_price;
            }

            public void setFinal_price(String final_price) {
                this.final_price = final_price;
            }

            public String getProducts_tax() {
                return products_tax;
            }

            public void setProducts_tax(String products_tax) {
                this.products_tax = products_tax;
            }

            public int getProducts_quantity() {
                return products_quantity;
            }

            public void setProducts_quantity(int products_quantity) {
                this.products_quantity = products_quantity;
            }

            public Object getReview_id() {
                return review_id;
            }

            public void setReview_id(Object review_id) {
                this.review_id = review_id;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }
    }
}
