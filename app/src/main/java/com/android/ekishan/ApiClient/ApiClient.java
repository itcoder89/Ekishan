package com.android.ekishan.ApiClient;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //public static final String BASE_URL = "http://ekissan.drivedigital.co.in/";
    public static final String BASE_URL = "https://ekisans.com/";
   // public static final String BASE_URL = "http://ekissan.drivedigital.co.in/";
    //public static final String BASE_URL = "https://recipetipswithrubia.in/";
    //https://recipetipswithrubia.in/
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …
// add logging as last interceptors
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
//                    .addConverterFactory(new GsonConverterFactory())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("ApiResponse", message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = (new OkHttpClient.Builder().addInterceptor(logInterceptor).addNetworkInterceptor(chain -> {
            Request originalRequest = chain.request();
            CacheControl control = originalRequest.cacheControl();
            int maxAge = control.maxAgeSeconds();
            Request request = originalRequest.newBuilder()
//                    .header("Content-Type","application/json")
//                    .header("Authorization", MyApplication.tinyDB.getString(Constants.AUTHTOKEN))
//                    .header("FcmToken", MyApplication.tinyDB.getString(Constants.SHARED_PREFERENCE_USER_FCM_TOKEN))
                    //.header("X-app-os", "android")
//                    .header("X-app-version", CommonUtils.getAppVersion(context))
                    .build();
            Response response = chain.proceed(request);

            if (maxAge > 0) {
                String cacheHeaderValue = "private, max-age=" + control.maxAgeSeconds();
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
            return response;
        }));
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        return client.build();
    }
}
