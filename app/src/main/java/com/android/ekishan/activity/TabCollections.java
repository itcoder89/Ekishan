package com.android.ekishan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.CollectionsListAdapter;
import com.android.ekishan.adapter.TodayCollectionsListAdapter;
import com.android.ekishan.model.CollectionsListData;
import com.android.ekishan.model.VendorCollectionListData;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabCollections extends AppCompatActivity {

    RecyclerView my_order_recycle;
    RecyclerView my_order_recycle2;
    List<VendorCollectionListData.DataBean> collection_list;
    List<CollectionsListData.DataBean> today_collection_list;
    ApiInterface apiService;
    TextView noFound;
    CollectionsListAdapter collectionsListAdapter;
    TodayCollectionsListAdapter todayCollectionsListAdapter;
    private TextView tvCollections,tvTodayCollections;
    ProgressBar progressBar;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_list_layout);
        getSupportActionBar().hide();
        iv_back=(ImageView)findViewById(R.id.iv_back);
        my_order_recycle=(RecyclerView)findViewById(R.id.my_order_recycle);
        my_order_recycle2=(RecyclerView)findViewById(R.id.my_order_recycle2);
        tvCollections=(TextView)findViewById(R.id.tvCollections);
        tvTodayCollections=(TextView)findViewById(R.id.tvTodayCollections);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getTodayCollections();
        tvCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_order_recycle.setVisibility(View.VISIBLE);
                my_order_recycle2.setVisibility(View.GONE);
                getCollections();
                tvCollections.setTextColor(getResources().getColor(R.color.white));
                tvTodayCollections.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tvTodayCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_order_recycle.setVisibility(View.GONE);
                my_order_recycle2.setVisibility(View.VISIBLE);
                tvCollections.setTextColor(getResources().getColor(R.color.black));
                tvTodayCollections.setTextColor(getResources().getColor(R.color.white));
                getTodayCollections();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    private void getCollections() {
        progressBar.setVisibility(View.VISIBLE);
       /* final ProgressDialog progressDialog = new ProgressDialog(TabCollections.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        Call<VendorCollectionListData> responseCall = apiService.getVendorColletionsList(MyApplication.getvendor_id());
        //Call<VendorCollectionListData> responseCall = apiService.getVendorColletionsList("2");


        responseCall.enqueue(new Callback<VendorCollectionListData>() {
            @Override
            public void onResponse(Call<VendorCollectionListData> call, Response<VendorCollectionListData> response) {
                progressBar.setVisibility(View.GONE);

                collection_list=response.body().getData();
                RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(TabCollections.this);
                my_order_recycle.setLayoutManager(mLayoutManager10);
                collectionsListAdapter = new CollectionsListAdapter(TabCollections.this,collection_list);
                my_order_recycle.setAdapter(collectionsListAdapter);
                collectionsListAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<VendorCollectionListData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TabCollections.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTodayCollections() {
        progressBar.setVisibility(View.VISIBLE);
        /*final ProgressDialog progressDialog = new ProgressDialog(TabCollections.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        Call<CollectionsListData> responseCall = apiService.getVendotTodayColletionsList(MyApplication.getvendor_id());
        //Call<CollectionsListData> responseCall = apiService.getVendotTodayColletionsList("5");


        responseCall.enqueue(new Callback<CollectionsListData>() {
            @Override
            public void onResponse(Call<CollectionsListData> call, Response<CollectionsListData> response) {
                progressBar.setVisibility(View.GONE);

                today_collection_list=response.body().getData();
                RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(TabCollections.this);
                my_order_recycle2.setLayoutManager(mLayoutManager10);
                todayCollectionsListAdapter = new TodayCollectionsListAdapter(TabCollections.this,today_collection_list);
                my_order_recycle2.setAdapter(todayCollectionsListAdapter);
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
            public void onFailure(Call<CollectionsListData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TabCollections.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
