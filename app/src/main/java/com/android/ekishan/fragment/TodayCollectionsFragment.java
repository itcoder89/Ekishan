package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.TodayCollectionsListAdapter;
import com.android.ekishan.model.CollectionsListData;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ReponseProfile;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayCollectionsFragment extends Fragment {
    RecyclerView my_order_recycle;
    List<CollectionsListData.DataBean> collection_list;
    //CollectionsListData.DataBean dataBean;
    ApiInterface apiService;
    TextView noFound;
    TodayCollectionsListAdapter todayCollectionsListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order, container, false);
        my_order_recycle=(RecyclerView)view.findViewById(R.id.my_order_recycle);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getTodayCollections();
        return view;

    }

    private void getTodayCollections() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<CollectionsListData> responseCall = apiService.getVendotTodayColletionsList(MyApplication.getvendor_id());
        //Call<CollectionsListData> responseCall = apiService.getVendotTodayColletionsList("5");


        responseCall.enqueue(new Callback<CollectionsListData>() {
            @Override
            public void onResponse(Call<CollectionsListData> call, Response<CollectionsListData> response) {
                progressDialog.dismiss();

                collection_list=response.body().getData();
                RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(getActivity());
                my_order_recycle.setLayoutManager(mLayoutManager10);
                todayCollectionsListAdapter = new TodayCollectionsListAdapter(getActivity(),collection_list);
                my_order_recycle.setAdapter(todayCollectionsListAdapter);
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
