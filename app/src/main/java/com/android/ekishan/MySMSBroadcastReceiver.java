package com.android.ekishan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class MySMSBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);


            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.
                    //  Log.e("message", message);


//                    String messages[] = message.split(".");
//                    message = messages[0].replaceAll("[^0-9]+", "");

                    if (MyApplication.listener != null) {
                        MyApplication.listener.onOTPReceived(takeLast(message.split("\\.")[0].trim(),4));
                        //context.unregisterReceiver(this);
                    }

                    break;
                case CommonStatusCodes.TIMEOUT:
                    String messge = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.
                    // Log.e("message", messge);
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    if (MyApplication.listener != null)
                        MyApplication.listener.onOTPTimeOut();
                    break;
            }
        }
    }
    public static String takeLast(String value, int count) {
        if (value == null || value.trim().length() == 0 || count < 1) {
            return "";
        }

        if (value.length() > count) {
            return value.substring(value.length() - count);
        } else {
            return value;
        }
    }
}
