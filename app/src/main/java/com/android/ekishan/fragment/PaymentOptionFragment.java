package com.android.ekishan.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.payment.PaytmMerchantActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentOptionFragment extends Fragment {
    LinearLayout placeOrder;
    RadioButton walletRd,paytmRd,cashRd;
    ApiInterface apiService;
    TextView wallet,total_order_amount;
    LinearLayout pay_now,addWallet;
    String wallet_amount;
    Dialog dialog;
    EditText edt_mobile;
    EditText edt_otp;
    TextView tvLogin;
    LinearLayout ll_otp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.payment_option_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        pay_now=(LinearLayout) root.findViewById(R.id.pay_now);
        wallet=(TextView)root.findViewById(R.id.wallet);
        placeOrder=root.findViewById(R.id.placeOrder);
        walletRd=root.findViewById(R.id.walletRd);
        paytmRd=root.findViewById(R.id.paytmRd);
        cashRd=root.findViewById(R.id.cashRd);
        addWallet=root.findViewById(R.id.addWallet);
        total_order_amount=root.findViewById(R.id.total_order_amount);
        total_order_amount.setText("Rs "+MyApplication.totalprice);
        walletRd.setChecked(true);
        addWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Enter Amount");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 50);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!input.getText().toString().equalsIgnoreCase("")&&!input.getText().toString().equalsIgnoreCase("0")) {
                                    Intent intent = new Intent(getActivity(), PaytmMerchantActivity.class);
                                    intent.putExtra("amount", input.getText().toString());
                                    intent.putExtra("payment_type", "WALLET");
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getActivity(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getMobile().equalsIgnoreCase("")){
                    verifyMobileDialog();
                    return;
                }
                if (Double.parseDouble(wallet_amount)>Double.parseDouble(MyApplication.totalprice)) {
                    placeOrder("WALLET");
                }else {
                    Toast.makeText(getActivity(), "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getMobile().equalsIgnoreCase("")){
                    verifyMobileDialog();
                    return;
                }
                if (walletRd.isChecked()){
                    if (Double.parseDouble(wallet_amount)>Double.parseDouble(MyApplication.totalprice)) {
                        placeOrder("WALLET");
                    }else {
                        Toast.makeText(getActivity(), "Not enough balance!", Toast.LENGTH_SHORT).show();
                    }
                }else if (paytmRd.isChecked()){
                    Intent intent = new Intent(getActivity(), PaytmMerchantActivity.class);
                    intent.putExtra("amount", MyApplication.totalprice);
                    intent.putExtra("payment_type", "PAYTM");
                    startActivity(intent);
                }else {
                    placeOrder("CASH");
                }
            }
        });
        walletRd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletRd.setChecked(true);
                paytmRd.setChecked(false);
                cashRd.setChecked(false);
            }
        });
        paytmRd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletRd.setChecked(false);
                paytmRd.setChecked(true);
                cashRd.setChecked(false);
            }
        });
        cashRd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletRd.setChecked(false);
                paytmRd.setChecked(false);
                cashRd.setChecked(true);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.jsonObject1!=null){
            try {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof OrderSuccesFragment)) {
                    loadFragment(new OrderSuccesFragment(MyApplication.jsonObject1.getString("orders_id"),MyApplication.jsonObject1.getString("order_date"),MyApplication.jsonObject1.getString("order_price"),MyApplication.jsonObject1.getString("total_items")));
                    MyApplication.jsonObject1=null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            getCustomerWallet();
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    private void getCustomerWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.getCustomerWallet(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        JSONObject jsonObject2=new JSONObject(jsonObject.getString("data"));
                        wallet_amount=jsonObject2.getString("wallet");
                        wallet.setText("Rs "+jsonObject2.getString("wallet"));
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void placeOrder(String payment_type) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.placeOrder(MyApplication.getCustomerId(),"0",MyApplication.totalprice,MyApplication.shippingCost,"0","0",MyApplication.addressId,payment_type,MyApplication.date,MyApplication.selectedTimeSlot,"0");


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof OrderSuccesFragment)) {
                            loadFragment(new OrderSuccesFragment(jsonObject1.getString("orders_id"),jsonObject1.getString("order_date"),jsonObject1.getString("order_price"),jsonObject1.getString("total_items")));
                        }
                    }else {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void verifyMobileDialog(){
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.verfiy_mobile_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        RelativeLayout rlLogin=dialog.findViewById(R.id.rlLogin);
        RelativeLayout cancel_action=dialog.findViewById(R.id.cancel_action);
        ll_otp=dialog.findViewById(R.id.ll_otp);
        edt_mobile=dialog.findViewById(R.id.edt_mobile);
        edt_otp=dialog.findViewById(R.id.edt_otp);
        tvLogin=dialog.findViewById(R.id.tvLogin);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_mobile.getText().toString().equalsIgnoreCase("")&&edt_mobile.getText().toString().length()==10){
                    if (ll_otp.getVisibility()==View.GONE) {
                        edt_mobile.setEnabled(false);
                        sendOtpSocialVerify(edt_mobile.getText().toString());
                    }else {
                        if (!edt_otp.getText().toString().equalsIgnoreCase("")){
                            verifyOtpSocialVerify(edt_mobile.getText().toString(),edt_otp.getText().toString());
                        }else {
                            edt_otp.setError("Please enter valid otp");
                        }
                    }
                }else {
                    edt_mobile.setError("Please enter valid number");
                }
            }
        });
        dialog.show();
    }
    private void sendOtpSocialVerify(String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.sendOtpSocialVerify(MyApplication.getCustomerId(),mobile);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        ll_otp.setVisibility(View.VISIBLE);
                        tvLogin.setText("Submit");
                    }else {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void verifyOtpSocialVerify(String mobile,String otp) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.verifyOtpSocialVerify(MyApplication.getCustomerId(),mobile,otp);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                       if (dialog!=null){
                           dialog.cancel();
                           MyApplication.setMobile(mobile);
                           Toast.makeText(getActivity(), "Mobile verified successfully", Toast.LENGTH_SHORT).show();
                       }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
