package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.ResponseContactUs;
import com.android.ekishan.model.ResponseVendorWallet;
import com.android.ekishan.model.VendorWalletData;

public class ContactUsFragement extends Fragment {
    ApiInterface apiService;
    TextView description, contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_layout, container, false);
        description = view.findViewById(R.id.description);
        contact = view.findViewById(R.id.contact);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        getOrders();
        return view;
    }

    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseContactUs> responseCall = apiService.contactUs();


        responseCall.enqueue(new Callback<ResponseContactUs>() {
            @Override
            public void onResponse(Call<ResponseContactUs> call, Response<ResponseContactUs> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        contact.setText("" + response.body().getData().get(0).getPhone());
                        description.setText("" + response.body().getData().get(0).getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseContactUs> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
