package com.android.ekishan.activity;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.IncomingSMSListener;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.ResponseLogin;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity implements IncomingSMSListener {
    TextView tvLogin;
    ApiInterface apiService;
    String mobile = "";
    EditText edt1, edt2, edt3, edt4;
    TextView resendOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.kishan_otp);
        tvLogin = findViewById(R.id.tvLogin);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        resendOtp = findViewById(R.id.resendOtp);
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
                if (!edt1.getText().toString().equalsIgnoreCase("")&&!edt2.getText().toString().equalsIgnoreCase("")&&!edt3.getText().toString().equalsIgnoreCase("")&&!edt4.getText().toString().equalsIgnoreCase("")) {
                    String str_otp = edt1.getText().toString() + "" + edt2.getText().toString() + "" + edt3.getText().toString() + "" + edt4.getText().toString();
                    varify_otp(mobile, str_otp);
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Otp",Toast.LENGTH_SHORT).show();
                }

            }
        });
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp(MyApplication.getCustomerId());
            }
        });
        startSMS_Tracking();
    }


    private void resendOtp(String customer_id) {
        final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.resendOtp(customer_id,getIntent().getStringExtra("isregister"));


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
                Toast.makeText(OtpActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void startSMS_Tracking() {
        MyApplication.listener=this;
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
        final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseLogin> responseCall = apiService.verifyOtp(otp, mobile,getIntent().getStringExtra("isregister"));


        responseCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    SharedPreferences preferences =getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    MyApplication.setIsLogin(true);
                    MyApplication.setMobile(mobile);
                    MyApplication.setCustomerId(response.body().getData().getCustomers_id());
                    MyApplication.setReferCode(response.body().getData().getRefercode());
                    finish();

                } else {
                    Toast.makeText(OtpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(OtpActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOTPReceived(String otp) {
        edt1.setText(String.valueOf(otp.charAt(0)));
        edt2.setText(String.valueOf(otp.charAt(1)));
        edt3.setText(String.valueOf(otp.charAt(2)));
        edt4.setText(String.valueOf(otp.charAt(3)));
        if (!edt1.getText().toString().equalsIgnoreCase("")&&!edt2.getText().toString().equalsIgnoreCase("")&&!edt3.getText().toString().equalsIgnoreCase("")&&!edt4.getText().toString().equalsIgnoreCase("")) {
            String str_otp = edt1.getText().toString() + "" + edt2.getText().toString() + "" + edt3.getText().toString() + "" + edt4.getText().toString();
            varify_otp(mobile, str_otp);
        }else {
            Toast.makeText(getApplicationContext(),"Invalid Otp",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOTPTimeOut() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
        super.onBackPressed();
    }
}
