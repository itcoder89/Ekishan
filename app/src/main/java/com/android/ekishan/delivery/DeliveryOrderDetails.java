package com.android.ekishan.delivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.DeliverDashboarResponse;
import com.android.ekishan.model.DeliveryOrderItemsResponse;
import com.android.ekishan.model.DeliveryOrderitems;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseDeliveryBoyAcceptOrder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 6/10/2020.
 */
public class DeliveryOrderDetails extends AppCompatActivity {
    RecyclerView Recycler;
    List<DeliveryOrderitems> data=new ArrayList<>();
    RelativeLayout confirm,decline;
    String name,mobile;
    TextView nameTxt,mobileTxt;
    ApiInterface apiService;
    String order_id = "";
    String agro_item_id = "";
    String vendor_sales_id = "";
    String id = "";
    String order_type="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.delivery_order_details);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        name=getIntent().getStringExtra("name");
        mobile=getIntent().getStringExtra("mobile");
        id=getIntent().getStringExtra("id");
        order_id=getIntent().getStringExtra("order_id");
        agro_item_id=getIntent().getStringExtra("agro_item_id");
        confirm=findViewById(R.id.confirm);
        decline=findViewById(R.id.decline);
        Recycler=findViewById(R.id.Recycler);
        nameTxt=findViewById(R.id.nameTxt);
        mobileTxt=findViewById(R.id.mobileTxt);
        nameTxt.setText(name);
        mobileTxt.setText(mobile);
        order_type=getIntent().getStringExtra("order_type");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ConfirmOrderActivity.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("agro_item_id",agro_item_id);
                intent.putExtra("subscription_id", getIntent().getStringExtra("subscription_id"));
                intent.putExtra("package_delivery_id", getIntent().getStringExtra("package_delivery_id"));
                intent.putExtra("payment_status", getIntent().getStringExtra("payment_status"));
                intent.putExtra("order_type", getIntent().getStringExtra("order_type"));
                startActivity(intent);
                finish();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryOrderDetails.this);
                alertDialog.setMessage("Enter Reason");
                final EditText input = new EditText(DeliveryOrderDetails.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String reason = input.getText().toString();
                                deliveryBoyAcceptDeclineOrder( MyApplication.get_delivery_id(),"0",order_id,reason);

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
        Recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        deliveryBoyOrderItems(id,order_id);
    }
    private void deliveryBoyAcceptDeclineOrder(String id, String resp, String order_id, String reason) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryOrderDetails.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseDeliveryBoyAcceptOrder> responseCall = apiService.deliveryBoyAcceptDeclineOrder(agro_item_id,id, resp, order_id,getIntent().getStringExtra("subscription_id"),getIntent().getStringExtra("package_delivery_id"), reason,MyApplication.vendor_sales_id,order_type);


        responseCall.enqueue(new Callback<ResponseDeliveryBoyAcceptOrder>() {
            @Override
            public void onResponse(Call<ResponseDeliveryBoyAcceptOrder> call, Response<ResponseDeliveryBoyAcceptOrder> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    Toast.makeText(DeliveryOrderDetails.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DeliveryOrderDetails.this,DeliveryDashboardActivity.class));
                } else {
                    Toast.makeText(DeliveryOrderDetails.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDeliveryBoyAcceptOrder> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryOrderDetails.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deliveryBoyOrderItems(String id, String order_id) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryOrderDetails.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<DeliveryOrderItemsResponse> responseCall = apiService.deliveryBoyOrderItems(agro_item_id,
                MyApplication.get_delivery_id(),
                order_id,getIntent().getStringExtra("subscription_id"),
                getIntent().getStringExtra("package_delivery_id"),
                order_type,MyApplication.vendor_sales_id);


        responseCall.enqueue(new Callback<DeliveryOrderItemsResponse>() {
            @Override
            public void onResponse(Call<DeliveryOrderItemsResponse> call, Response<DeliveryOrderItemsResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    MyApplication.collect_amount=response.body().getData().getCollect_amount();
                    data=response.body().getData().getOrders();
                    setAdapter();
                } else {
                    Toast.makeText(DeliveryOrderDetails.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeliveryOrderItemsResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryOrderDetails.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    MyOrderListAdapter adapter;

    private void setAdapter() {
        adapter = new MyOrderListAdapter(getApplication(), data);
        Recycler.setAdapter(adapter);
    }
    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<DeliveryOrderitems> data;

        public MyOrderListAdapter(Context c, List<DeliveryOrderitems> data) {
            mContext = c;
            this.data = data;
        }

        @Override
        public MyOrderListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delivery_order_detail_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            MyOrderListAdapter.CustomViewHolder viewHolder = new MyOrderListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyOrderListAdapter.CustomViewHolder holder, final int position) {
            holder.txt_name.setText(data.get(position).getProducts_name());
            holder.unit.setText(data.get(position).getProducts_quantity());
            holder.txt_price.setText(data.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name,txt_price,unit;

            public CustomViewHolder(View convertView) {
                super(convertView);
                txt_name=convertView.findViewById(R.id.txt_name);
                txt_price=convertView.findViewById(R.id.txt_price);
                unit=convertView.findViewById(R.id.unit);
            }

        }
    }
}
