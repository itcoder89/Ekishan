package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.CollectionsListAdapter;
import com.android.ekishan.adapter.SalesReportsListAdapter;
import com.android.ekishan.model.SalesReportListData;
import com.android.ekishan.model.VendorCollectionListData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReportFragment extends AppCompatActivity {
    RecyclerView my_order_recycle;
    List<SalesReportListData.DataBean> collection_list;
    ApiInterface apiService;
    TextView noFound;
    SalesReportsListAdapter collectionsListAdapter;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_req_list_layout);//my_order
        getSupportActionBar().hide();
        my_order_recycle=(RecyclerView)findViewById(R.id.my_order_recycle);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getTodayCollections();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order, container, false);
        my_order_recycle=(RecyclerView)view.findViewById(R.id.my_order_recycle);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getTodayCollections();
        return view;

    }*/

    private void getTodayCollections() {
        final ProgressDialog progressDialog = new ProgressDialog(SalesReportFragment.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<SalesReportListData> responseCall = apiService.getVendotSaleRequest(MyApplication.getvendor_id());
        //Call<SalesReportListData> responseCall = apiService.getVendorColletionsList("2");


        responseCall.enqueue(new Callback<SalesReportListData>() {
            @Override
            public void onResponse(Call<SalesReportListData> call, Response<SalesReportListData> response) {
                progressDialog.dismiss();

                collection_list=response.body().getData();
                RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(SalesReportFragment.this);
                my_order_recycle.setLayoutManager(mLayoutManager10);
                collectionsListAdapter = new SalesReportsListAdapter(SalesReportFragment.this,collection_list);
                my_order_recycle.setAdapter(collectionsListAdapter);
                /*if (response.body().getSuccess().equals("1")) {
                    model=response.body().getData();
                    setdata(model);
                }*/
//                    Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SalesReportListData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(SalesReportFragment.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
