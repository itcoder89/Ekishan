package com.android.ekishan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.R;
import com.android.ekishan.adapter.ProductsListAdapter;
import com.android.ekishan.model.OrderDetailsData;
import com.android.ekishan.model.VendorTransactionDemandData;
import com.android.ekishan.model.VendorTransactionDetailData;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorTransactionDetailsActivity extends AppCompatActivity {
    List<OrderDetailsData.DataBean> collection_list;
    ApiInterface apiService;
    private TextView tvInvoiceNo,tvDate,tvCollectedBy;
    private TextView tvWallet,tvCash,tvTotal,tvHeaderName;
    private TextView tvProductName,particulars,tvStatus;
    private ImageView iv_back;
    String order_id = "";
    String status = "";
    private LinearLayout llDemandView;
    private RecyclerView recyclerview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_transaction_details_layout);
        getSupportActionBar().hide();
        order_id = getIntent().getStringExtra("order_id");
        status = getIntent().getStringExtra("status");
        llDemandView=(LinearLayout)findViewById(R.id.llDemandView);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        tvInvoiceNo=(TextView)findViewById(R.id.tvInvoiceNo);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvCollectedBy=(TextView)findViewById(R.id.tvCollectedBy);
        tvWallet=(TextView)findViewById(R.id.tvWallet);
        tvProductName=(TextView)findViewById(R.id.tvProductName);
        particulars=(TextView)findViewById(R.id.particulars);
        tvStatus=(TextView)findViewById(R.id.tvStatus);
        tvCash=(TextView)findViewById(R.id.tvCash);
        tvTotal=(TextView)findViewById(R.id.tvTotal);
        tvHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        tvStatus.setText(status);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDetails();
    }

    private void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(VendorTransactionDetailsActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Call<OrderDetailsData> responseCall = apiService.getCollectionOrderDetails(MyApplication.getCustomerId());
        Call<VendorTransactionDemandData> responseCall = apiService.transactionDetails1(order_id,status);


        responseCall.enqueue(new Callback<VendorTransactionDemandData>() {
            @Override
            public void onResponse(Call<VendorTransactionDemandData> call, Response<VendorTransactionDemandData> response) {
                progressDialog.dismiss();
                //if(status.equalsIgnoreCase("sale")){
                  //  recyclerview.setVisibility(View.VISIBLE);
                   // llDemandView.setVisibility(View.GONE);
                    /*tvInvoiceNo.setText(response.body().getData().getOrders_id() + "");
                    tvDate.setText(response.body().getData().getOrder_booking_date() + "");
                    tvCollectedBy.setText(response.body().getData().getDrop_name() + "");
                    tvWallet.setText(response.body().getData().getWallet_amount() + "");
                    tvCash.setText(response.body().getData().getCash_amount() + "");
                    tvTotal.setText(response.body().getData().getWallet_amount()+response.body().getData().getCash_amount()+ "");
                    tvProductName.setText(response.body().getData().getName() + "");
                    particulars.setText(response.body().getData().getQuantity()+" Pcs * "+response.body().getData().getPrice());
                    collection_list = (List<OrderDetailsData.DataBean>) response.body().getData();
                    RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(VendorTransactionDetailsActivity.this);
                    recyclerview.setLayoutManager(mLayoutManager10);
                    ProductsListAdapter productsListAdapter = new ProductsListAdapter(VendorTransactionDetailsActivity.this, collection_list);
                    recyclerview.setAdapter(productsListAdapter);*/
                //}else{
                   // recyclerview.setVisibility(View.GONE);
                    //llDemandView.setVisibility(View.VISIBLE);

                    tvInvoiceNo.setText(response.body().getData().getVendors_demands_id() + "");
                    tvDate.setText(response.body().getData().getOrder_booking_date() + "");
                    tvCollectedBy.setText(response.body().getData().getDrop_name() + "");

                    tvProductName.setText(response.body().getData().getName() + "");
                    particulars.setText(response.body().getData().getQuantity()+" * "+response.body().getData().getPrice());

                    int total_value=0;
                    total_value=(Integer.parseInt(response.body().getData().getQuantity()) * response.body().getData().getPrice());
                    tvWallet.setText(response.body().getData().getWallet_amount() + "");
                    tvCash.setText(response.body().getData().getCash_amount() + "");
                    //tvTotal.setText(Float.parseFloat(response.body().getData().getWallet_amount())+Float.parseFloat(response.body().getData().getCash_amount())+ "");
                    tvTotal.setText(total_value+ "");
                //}

            }

            @Override
            public void onFailure(Call<VendorTransactionDemandData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(VendorTransactionDetailsActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
