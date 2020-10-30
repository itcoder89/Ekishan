package com.android.ekishan.payment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.OrderSuccesFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaytmMerchantActivity extends AppCompatActivity {

    PaytmPGService Service;
    String CHECKSUMHASH1, orderid, customerid, price, MID = "DMifSK80547835598386"/*MyApplication.paytm_mid*/,
            Industry_TYpe = "Retail", Channel_ID = "WAP", WEBSITE = "DEFAULT";
            /*String CHECKSUMHASH1, orderid, customerid, price, MID = MyApplication.paytm_mid,
            Industry_TYpe = "Retail", Channel_ID = "WAP", WEBSITE = "WEBSTAGING";
*/
            ApiInterface apiService;
    RequestQueue requestQueue;
    String payment_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        if (getIntent() != null && getIntent().getExtras() != null) {
            price = getIntent().getExtras().getString("amount")+".00";
            payment_type = getIntent().getExtras().getString("payment_type");
        }

        requestQueue = Volley.newRequestQueue(PaytmMerchantActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //This is to refresh the order id: Only for the Sample App's purpose.
    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        onStartTransaction();
    }


    public void onStartTransaction() {
//        CHECKSUMHASH1 = "";
        orderid = "";
        customerid = "";
        orderid = MyApplication.getCustomerId() + "EKISAN" + System.currentTimeMillis();
        customerid = "EKISAN" + MyApplication.getCustomerId();


        CallVolley(ApiClient.BASE_URL+"paytmChecksum");
    }

    public void CallVolley(String a) {
//        merchantBinding.setRefreshing(true);
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            StringRequest strRequest = new StringRequest(Request.Method.POST, a,
                    response -> {
//                        merchantBinding.setRefreshing(false);
                        progressDialog.cancel();
                        try {
                            Log.i("CheckVer is", response);
                            JSONObject jsonObject = new JSONObject(response);
                            CHECKSUMHASH1 = jsonObject.getJSONObject("data").has("CHECKSUMHASH") ? jsonObject.getJSONObject("data").getString("CHECKSUMHASH") : "";
                            CallPaytmIntegration();
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    },
                    error -> {
//                        merchantBinding.setRefreshing(false);
                        progressDialog.cancel();
                        showToast(error.toString());
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    //paytm live BBF
                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("MID", MID);
                    paramMap.put("ORDER_ID", orderid);
                    paramMap.put("CUST_ID", customerid);
                    paramMap.put("INDUSTRY_TYPE_ID", Industry_TYpe);
                    paramMap.put("CHANNEL_ID", Channel_ID);
                    paramMap.put("TXN_AMOUNT", price);
                    paramMap.put("WEBSITE", WEBSITE);
                    paramMap.put("EMAIL", "ekjpr20@gmail.com");
                    paramMap.put("MOBILE_NO", MyApplication.getMobile());
                    //paramMap.put("MOBILE_NO", PHONE_NO);
                    paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid);//Provided by Paytm live
//                     paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid);//Provided by Paytm Staging url

                    return paramMap;
                }
            };
            requestQueue.add(strRequest);
        } catch (Exception e) {
            showToast("---" + e);
        }

    }

    String ORDERID = "";

    public void CallPaytmIntegration() {
        Service = PaytmPGService.getProductionService();

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", MID);
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("CUST_ID", customerid);
        paramMap.put("INDUSTRY_TYPE_ID", Industry_TYpe);
        paramMap.put("CHANNEL_ID", Channel_ID);
        paramMap.put("TXN_AMOUNT", price);
        paramMap.put("WEBSITE", WEBSITE);
        paramMap.put("EMAIL", "ekjpr20@gmail.com");
        paramMap.put("MOBILE_NO", MyApplication.getMobile());

        //  paramMap.put("MOBILE_NO", PHONE_NO);
        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid);//Provided by Paytm
//          paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid);//Provided by Paytm
        paramMap.put("CHECKSUMHASH", CHECKSUMHASH1);

        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.d("LOG 1", "UI Error Occur.");
                showToast("UI Error Occur.");
                finish();
            }

            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.d("LOG 2", "Payment Transaction : " + inResponse);
                //   Log.e("RESPONSE", inResponse.toString());
                String status = (String) inResponse.get("STATUS");

                if (status.equals("TXN_SUCCESS")) {
                    ORDERID = (String) inResponse.get("ORDERID");
//                    addBalance();
                    if (payment_type.equalsIgnoreCase("PAYTM")) {
                        placeOrder(payment_type);
                    }else {
                        addToCustomerWallet();
                    }
                } else {
                    String reason = (String) inResponse.get("RESPMSG");
                    showToast(reason);
                    finishThisActivity();
                }
            }

            @Override
            public void networkNotAvailable() {
                showToast("UI Error Occur.");
                Log.d("LOG 3", "UI Error Occur.");
                // AppUtils.showErrorr(PaytmMerchantActivity.this, "UI Error occured!");
                finishThisActivity();
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.d("LOG 4", "UI Error Occur.");
                showToast("server side error");
                finishThisActivity();
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Log.d("LOG 5", "web page error");
                finishThisActivity();
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.d("LOG 6", "back pressed");
                finishThisActivity();
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.d("LOG 7", "Payment Transaction Failed " + inErrorMessage);
                showToast("Payment transaction failed");
                finishThisActivity();
            }

        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog d = new AlertDialog.Builder(PaytmMerchantActivity.this).create();
        d.setMessage("Are you sure to cancel the payment");
        d.setTitle("Cancel Payment");
        d.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> finish());
        d.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {

        });
        d.show();
    }

    public void showToast(String message) {
        Toast.makeText(PaytmMerchantActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    private void finishThisActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    private void addToCustomerWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.addToCustomerWallet(MyApplication.isVendorLogin()?MyApplication.getvendor_id():MyApplication.getCustomerId(),MyApplication.isVendorLogin()?"VENDOR":"CUSTOMER",price,orderid);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    if (res!=null) {
                        JSONObject jsonObject = new JSONObject(res);
                        finish();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeOrder(String payment_type) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.placeOrder(MyApplication.getCustomerId(),"0",MyApplication.totalprice,"0","0","0",MyApplication.addressId,payment_type,MyApplication.date,MyApplication.selectedTimeSlot,orderid);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                        MyApplication.jsonObject1=jsonObject1;
                        Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startMain.putExtra("paymentSuccess",true);
                        startActivity(startMain);
                        finishThisActivity();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

