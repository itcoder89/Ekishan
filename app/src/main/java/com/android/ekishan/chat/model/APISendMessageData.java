package com.android.ekishan.chat.model;

public class APISendMessageData {


    /**
     * success : 1
     * message :
     * data : {"sender":"2","receiver":1,"message":"hi this is testing","updated_at":"2020-10-08 23:11:55","created_at":"2020-10-08 23:11:55","id":12}
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
         * sender : 2
         * receiver : 1
         * message : hi this is testing
         * updated_at : 2020-10-08 23:11:55
         * created_at : 2020-10-08 23:11:55
         * id : 12
         */

        private String sender;
        private int receiver;
        private String message;
        private String updated_at;
        private String created_at;
        private int id;

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public int getReceiver() {
            return receiver;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
