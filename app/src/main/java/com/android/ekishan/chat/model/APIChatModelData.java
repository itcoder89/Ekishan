package com.android.ekishan.chat.model;

import java.util.List;

public class APIChatModelData {

    public static final String SIDE_LEFT = "left";
    public static final String SIDE_RIGHT = "right";
    /**
     * success : 1
     * message :
     * data : [{"id":5,"message":"fsdfdfsdf","status":"unread","sender":1,"receiver":2,"created_at":"2020-10-07 17:51:19","updated_at":"2020-10-07 17:51:19"},{"id":6,"message":"ttt","status":"unread","sender":1,"receiver":2,"created_at":"2020-10-07 17:52:31","updated_at":"2020-10-07 17:52:31"},{"id":7,"message":"yyyyy","status":"unread","sender":1,"receiver":2,"created_at":"2020-10-07 17:53:33","updated_at":"2020-10-07 17:53:33"},{"id":8,"message":"test message by admin","status":"unread","sender":2,"receiver":1,"created_at":"2020-10-07 17:53:33","updated_at":"2020-10-07 17:53:33"},{"id":9,"message":"yyyyy","status":"unread","sender":2,"receiver":1,"created_at":"2020-10-07 17:53:33","updated_at":"2020-10-07 17:53:33"},{"id":10,"message":"hello this is test message","status":"unread","sender":2,"receiver":1,"created_at":"2020-10-08 22:42:48","updated_at":"2020-10-08 22:42:48"}]
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
         * id : 5
         * message : fsdfdfsdf
         * status : unread
         * sender : 1
         * receiver : 2
         * created_at : 2020-10-07 17:51:19
         * updated_at : 2020-10-07 17:51:19
         */

        private int id;
        private String message;
        private String status;
        private int sender;
        private int receiver;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getSender() {
            return sender;
        }

        public void setSender(int sender) {
            this.sender = sender;
        }

        public int getReceiver() {
            return receiver;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
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
    }
}
