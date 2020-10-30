package com.android.ekishan.model;

import java.util.List;

public class VendorCollectionListData {


    /**
     * data : [{"status":"sale","collection_date":{"date":"2020-10-17 14:32:05.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"price":"100.00","name":"Bean (फलियां)","weight":100,"id":44},{"status":"sale","collection_date":{"date":"2020-10-17 14:32:05.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"price":"50.00","name":"Banana (केला)","weight":50,"id":45}]
     * success : 1
     */

    private int success;
    private List<DataBean> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : sale
         * collection_date : {"date":"2020-10-17 14:32:05.000000","timezone_type":3,"timezone":"Asia/Kolkata"}
         * price : 100.00
         * name : Bean (फलियां)
         * weight : 100
         * id : 44
         */

        private String status;
        private CollectionDateBean collection_date;
        private String price;
        private String name;
        private int weight;
        private int id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public CollectionDateBean getCollection_date() {
            return collection_date;
        }

        public void setCollection_date(CollectionDateBean collection_date) {
            this.collection_date = collection_date;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public static class CollectionDateBean {
            /**
             * date : 2020-10-17 14:32:05.000000
             * timezone_type : 3
             * timezone : Asia/Kolkata
             */

            private String date;
            private int timezone_type;
            private String timezone;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getTimezone_type() {
                return timezone_type;
            }

            public void setTimezone_type(int timezone_type) {
                this.timezone_type = timezone_type;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }
        }
    }
}
