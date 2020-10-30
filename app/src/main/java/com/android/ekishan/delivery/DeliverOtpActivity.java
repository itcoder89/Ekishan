package com.android.ekishan.delivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.IncomingSMSListener;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.OtpActivity;
import com.android.ekishan.model.ResponseVerifyOtp;
import com.android.ekishan.model.UpdateProfileResponse;
import com.android.ekishan.vendor.AddEditVegetableActivity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliverOtpActivity extends AppCompatActivity implements IncomingSMSListener {
    TextView tvLogin;
    ApiInterface apiService;
    String mobile = "";
    EditText edt1, edt2, edt3, edt4;
    String is_rgister = "";
    TextView txt_resend_otp;
//    TextView resendOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.vendor_otp);
        is_rgister = getIntent().getStringExtra("is_register");
        tvLogin = findViewById(R.id.tvLogin);
        txt_resend_otp = findViewById(R.id.txt_resend_otp);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
//        resendOtp = findViewById(R.id.resendOtp);
        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edt1.length() >= 1) {
                    edt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edt2.length() >= 1) {
                    edt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edt3.length() >= 1) {
                    edt4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mobile = getIntent().getStringExtra("mobile");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt1.getText().toString().equalsIgnoreCase("") && !edt2.getText().toString().equalsIgnoreCase("") && !edt3.getText().toString().equalsIgnoreCase("") && !edt4.getText().toString().equalsIgnoreCase("")) {
                    String str_otp = edt1.getText().toString() + "" + edt2.getText().toString() + "" + edt3.getText().toString() + "" + edt4.getText().toString();
                    varify_otp(mobile, str_otp);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
                }




            }
        });
        txt_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vendor_sendNewOtp(mobile);
            }
        });
//
//        resendOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resendOtp(MyApplication.getCustomerId());
//            }
//        });
        startSMS_Tracking();
    }
    private void resendOtp(String customer_id) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliverOtpActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.resendOtp(customer_id,"0");


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliverOtpActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void startSMS_Tracking() {
        MyApplication.listener = this;
        SmsRetrieverClient client = SmsRetriever.getClient(this);

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void varify_otp(final String mobile, String otp) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliverOtpActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseVerifyOtp> responseCall = apiService.deliveryBoyVerifyLogin(mobile,otp, is_rgister);


        responseCall.enqueue(new Callback<ResponseVerifyOtp>() {
            @Override
            public void onResponse(Call<ResponseVerifyOtp> call, Response<ResponseVerifyOtp> response) {
                progressDialog.dismiss();

                    if (response.body().getSuccess().equals("1")) {
                        SharedPreferences preferences =getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                    MyApplication.setDeliveryIsLogin(true);
                    MyApplication.setMobile(mobile);
                    MyApplication.set_delivery_id(response.body().getData().getId());
                    MyApplication.set_delivery_type(response.body().getData().getType());
                    startActivity(new Intent(DeliverOtpActivity.this,DeliveryDashboardActivity.class));
                    finish();
//                    startActivity(new Intent(DeliverOtpActivity.this, AddEditVegetableActivity.class));
//                    finish();

                } else {
                    Toast.makeText(DeliverOtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
//                }
//                    Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();

//                if (response.body().getStatus()) {
//                    if (response.body().getData() != null) {
//
//                    }
//                } else {
//                }
            }

            @Override
            public void onFailure(Call<ResponseVerifyOtp> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliverOtpActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void vendor_sendNewOtp(final String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliverOtpActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.deliveryBoySendNewOtp(mobile);


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Toast.makeText(DeliverOtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(DeliverOtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
//                }
//                    Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();

//                if (response.body().getStatus()) {
//                    if (response.body().getData() != null) {
//
//                    }
//                } else {
//                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliverOtpActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOTPReceived(String otp) {
        edt1.setText(String.valueOf(otp.charAt(0)));
        edt2.setText(String.valueOf(otp.charAt(1)));
        edt3.setText(String.valueOf(otp.charAt(2)));
        edt4.setText(String.valueOf(otp.charAt(3)));
        if (!edt1.getText().toString().equalsIgnoreCase("") && !edt2.getText().toString().equalsIgnoreCase("") && !edt3.getText().toString().equalsIgnoreCase("") && !edt4.getText().toString().equalsIgnoreCase("")) {
            String str_otp = edt1.getText().toString() + "" + edt2.getText().toString() + "" + edt3.getText().toString() + "" + edt4.getText().toString();
            varify_otp(mobile, str_otp);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOTPTimeOut() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeliverOtpActivity.this, DeliveryLoginActivity.class));
        super.onBackPressed();
    }
}
