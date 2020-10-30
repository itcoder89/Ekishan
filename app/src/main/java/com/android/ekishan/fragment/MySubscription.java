package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.LoginActivity;
import com.android.ekishan.activity.OtpActivity;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseLogin;
import com.android.ekishan.model.ResponseOrder;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySubscription extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<OrderListModel> order_list=new ArrayList<>();
    ApiInterface apiService;
    TextView noFound;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order, container, false);
        noFound=view.findViewById(R.id.noFound);
        my_order_recycle=view.findViewById(R.id.my_order_recycle);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getOrders();
        return view;
    }
    MyOrderListAdapter adapter;

    private void setAdapter() {
        adapter = new MyOrderListAdapter(getActivity(), order_list,MySubscription.this);
        my_order_recycle.setAdapter(adapter);
    }


    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseOrder> responseCall = apiService.getMySubscription(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    order_list=response.body().getData();
                    if (order_list.size()>0) {
                        noFound.setVisibility(View.GONE);
                        my_order_recycle.setVisibility(View.VISIBLE);
                        setAdapter();
                    }else {
                        noFound.setVisibility(View.VISIBLE);
                        my_order_recycle.setVisibility(View.GONE);
                    }
                }else {
                    noFound.setVisibility(View.VISIBLE);
                    my_order_recycle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                noFound.setVisibility(View.VISIBLE);
                my_order_recycle.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //


    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<OrderListModel> data;


        MySubscription fragment;

        public MyOrderListAdapter(Context c, List<OrderListModel> data,MySubscription fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public  CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new  CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final  CustomViewHolder holder, final int position) {
            holder.txt_order.setText("Package No. ");
            holder.order_no.setText(data.get(position).getSubscription_id());
            holder.dateTime.setText(data.get(position).getPreferred_delivery_date());
            holder.itemCount.setText(data.get(position).getItems());
            holder.price.setText(data.get(position).getTotal());
            holder.status.setText(data.get(position).getStatus());
            if (data.get(position).getStatus().equalsIgnoreCase("COMPLETED")||data.get(position).getStatus().equalsIgnoreCase("CONFIRM")||data.get(position).getStatus().equalsIgnoreCase("DELIVERED")){
                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_correct));
            }else {
                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_stop));
            }

            holder.llLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof OrderDetailsFragment)) {
                        MyApplication.subscription_id=data.get(position).getSubscription_id();
                        MyApplication.isFrom="SUBSCRIPTION";
                        loadFragment(new OrderDetailsFragment());
                    }
                }
            });
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
        @Override
        public int getItemCount() {
            return data.size();
        }



        public class CustomViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llLayout,llicon;
            TextView order_no,dateTime;
            TextView itemCount,price;
            TextView status,txt_order;
            public CustomViewHolder(View convertView) {
                super(convertView);
                llicon=(LinearLayout)convertView.findViewById(R.id.llicon);
                llLayout=(LinearLayout)convertView.findViewById(R.id.llLayout);
                order_no=(TextView) convertView.findViewById(R.id.order_no);
                dateTime=(TextView)convertView.findViewById(R.id.dateTime);
                itemCount=(TextView)convertView.findViewById(R.id.itemCount);
                price=(TextView)convertView.findViewById(R.id.price);
                status=(TextView)convertView.findViewById(R.id.status);
                txt_order=(TextView)convertView.findViewById(R.id.txt_order);
            }

        }






    }


}
