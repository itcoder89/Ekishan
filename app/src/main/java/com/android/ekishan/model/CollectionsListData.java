package com.android.ekishan.model;

import java.util.List;

public class CollectionsListData {


    /**
     * data : [{"status":"demand","collection_date":"2020-10-18 06:54:43","price":12,"name":"Bean (फलियां)","weight":"90","id":44},{"status":"sale","collection_date":null,"price":"10.00","name":"Cucumber (खीरा) 500gm","weight":15,"id":"41"},{"status":"sale","collection_date":null,"price":"80.00","name":"Apple (सेब)","weight":10,"id":"41"}]
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
         * status : demand
         * collection_date : 2020-10-18 06:54:43
         * price : 12
         * name : Bean (फलियां)
         * weight : 90
         * id : 44
         */

        private String status;
        private String collection_date;
        private int price;
        private String name;
        private String weight;
        private int id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCollection_date() {
            if(collection_date==null || collection_date.equals("")){
                collection_date = "";
            }
            return collection_date;
        }

        public void setCollection_date(String collection_date) {
            this.collection_date = collection_date;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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
