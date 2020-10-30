package com.android.ekishan.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.LocaleList;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.ekishan.R;
 import com.android.ekishan.pushservices.ChatModel;
import com.android.volley.toolbox.HttpResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;

/**
 * Created by SandeepY on 18112019
 **/


public class CommonUtil {

    public static final String HEADER_X_API_KEY_VALUE = "X-API-KEY:12345";
    public static final String HEADER_AUTH_KEY_VALUE = "Authorization:Basic ZGRkOjEyMzQ1Ng==";

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String NO_NETWORK_ACTION = "no_network_action";

    public static final String FROM_HOME = "FROM_HOME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    //    Notification
    public static String notificationunread = "0";
    public static String notificationread = "1";
    public static String notificationaccept = "2";
    public static String notificationdecline = "3";
    public static String notificationexpired = "4";


    public static boolean isValidEmail(String email) {
        try {
            if (isValidMatch(REGEX_EMAIL, email))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isEmptyString(String str) {
        if (str != null && !str.equals("") && !str.equals("null") && str.length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean isValidMatch(String pattern, String sequence) {
        if (TextUtils.isEmpty(sequence)) {
            return false;
        } else {
            Pattern sPattern = Pattern.compile(pattern);
            Matcher matcher = sPattern.matcher(sequence);
            return matcher.matches();
        }
    }


    public static Uri getImageUri(Context applicationContext, Bitmap imageBitmap, ContentResolver contentresolve) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(contentresolve, imageBitmap, "Title", null);
        return Uri.parse(path);
    }

    public static Uri getImageUri1(Context applicationContext, Bitmap imageBitmap, ContentResolver contentresolve) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
        String path = MediaStore.Images.Media.insertImage(contentresolve, imageBitmap, "Title", null);
        return Uri.parse(path);
    }



    @SuppressWarnings("deprecation")
    public static String getRealPathFromURI(Uri contentUri, Activity activity) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }
    //endregion Getters and Setters
    // hindi
    public static void setLocale(String lang, Context context) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    public static  String getCurrentLanguage(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return LocaleList.getDefault().get(0).getLanguage();
        } else{
            return Locale.getDefault().getLanguage();
        }
    }
    @SuppressLint("NewApi")
    public static String getPaths(Uri uri, Context context) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGoogleMedia(uri)) {
                    return uri.getLastPathSegment();
                }
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGoogleMedia(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public static String compressImage(Context context, String imagePath) {
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        if (bmp != null) {
            bmp.recycle();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filepath = getFilename(context);
        try {
            out = new FileOutputStream(filepath);
            //write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filepath;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static String getFilename(Context context) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files/Compressed");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        String mImageName = "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        String uriString = (mediaStorageDir.getAbsolutePath() + "/" + mImageName);
        ;
        return uriString;
    }
    //method to convert the selected image to base64 encoded string

    public static String ConvertBitmapToString(Bitmap bitmap) {
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage = URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    @SuppressWarnings("deprecation")

    public static void generateNotification(Context context, ChatModel firebaseMessage) {
//        int icon;
        String appName = context.getString(R.string.app_name);
        NotificationManager notificationManager;
        Intent notificationIntent;
        PendingIntent intent;
        Notification mynotify;
        //Log.e("inside greter than","outside condition");
//        ArrayList<NotifyMessage> notifyList = MessageDAO.getInstance(context).getUnreadMessages();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(appName);
//        NotificationCompat.Builder mBuilder = new Notification.Builder(context).setSmallIcon(R.drawable.ic_launcher).setContentTitle(appName);
//        icon=R.mipmap.ic_launcher;
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        Notification.Builder.InboxStyle inboxStyle = new InboxStyle();
        NotificationCompat.BigTextStyle inboxStyle = new NotificationCompat.BigTextStyle();

        mBuilder.setStyle(inboxStyle);
        inboxStyle.bigText(firebaseMessage.getModule_title());
//        if (firebaseMessage.getFlag().equals("COLL")) {
//            inboxStyle.bigText(firebaseMessage.getMessage());
//
////                if (notifyList.get(i).type.equals("image"))
////                    inboxStyle.addLine(notifyList.get(i).channel_name + " : " + "image");
////                else if (notifyList.get(i).type.equals("video"))
////                    inboxStyle.addLine(notifyList.get(i).channel_name + " : " + "video");
////                else
//            // inboxStyle.addLine(firebaseMessage.getMessage());
//
//        } else
        Bitmap bitmap_image = null;

//        if (firebaseMessage.getFlag().equals("NEWPOST") || firebaseMessage.getFlag().equals("HEALTH_TIP") || firebaseMessage.getFlag().equals("SUPPORT_CHAT") || firebaseMessage.getFlag().equals("MENGAGE_CHANNEL")) {
//            if (firebaseMessage.getType().equalsIgnoreCase("IMAGE")) {
//                bitmap_image = getBitmapFromURL(firebaseMessage.getFile_path_url());
//            }
//        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            icon = R.mipmap.ic_launcher;
            if (firebaseMessage.getFlag().equals("NEWPOST") || firebaseMessage.getFlag().equals("HEALTH_TIP") || firebaseMessage.getFlag().equals("SUPPORT_CHAT") || firebaseMessage.getFlag().equals("MENGAGE_CHANNEL")) {
                if (firebaseMessage.getChat_image().equalsIgnoreCase("IMAGE")) {
                    mBuilder.setContentTitle(appName).setContentText(firebaseMessage.getModule_title())
                            .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                            .setSubText("")
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap_image))
                            .setWhen(System.currentTimeMillis());
                } else {
                    mBuilder.setContentTitle(appName).setContentText(firebaseMessage.getModule_title())
                            .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(firebaseMessage.getModule_title()))
                            .setWhen(System.currentTimeMillis())
                            .setContentText(firebaseMessage.getModule_title());
                    ;
                }
            } else {
                mBuilder.setContentTitle(appName).setContentText(firebaseMessage.getModule_title())
                        .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(firebaseMessage.getModule_title()))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setWhen(System.currentTimeMillis())
                        .setContentText(firebaseMessage.getModule_title());
                ;
            }


        } else {
//            icon = R.mipmap.ic_launcher;
            if (firebaseMessage.getFlag().equals("NEWPOST") || firebaseMessage.getFlag().equals("HEALTH_TIP") || firebaseMessage.getFlag().equals("MENGAGE_CHANNEL") || firebaseMessage.getFlag().equals("SUPPORT_CHAT")) {
                if (firebaseMessage.getChat_image().equalsIgnoreCase("IMAGE")) {
                    mBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                            .setContentTitle(firebaseMessage.getModule_title()).setContentText(firebaseMessage.getModule_description())
                            .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                            .setColor(R.color.status_bar_blue)
                            .setWhen(System.currentTimeMillis())
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setSubText("")
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap_image))
                            .setVisibility(View.VISIBLE);
                } else {
                    mBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                            .setContentTitle(firebaseMessage.getModule_title()).setContentText(firebaseMessage.getModule_description())
                            .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                            .setColor(R.color.status_bar_blue)
                            .setWhen(System.currentTimeMillis())
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(firebaseMessage.getModule_description()))
                            .setVisibility(View.VISIBLE);
                    ;
                }
            } else {
                mBuilder.setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentTitle(firebaseMessage.getModule_title()).setContentText(firebaseMessage.getModule_description())
                        .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                        .setColor(R.color.status_bar_blue)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(firebaseMessage.getModule_description()))
                        .setVisibility(View.VISIBLE)
                        .setContentText(firebaseMessage.getModule_description());

            }

        }
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.getNotification();
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);

