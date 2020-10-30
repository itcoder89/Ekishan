package com.android.ekishan.pushservices;

import java.io.Serializable;

/**
 * Created by a on 9/16/2016.
 */
public class Message implements Serializable {
    int id;
    int isRead;
    int channel_id;
    String message;
    String title;
    String flag;
    int question_id;
    int count;
    String firebase_reference;
    String firebase_token;
    String from_number;
    String to_number;
    int event_id;
    //for conference
    private int thinapp_id;
    private int conference_id;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String total_invitation;
    private String conference_name;
    private String start_datetime;
    private String start_endtime;
    private String description;
    private String type;
    int module_type_id;
    public String module_type;
    private String role;
    private int staff_id;
    private int service_id;
    private int customer_id;
    private String file_path_url;
    private int  payment_id;
    private String transaction_id;
    private int child_id;
    private String to_username;
    private String notification_date;
    private String timeline_child_id;
    private String chat_reference;
    private String silent;
    private String doctor_id;
    private String patient_id;
    private String notification_for;
    private String post_visit_id;
    private int chat_notification_id;

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

    public String getChat_reference() {
        return chat_reference;
    }

    public void setChat_reference(String chat_reference) {
        this.chat_reference = chat_reference;
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

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getFile_path_url() {
        return file_path_url;
    }

    public void setFile_path_url(String file_path_url) {
        this.file_path_url = file_path_url;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public int getModule_type_id() {
        return module_type_id;
    }

    public void setModule_type_id(int module_type_id) {
        this.module_type_id = module_type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getStart_endtime() {
        return start_endtime;
    }

    public void setStart_endtime(String start_endtime) {
        this.start_endtime = start_endtime;
    }

    public String getConference_name() {
        return conference_name;
    }

    public void setConference_name(String conference_name) {
        this.conference_name = conference_name;
    }

    public String getFrom_number() {
        return from_number;
    }

    public void setFrom_number(String from_number) {
        this.from_number = from_number;
    }

    public String getTo_number() {
        return to_number;
    }

    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }

    public int getThinapp_id() {
        return thinapp_id;
    }

    public void setThinapp_id(int thinapp_id) {
        this.thinapp_id = thinapp_id;
    }

    public int getConference_id() {
        return conference_id;
    }

    public void setConference_id(int conference_id) {
        this.conference_id = conference_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTotal_invitation() {
        return total_invitation;
    }

    public void setTotal_invitation(String total_invitation) {
        this.total_invitation = total_invitation;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getFirebase_reference() {
        return firebase_reference;
    }

    public void setFirebase_reference(String firebase_reference) {
        this.firebase_reference = firebase_reference;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getFrom() {
        return from_number;
    }

    public void setFrom(String from) {
        this.from_number = from;
    }

    public String getTo() {
        return to_number;
    }

    public void setTo(String to) {
        this.to_number = to;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getTimeline_child_id() {
        return timeline_child_id;
    }

    public void setTimeline_child_id(String timeline_child_id) {
        this.timeline_child_id = timeline_child_id;
    }
}
