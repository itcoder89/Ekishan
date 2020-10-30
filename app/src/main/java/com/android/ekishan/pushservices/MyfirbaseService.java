package com.android.ekishan.pushservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.android.ekishan.view.CommonUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.NotificationCompat;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyfirbaseService extends FirebaseMessagingService {

    public static int notificationID = 1;
//    Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
//            R.drawable.logo);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("inside furebase--", "inside error======================================================");

        if (remoteMessage.getData().size() > 0) {
            Log.d("", "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("", "Message Notification Body: " +
                    remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject obj = new JSONObject(remoteMessage.getData().get("moredata"));
                JSONObject msg = new JSONObject(remoteMessage.getData().get("message"));
                if (null!=obj.get("module")){
                    ChatModel message=new ChatModel();
                    message.setModule_type(obj.get("module").toString());
                    message.setFlag(obj.get("sub_module").toString());
                    message.setModule_type_id(obj.get("module_id").toString());
                    message.setOrder_id(obj.get("order_id").toString());
                    try {
                        message.setType_id(obj.get("type_id").toString());
                    }catch (Exception e){
                        message.setType_id("");
                    }
                    message.setModule_title(msg.get("title").toString());
                    message.setModule_description(msg.get("body").toString());
                    CommonUtil.generateNotification(getApplicationContext(),message);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}