//        TeluscareSharedPreference teluscareSharedPreference = new TeluscareSharedPreference();
//        SharedPreferences sharedPreferences = teluscareSharedPreference.getTeluscareSharedPreference();
//
//        boolean b = TeluscareSharedPreference.getBooleanValue(sharedPreferences, IS_LOGGED_IN);
//        if (firebaseMessage.getModule_type().equalsIgnoreCase("ORDER")) {
//            if (b) {
//                notificationIntent = new Intent(context, OrderMgmtActivity.class);
//                notificationIntent.putExtra("search",firebaseMessage.getOrder_id());
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                mBuilder.setContentIntent(intent);
//            } else {
//                notificationIntent = new Intent(context, LoginActivity.class);
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(intent);
//            }
//        } else if (firebaseMessage.getModule_type().equalsIgnoreCase("maps")) {
//            if (b) {
//                notificationIntent = new Intent(context, MApActivity.class);
//                notificationIntent.putExtra("order_id",firebaseMessage.getOrder_id());
//                notificationIntent.putExtra("order_date",firebaseMessage.getOrder_date());
//                notificationIntent.putExtra("type_id",firebaseMessage.getType_id());
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                mBuilder.setContentIntent(intent);
//            } else {
//                notificationIntent = new Intent(context, LoginActivity.class);
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(intent);
//            }
//        } else if (firebaseMessage.getModule_type().equalsIgnoreCase("Schedule")) {
//            if (b) {
//                notificationIntent = new Intent(context, ScheduleListActivity.class);
//                notificationIntent.putExtra("order_id",firebaseMessage.getOrder_id());
//                notificationIntent.putExtra("type_id",firebaseMessage.getType_id());
//                notificationIntent.putExtra("job_id",firebaseMessage.getOrder_id());
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                mBuilder.setContentIntent(intent);
//            } else {
//                notificationIntent = new Intent(context, LoginActivity.class);
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(intent);
//            }
//        } else if (firebaseMessage.getModule_type().equalsIgnoreCase("service_claim")) {
//
//                notificationIntent = new Intent(context, ScheduleMainActivity.class);
//                notificationIntent.putExtra("search",firebaseMessage.getOrder_id());
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(intent);
//
//        }else if (firebaseMessage.getModule_type().equalsIgnoreCase("quotation_management")) {
//
//                notificationIntent = new Intent(context, QuotationDetail.class);
//                notificationIntent.putExtra("search",firebaseMessage.getOrder_id());
//                intent = PendingIntent.getActivity(context, uniqueInt,
//                        notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                mBuilder.setContentIntent(intent);
//
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("" + m, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mBuilder.setChannelId("" + m);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notification = mBuilder.getNotification();
        try {
//                if (gcmMessage.getIs_mute().equals("false")) {
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
//                }
        } catch (Exception e) {
            e.printStackTrace();

        }

//            int m1 = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(m, notification);


    }

    static String result = "";

