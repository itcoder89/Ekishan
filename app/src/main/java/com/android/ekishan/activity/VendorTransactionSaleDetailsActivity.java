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

public class VendorTransactionSaleDetailsActivity extends AppCompatActivity {
    List<VendorTransactionDetailData.DataBean> collection_list;
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
        final ProgressDialog progressDialog = new ProgressDialog(VendorTransactionSaleDetailsActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Call<OrderDetailsData> responseCall = apiService.getCollectionOrderDetails(MyApplication.getCustomerId());
        Call<VendorTransactionDetailData> responseCall = apiService.transactionDetails(order_id,status);


        responseCall.enqueue(new Callback<VendorTransactionDetailData>() {
            @Override
            public void onResponse(Call<VendorTransactionDetailData> call, Response<VendorTransactionDetailData> response) {
                progressDialog.dismiss();
                    recyclerview.setVisibility(View.VISIBLE);
                    llDemandView.setVisibility(View.GONE);
                    tvInvoiceNo.setText(response.body().getData().get(0).getSales_request_id() + "");
                    tvDate.setText(response.body().getData().get(0).getOrder_booking_date() + "");
                    tvCollectedBy.setText(response.body().getData().get(0).getDrop_name() + "");



                    collection_list = (List<VendorTransactionDetailData.DataBean>) response.body().getData();
                    /*float total_amount = 0f;
                    for (int x=1;x < collection_list.size();x++) {
                        total_amount += (Float.parseFloat(collection_list.get(x).getPrice())*collection_list.get(x).getQuantity());
                    }*/
                float sum = 0f;

                for (int i = 0; i < collection_list.size(); i++) {

                        sum = sum + (Float.parseFloat(collection_list.get(i).getPrice())*collection_list.get(i).getQuantity());

                }
                Log.e("strBudgetVal", "sum: " + sum);

                    Log.e("SumofValue","total_amount "+sum);
                    float total_cash = 0f;
                    for (int x=1;x < collection_list.size();x++) {
                        total_cash += collection_list.get(x).getCash_amount();
                    }
                Log.e("SumofValue","total_cash "+total_cash);
                    float total_wallet = 0f;
                    for (int x=1;x < collection_list.size();x++) {
                        total_wallet += collection_list.get(x).getWallet_amount();
                    }
                Log.e("SumofValue","total_wallet "+total_wallet);
                    tvCash.setText(total_cash + "");
                    tvWallet.setText(total_wallet + "");


                   // tvTotal.setText(total_cash+total_wallet+ "");
                    tvTotal.setText(sum+ "");

                    RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(VendorTransactionSaleDetailsActivity.this);
                    recyclerview.setLayoutManager(mLayoutManager10);
                    ProductsListAdapter productsListAdapter = new ProductsListAdapter(VendorTransactionSaleDetailsActivity.this, collection_list);
                    recyclerview.setAdapter(productsListAdapter);


            }

            @Override
            public void onFailure(Call<VendorTransactionDetailData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(VendorTransactionSaleDetailsActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
