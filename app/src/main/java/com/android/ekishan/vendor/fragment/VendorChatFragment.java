package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.OrderDetailsFragment;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseOrder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 6/2/2020.
 */
public class VendorChatFragment  extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<OrderListModel> order_list = new ArrayList<>();
    ApiInterface apiService;
    TextView noFound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_order, container, false);
        noFound = view.findViewById(R.id.noFound);
        my_order_recycle = view.findViewById(R.id.my_order_recycle);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        apiService = ApiClient.getClient().create(ApiInterface.class);
        getOrders();
        setAdapter();
        return view;
    }

    MyOrderListAdapter adapter;

    private void setAdapter() {
        for (int i = 0; i < 10; i++) {
            OrderListModel model = new OrderListModel();
            model.setOrders_id("Tomato");
            order_list.add(model);
        }
        adapter = new MyOrderListAdapter(getActivity(), order_list, VendorChatFragment.this);
        my_order_recycle.setAdapter(adapter);
    }


    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseOrder> responseCall = apiService.getOrderList(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    order_list = response.body().getData();
                    if (order_list.size() > 0) {
                        noFound.setVisibility(View.GONE);
                        my_order_recycle.setVisibility(View.VISIBLE);
//                        setAdapter();
                    } else {
//                        noFound.setVisibility(View.VISIBLE);
//                        my_order_recycle.setVisibility(View.GONE);
                    }
                } else {
//                    noFound.setVisibility(View.VISIBLE);
//                    my_order_recycle.setVisibility(View.GONE);
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


    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<OrderListModel> data;

        VendorChatFragment fragment;

        public MyOrderListAdapter(Context c, List<OrderListModel> data, VendorChatFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment = fragment;
        }

        @Override
        public  CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
             CustomViewHolder viewHolder = new  CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final  CustomViewHolder holder, final int position) {
            if(position%2==0){
                holder.llleft.setVisibility(View.GONE);
                holder.rlright.setVisibility(View.VISIBLE);
            }else{
                holder.llleft.setVisibility(View.VISIBLE);
                holder.rlright.setVisibility(View.GONE);
            }
//            holder.order_no.setText(data.get(position).getOrders_id());
//            holder.dateTime.setText(data.get(position).getDate_purchased());

//            if (data.get(position).getOrder_status().equalsIgnoreCase("COMPLETED")||data.get(position).getOrder_status().equalsIgnoreCase("DELIVERED")){
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_correct));
//            }else {
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_stop));
//            }

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
            RelativeLayout llleft,rlright;

            public CustomViewHolder(View convertView) {
                super(convertView);
                llleft =  convertView.findViewById(R.id.llleft);
                rlright =   convertView.findViewById(R.id.rlright);

            }
        }
    }
}
