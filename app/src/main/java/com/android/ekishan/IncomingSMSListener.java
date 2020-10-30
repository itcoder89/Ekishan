package com.android.ekishan;

public interface IncomingSMSListener {

    void onOTPReceived(String otp);

    void onOTPTimeOut();
}
