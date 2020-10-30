package com.android.ekishan.delivery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.DBHistoryListAdapter;
import com.android.ekishan.adapter.ProductsListAdapter;
import com.android.ekishan.delivery.model.DBHistoryListData;
import com.android.ekishan.model.DeliverDashboarResponse;
import com.android.ekishan.model.NewDeliveryBoyData;
import com.android.ekishan.model.OrderDetailsData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryBoyHistoryActivity extends AppCompatActivity {
    List<DBHistoryListData.DataBean> collection_list;
    List<NewDeliveryBoyData.DataBean> newdata = new ArrayList<>();
    ApiInterface apiService;
    private ImageView iv_back;
    String order_id = "";
    private RecyclerView my_order_recycle;
    DBHistoryListAdapter dbHistoryListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        getSupportActionBar().hide();
        my_order_recycle=(RecyclerView)findViewById(R.id.my_order_recycle);

        ///tvHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        newdata.clear();
    }

    private void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryBoyHistoryActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Call<OrderDetailsData> responseCall = apiService.getCollectionOrderDetails(MyApplication.getCustomerId());
        Call<DBHistoryListData> responseCall = apiService.deliveryBoyHistoryList(MyApplication.get_delivery_id(),MyApplication.get_delivery_type());


        responseCall.enqueue(new Callback<DBHistoryListData>() {
            @Override
            public void onResponse(Call<DBHistoryListData> call, Response<DBHistoryListData> response) {
                progressDialog.dismiss();
                collection_list=response.body().getData();
                NewDeliveryBoyData.DataBean newDeliveryBoyData= new NewDeliveryBoyData.DataBean();
                for(int i=0;i<collection_list.size();i++){

                    if(collection_list.get(i).getOrder_status().equals("Delivered")){
                        newDeliveryBoyData= new NewDeliveryBoyData.DataBean();
                        newDeliveryBoyData.setVendors_sales_id(collection_list.get(i).getVendors_sales_id());
                        newDeliveryBoyData.setOrders_date(collection_list.get(i).getOrders_date());
                        newDeliveryBoyData.setPayment_status(collection_list.get(i).getPayment_status());
                        newDeliveryBoyData.setOrder_status(collection_list.get(i).getOrder_status());
                        newDeliveryBoyData.setName(collection_list.get(i).getName());
                        newDeliveryBoyData.setLat(collection_list.get(i).getLat());
                        newDeliveryBoyData.setLongX(collection_list.get(i).getLongX());
                        newDeliveryBoyData.setMobile(collection_list.get(i).getMobile());
                        newDeliveryBoyData.setSubscription_id(collection_list.get(i).getSubscription_id());
                        newDeliveryBoyData.setPackage_delivery_id(collection_list.get(i).getPackage_delivery_id());
                        newDeliveryBoyData.setOrders_id(collection_list.get(i).getOrders_id());
                        newDeliveryBoyData.setOrder_type(collection_list.get(i).getOrder_type());
                        newDeliveryBoyData.setAgro_item_id(collection_list.get(i).getAgro_item_id());
                        newdata.add(newDeliveryBoyData);
                    }

                }
                Log.e("newarraysize","size "+newdata.size());
                RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(DeliveryBoyHistoryActivity.this);
                my_order_recycle.setLayoutManager(mLayoutManager10);
                dbHistoryListAdapter = new DBHistoryListAdapter(DeliveryBoyHistoryActivity.this,newdata);
                my_order_recycle.setAdapter(dbHistoryListAdapter);
                dbHistoryListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<DBHistoryListData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryBoyHistoryActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
