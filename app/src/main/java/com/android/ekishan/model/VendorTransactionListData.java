package com.android.ekishan.model;

import java.util.List;

public class VendorTransactionListData {


    /**
     * success : 1
     * message :
     * data : {"transactions":[{"status":"demand","transaction_date":{"date":"2020-10-14 14:02:16.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"price":701,"name":"Banana (केला)","weight":"601","id":39}]}
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
        private List<TransactionsBean> transactions;

        public List<TransactionsBean> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<TransactionsBean> transactions) {
            this.transactions = transactions;
        }

        public static class TransactionsBean {
            /**
             * status : demand
             * transaction_date : {"date":"2020-10-14 14:02:16.000000","timezone_type":3,"timezone":"Asia/Kolkata"}
             * price : 701
             * name : Banana (केला)
             * weight : 601
             * id : 39
             */

            private String status;
            private TransactionDateBean transaction_date;
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

            public TransactionDateBean getTransaction_date() {
                return transaction_date;
            }

            public void setTransaction_date(TransactionDateBean transaction_date) {
                this.transaction_date = transaction_date;
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

            public static class TransactionDateBean {
                /**
                 * date : 2020-10-14 14:02:16.000000
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
}
