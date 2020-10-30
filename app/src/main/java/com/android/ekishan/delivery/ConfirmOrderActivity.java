package com.android.ekishan.delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.IncomingSMSListener;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.DeliveryOrderItemsResponse;
import com.android.ekishan.model.ResponseDeliveryBoyAcceptOrder;
import com.android.ekishan.model.ResponseVerifyOtp;
import com.android.ekishan.model.UpdateProfileResponse;
import com.android.ekishan.vendor.AddEditVegetableActivity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity implements IncomingSMSListener {
    TextView tvLogin;
    ApiInterface apiService;
    String mobile = "";
    EditText edt1, edt2, edt3, edt4;
    String is_rgister = "";
    RadioGroup payment_mode;
    RadioButton cash_pay, online_pay;
    String order_id;
    String agro_item_id;
    String payment_status = "";
    TextView txt_paid,totalAmount;
    String order_type = "";
    LinearLayout lldelivery, llcollection;
    CheckBox chk_wallet, chk_cash;
    EditText edt_wallet;
    EditText edt_cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.confirm_order_layout);
        is_rgister = getIntent().getStringExtra("is_register");
        payment_status = getIntent().getStringExtra("payment_status");
        order_type = getIntent().getStringExtra("order_type");
        agro_item_id = getIntent().getStringExtra("agro_item_id");
        payment_mode = (RadioGroup) findViewById(R.id.group);
        cash_pay = (RadioButton) findViewById(R.id.cash_pay);
        online_pay = (RadioButton) findViewById(R.id.online_pay);
        chk_wallet = findViewById(R.id.chk_wallet);
        chk_cash = findViewById(R.id.chk_cash);
        edt_cash = findViewById(R.id.edt_cash);
        edt_wallet = findViewById(R.id.edt_wallet);
        tvLogin = findViewById(R.id.tvLogin);
        lldelivery = findViewById(R.id.lldelivery);
        llcollection = findViewById(R.id.llcollection);
        totalAmount = findViewById(R.id.totalAmount);
        txt_paid = findViewById(R.id.txt_paid);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        totalAmount.setText(MyApplication.collect_amount+"/-");
        chk_wallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edt_wallet.setEnabled(true);
                } else {
                    edt_wallet.setEnabled(false);
                }
            }
        });
        chk_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edt_cash.setEnabled(true);
                } else {
                    edt_cash.setEnabled(false);
                }
            }
        });
        if (MyApplication.get_delivery_type().equalsIgnoreCase("delivery")) {
            lldelivery.setVisibility(View.VISIBLE);
            llcollection.setVisibility(View.GONE);
            if (payment_status.equalsIgnoreCase("PAYTM")) {
                txt_paid.setVisibility(View.VISIBLE);
                payment_mode.setVisibility(View.GONE);
            } else {
                txt_paid.setVisibility(View.GONE);
                payment_mode.setVisibility(View.VISIBLE);
            }
        } else {
            lldelivery.setVisibility(View.GONE);
            llcollection.setVisibility(View.VISIBLE);
        }

        order_id = getIntent().getStringExtra("order_id");
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

                    int selectedId = payment_mode.getCheckedRadioButtonId();
                    RadioButton select_payment = (RadioButton) findViewById(selectedId);
                    String status = select_payment.getText().toString();
                    if (MyApplication.get_delivery_type().equalsIgnoreCase("delivery")) {
                        if (status.equalsIgnoreCase("")) {
                            Toast.makeText(getApplicationContext(), "Please Select Payment Method", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        deliveryBoyConfirmOrder(MyApplication.get_delivery_id(), order_id, str_otp, status, "", "");
                    } else {
                        String cash_amount = "";
                        String wallet_amount = "";
                        if (chk_cash.isChecked()) {
                            cash_amount=edt_cash.getText().toString();
                            if (edt_cash.getText().toString().length() < 1) {
                                Toast.makeText(ConfirmOrderActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }else{

                        }
                        if (chk_wallet.isChecked()) {
                            wallet_amount=edt_wallet.getText().toString();
                            if (edt_wallet.getText().toString().length() < 1) {
                                Toast.makeText(ConfirmOrderActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        deliveryBoyConfirmOrder(MyApplication.get_delivery_id(), order_id, str_otp, status,cash_amount,wallet_amount );


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        startSMS_Tracking();
    }

    private void deliveryBoyConfirmOrder(String id, String order_id, String otp, String payment_by, String cash, String wallet) {
        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOrderActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseDeliveryBoyAcceptOrder> responseCall = apiService.deliveryBoyConfirmOrder(agro_item_id,id, order_id, otp, payment_by, getIntent().getStringExtra("subscription_id"), getIntent().getStringExtra("package_delivery_id"), MyApplication.vendor_sales_id, order_type, cash, wallet);


        responseCall.enqueue(new Callback<ResponseDeliveryBoyAcceptOrder>() {
            @Override
            public void onResponse(Call<ResponseDeliveryBoyAcceptOrder> call, Response<ResponseDeliveryBoyAcceptOrder> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    startActivity(new Intent(ConfirmOrderActivity.this, DeliveryDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseDeliveryBoyAcceptOrder> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(ConfirmOrderActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOrderActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseVerifyOtp> responseCall = apiService.vendor_verifyOtp(mobile, otp, is_rgister);


        responseCall.enqueue(new Callback<ResponseVerifyOtp>() {
            @Override
            public void onResponse(Call<ResponseVerifyOtp> call, Response<ResponseVerifyOtp> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    startActivity(new Intent(ConfirmOrderActivity.this, AddEditVegetableActivity.class));
//                    MyApplication.setIsLogin(true);
                    MyApplication.setMobile(mobile);
                    MyApplication.set_vendor_id(response.body().getData().getVendors_id());
                    finish();

                    MyApplication.setVendorIsLogin(true);
//                    startActivity(new Intent(ConfirmOrderActivity.this, AddEditVegetableActivity.class));
//                    finish();

                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ConfirmOrderActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void vendor_sendNewOtp(final String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOrderActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.vendor_sendNewOtp(mobile, is_rgister);


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Toast.makeText(ConfirmOrderActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ConfirmOrderActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
    }
}
