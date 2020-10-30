package com.android.ekishan.pushservices;

import java.io.Serializable;

/**
 * Created by a on 11/18/2016.
 */
public class ChatModel implements Serializable {
    public Long notification_id;
    public String module_type;
    public int app_id;
    public String module_type_id;
    public String is_read;
    public String module_title;
    public String module_description;
    public String order_id;
    public String type_id;
    public String order_date;
    public String to_number;
    public int channel_id;
    private String flag;
    private int service_id;
    private String role;
    private String transaction_id;
    private int child_id;
    private String day_type;
    private String to_username;
    private String notification_date;
    private String silent;
    private String chat_image;
    private String doctor_id;
    private String patient_id;
    private String notification_for;
    private String post_visit_id;
    private int chat_notification_id;
    private String room_name;
    private String twilio_token;
    private String time;
    private String from_mobile;


    private int doctors_thin_app_id;

    public int getDoctors_thin_app_id() {
        return doctors_thin_app_id;
    }

    public void setDoctors_thin_app_id(int doctors_thin_app_id) {
        this.doctors_thin_app_id = doctors_thin_app_id;
    }

    public String getFrom_mobile() {
        return from_mobile;
    }

    public void setFrom_mobile(String from_mobile) {
        this.from_mobile = from_mobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getTwilio_token() {
        return twilio_token;
    }

    public void setTwilio_token(String twilio_token) {
        this.twilio_token = twilio_token;
    }

    public String getPost_visit_id() {
        return post_visit_id;
    }

    public void setPost_visit_id(String post_visit_id) {
        this.post_visit_id = post_visit_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getNotification_for() {
        return notification_for;
    }

    public void setNotification_for(String notification_for) {
        this.notification_for = notification_for;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public int getChat_notification_id() {
        return chat_notification_id;
    }

    public void setChat_notification_id(int chat_notification_id) {
        this.chat_notification_id = chat_notification_id;
    }

    public String getSilent() {
        return silent;
    }

    public void setSilent(String silent) {
        this.silent = silent;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }

    public String getTo_username() {
        return to_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getTo_number() {
        return to_number;
    }

    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }

    public Long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getModule_type_id() {
        return module_type_id;
    }

    public void setModule_type_id(String module_type_id) {
        this.module_type_id = module_type_id;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getModule_title() {
        return module_title;
    }

    public void setModule_title(String module_title) {
        this.module_title = module_title;
    }

    public String getModule_description() {
        return module_description;
    }

    public void setModule_description(String module_description) {
        this.module_description = module_description;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
