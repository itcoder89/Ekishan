package com.android.ekishan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.delivery.DeliveryDashboardActivity;
import com.android.ekishan.model.VendorTransactionDemandData;
import com.android.ekishan.model.VersionData;
import com.android.ekishan.vendor.VendorMainActivity;
import com.android.ekishan.view.CommonUtil;

public class SplashScreen extends AppCompatActivity {

    private String version_name="";
    ApiInterface apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getVersionName();

    }
    private void getVersionName() {
        final ProgressDialog progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<VersionData> responseCall = apiService.getAppVersion();
        responseCall.enqueue(new Callback<VersionData>() {
            @Override
            public void onResponse(Call<VersionData> call, Response<VersionData> response) {
                progressDialog.dismiss();
                version_name=response.body().getApp_version();
                Log.e("onResponse", "version_name: " + version_name);
                Handler handler=new Handler();
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        if (MyApplication.get_lang().equalsIgnoreCase("en")) {
                            CommonUtil.setLocale("en", SplashScreen.this);
                            MyApplication.updateLocale("en", SplashScreen.this);
                        } else {
                            CommonUtil.setLocale("hi", SplashScreen.this);
                            MyApplication.updateLocale("hi", SplashScreen.this);
                        }
                        if (MyApplication.isVendorLogin()){
                            startActivity(new Intent(SplashScreen.this, VendorMainActivity.class)
                                    .putExtra("version_name", version_name));
                        }else if (MyApplication.isDeliveryLogin()){
                            startActivity(new Intent(SplashScreen.this, DeliveryDashboardActivity.class)
                                    .putExtra("version_name", version_name));
                        }else {
                            startActivity(new Intent(SplashScreen.this, MainActivity.class)
                                    .putExtra("version_name", version_name));
                        }
                        finish();
                    }
                };
                handler.postDelayed(runnable,1000);
            }

            @Override
            public void onFailure(Call<VersionData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                //Toast.makeText(SplashScreen.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