//    protected static JSONObject getLocationFormGoogle(double lat, double longi) {
//        String apiRequest = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + longi + "&key=AIzaSyA7UjmnuIVewLuZtZRLZVm-ITfdh2hnAmg" + "&sensor=false";
//        HttpGet httpGet = new HttpGet(apiRequest);
//        HttpClient client = new DefaultHttpClient();
//        HttpResponse response;
//        StringBuilder stringBuilder = new StringBuilder();
//
//        try {
//            response = client.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            InputStream stream = entity.getContent();
//            int b;
//            while ((b = stream.read()) != -1) {
//                stringBuilder.append((char) b);
//            }
//        } catch (ClientProtocolException e) {
//        } catch (IOException e) {
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject = new JSONObject(stringBuilder.toString());
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//        }
//
//        return jsonObject;
//    }

//    public static String getLocationCityName(double lat, double lon) {
//        JSONObject result = getLocationFormGoogle(lat, lon);
//        return getCityAddress(result);
//    }

    protected static String getCityAddress(JSONObject result) {
        if (result.has("results")) {
            try {
                JSONArray array = result.getJSONArray("results");
                if (array.length() > 0) {
                    String str = array.getJSONObject(0).getString("formatted_address");
                    return str;
//                    for( int i = 0 ; i < array.length() ; i++ ) {
//                        JSONObject component = array.getJSONObject(i);
//
//                    }


//                    JSONObject place = array.getJSONObject(0);
//                    JSONArray components = place.getJSONArray("address_components");
//                    for( int i = 0 ; i < components.length() ; i++ ){
//                        JSONObject component = components.getJSONObject(i);
//                        JSONArray types = component.getJSONArray("types");
//                        for( int j = 0 ; j < types.length() ; j ++ ){
//                            if( types.getString(j).equals("locality") ){
//                                return component.getString("long_name");
//                            }
//                        }
//                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

//    @NonNull
//    public static String getDeviceIpAddress(Context context) {
//        String actualConnectedToNetwork = null;
//        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connManager != null) {
//            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (mWifi.isConnected()) {
//                actualConnectedToNetwork = getWifiIp(context);
//            }
//        }
//        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
//            actualConnectedToNetwork = getNetworkInterfaceIpAddress();
//        }
//        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
//            actualConnectedToNetwork = "127.0.0.1";
//        }
//        return actualConnectedToNetwork;
//    }
//
//    @Nullable
//    private static String getWifiIp(Context context) {
//        final WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
//            int ip = mWifiManager.getConnectionInfo().getIpAddress();
//            return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
//                    + ((ip >> 24) & 0xFF);
//        }
//        return null;
//    }
//
//
//    @Nullable
//    public static String getNetworkInterfaceIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface networkInterface = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                        String host = inetAddress.getHostAddress();
//                        if (!TextUtils.isEmpty(host)) {
//                            return host;
//                        }
//                    }
//                }
//
//            }
//        } catch (Exception ex) {
//            Log.e("IP Address", "getLocalIpAddress", ex);
//        }
//        return null;
//    }
//    public static  String getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
////                        Log.i(TAG, "***** IP="+ ip);
//                        return ip;
//                    }
//                }
//            }
//        } catch (SocketException ex) {
////            Log.e(TAG, ex.toString());
//        }
//        return null;
//    }


}
