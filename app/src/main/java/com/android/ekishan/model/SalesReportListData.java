package com.android.ekishan.model;

import java.util.List;

public class SalesReportListData {


    /**
     * data : [{"status":"sale","sale_status":"pending","collection_date":"2020-08-26 03:21:10","price":"12.00","name":"Bean (फलियां)","weight":"12 Kg","id":14},{"status":"sale","sale_status":"pending","collection_date":"2020-10-06 05:07:16","price":"450.00","name":"Bean (फलियां)","weight":"560 Kg","id":29},{"status":"sale","sale_status":"pending","collection_date":"2020-10-06 05:46:53","price":"5600.00","name":"Apple (सेब)","weight":"5601 Kg","id":30},{"status":"sale","sale_status":"pending","collection_date":"2020-10-06 05:51:51","price":"10001.00","name":"Turnip (शलजम)","weight":"10002 Kg","id":31},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-06 07:02:04","price":"5001.00","name":"Brinjal","weight":"5002 Kg","id":32},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-06 07:01:52","price":"3001.00","name":"Mango (आम) 1 kg","weight":"3002 Kg","id":33},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-17 14:29:57","price":"100.00","name":"Bean (फलियां)","weight":"100 Kg","id":40},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-17 14:29:57","price":"50.00","name":"Banana (केला)","weight":"50 Kg","id":40},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-18 06:58:09","price":"10.00","name":"Cucumber (खीरा) 500gm","weight":"15 Kg","id":41},{"status":"sale","sale_status":"accepted","collection_date":"2020-10-18 06:58:09","price":"80.00","name":"Apple (सेब)","weight":"10 Kg","id":41}]
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
         * sale_status : pending
         * collection_date : 2020-08-26 03:21:10
         * price : 12.00
         * name : Bean (फलियां)
         * weight : 12 Kg
         * id : 14
         */

        private String status;
        private String sale_status;
        private String collection_date;
        private String price;
        private String name;
        private String weight;
        private int id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSale_status() {
            return sale_status;
        }

        public void setSale_status(String sale_status) {
            this.sale_status = sale_status;
        }

        public String getCollection_date() {
            return collection_date;
        }

        public void setCollection_date(String collection_date) {
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

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
