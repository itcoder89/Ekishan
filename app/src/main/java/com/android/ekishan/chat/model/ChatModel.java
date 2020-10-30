package com.android.ekishan.chat.model;

import java.util.ArrayList;

public class ChatModel {

	public static final String SIDE_LEFT = "left";
	public static final String SIDE_RIGHT = "right";

	private String text = "";
	private String datetime = "";
	private String imageUrl = "";
	private String side = "";
	private String messageId = "";
	private String username = "";
	
	String senderid,receiverid;
	// getting the ArrayList value
	public ArrayList getOffArrayList() {
		return offArrayList;
	}
	// setting the ArrayList Value
	public void setOffArrayList(ArrayList offArrayList) {
		this.offArrayList = offArrayList;
	}

	private ArrayList offArrayList;
	


	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public static String getSideLeft() {
		return SIDE_LEFT;
	}

	public static String getSideRight() {
		return SIDE_RIGHT;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
