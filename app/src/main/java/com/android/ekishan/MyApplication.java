package com.android.ekishan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.android.ekishan.model.AddressListModel;
import com.android.ekishan.model.ReviewedList;
import com.android.ekishan.view.CommonUtil;

import org.json.JSONObject;

import java.util.Locale;

public class MyApplication extends Application {
    public static final String MY_PREFS_NAME = "EKISAN";
    private static final String IS_LANG = "IS_LANG";
    private static final String ISLOGIN = "ISLOGIN";
    private static final String IS_VENDOR_LOGIN = "IS_VENDOR_LOGIN";
    private static final String IS_DELIVERY_LOGIN = "IS_DELIVERY_LOGIN";
    private static final String MOBILE = "MOBILE";
    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String REFER_CODE = "REFER_CODE";
    private static final String VENDOR_ID = "vendor_id";
    private static final String DELIVERY_ID = "DELIVERY_ID";
    private static final String DELIVERY_TYPE = "delivery_type";
    public static String isFrom="ORDER";
    public static String ORDER_ID="0";
    public static String subscription_id="0";
    public static String IS_CUSTOM_BASKET="ALL";
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor editor;
    public static IncomingSMSListener listener;
	public static ReviewedList edit_review_model;
    public static AddressListModel edit_model;
    public static String totalItem="0";
    public static String totalprice="0";
    public static String shippingCost="0";
    public static String addressId="0";
    public static String date="";
    public static String basketId="0";
    public static String vendor_sales_id="0";
    public static String selectedTimeSlot="0";
    public static String collect_amount="0";
    public static JSONObject jsonObject1;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor=prefs.edit();
    }

    public static void setCustomerId(String customerId){
        editor.putString(CUSTOMER_ID,customerId);
        editor.commit();
    }

    public static void setReferCode(String referCode){
        editor.putString(REFER_CODE,referCode);
        editor.commit();
    }
    public static void setMobile(String mobile){
        editor.putString(MOBILE,mobile);
        editor.commit();
    }
    public static void setIsLogin(boolean isLogin){
        editor.putBoolean(ISLOGIN,isLogin);
        editor.commit();
    }
    public static void set_vendor_id(String vendor_id){
        editor.putString(VENDOR_ID,vendor_id);
        editor.commit();
    }
    public static void set_delivery_id(String vendor_id){
        editor.putString(DELIVERY_ID,vendor_id);
        editor.commit();
    }
    public static void set_lang(String lang){
        editor.putString(IS_LANG,lang);
        editor.commit();
    }

    public static void set_delivery_type(String type){
        editor.putString(DELIVERY_TYPE,type);
        editor.commit();
    }
	  public static String  get_delivery_type(){
       return prefs.getString(DELIVERY_TYPE,"");

    } public static String  get_delivery_id(){
       return prefs.getString(DELIVERY_ID,"");

    }public static String  get_lang(){
       return prefs.getString(IS_LANG,"en");

    }

    public static boolean isLogin(){
        return prefs.getBoolean(ISLOGIN,false);
    }

    public static void setVendorIsLogin(boolean isLogin){
        editor.putBoolean(IS_VENDOR_LOGIN,isLogin);
        editor.commit();
    }
    public static Resources resources;
    public static void setDeliveryIsLogin(boolean isLogin){
        editor.putBoolean(IS_DELIVERY_LOGIN,isLogin);
        editor.commit();
    }
    public static String change_to_hindi(int id, Activity activity) {
        try {
            if (resources != null) {
                return resources.getString(id);
            } else {
                Locale current = ConfigurationCompat.getLocales(activity.getResources().getConfiguration()).get(0);
//                SharedPreferences langsharedPref = appContext.getSharedPreferences(Splace_Screen_Fragment.MyPREFERENCES, Context.MODE_PRIVATE);
//                String selected_lang = langsharedPref.getString("LANGUAGE", "");
                String selected_lang = current.getDisplayLanguage();
                if (CommonUtil.isEmptyString(selected_lang)) {
                    if (selected_lang.equalsIgnoreCase("HINDI")) {
                       updateLocale("hi", activity);


                    } else if (selected_lang.equalsIgnoreCase("ENGLISH")) {
                         updateLocale("en", activity);

                    }
                } else {
                     updateLocale("en", activity);
//                    SharedPreferences.Editor lang_editor = langsharedPref.edit();
//                    lang_editor.putString("LANGUAGE", "");
//                    lang_editor.apply();

                }
                return activity.getResources().getString(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void updateLocale(String locale, Activity activity) {
        Configuration conf = activity.getResources().getConfiguration();
        conf.locale = new Locale(locale);
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        resources = new Resources(activity.getAssets(), metrics, conf);
    }
    public static boolean isVendorLogin(){
        return prefs.getBoolean(IS_VENDOR_LOGIN,false);
    }

    public static boolean isDeliveryLogin(){
        return prefs.getBoolean(IS_DELIVERY_LOGIN,false);
    }

    public static String getMobile(){
        return prefs.getString(MOBILE,"");
    }
    public static String getCustomerId(){
        return prefs.getString(CUSTOMER_ID,"0");
    }
    public static String getvendor_id(){
        return prefs.getString(VENDOR_ID,"0");
    }

    public static String getReferCode(){
        return prefs.getString(REFER_CODE,"");
    }

    public static void showAlert(AppCompatActivity appCompatActivity, String mesg) {
        final Flashbar flashbar = new Flashbar.Builder(appCompatActivity)
                .gravity(Flashbar.Gravity.TOP)
                .title("Alert")
                .message(mesg)
                .backgroundDrawable(R.color.redDark)
                .iconAnimation(FlashAnim.with(appCompatActivity).animateIcon().pulse()
                        .alpha()
                        .duration(750)
                        .accelerate())
                .build();
        flashbar.show();
        new Handler().postDelayed(flashbar::dismiss, 3500);
    }
}
