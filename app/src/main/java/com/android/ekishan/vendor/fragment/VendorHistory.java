package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.OrderDetailsFragment;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.model.VendorCollectionList;
import com.android.ekishan.model.VendorResponseCollection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${} on 5/31/2020.
 */
public class VendorHistory extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<VendorCollectionList> order_list = new ArrayList<>();
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
        return view;
    }

    MyOrderListAdapter adapter;

    private void setAdapter() {

        adapter = new MyOrderListAdapter(getActivity(), order_list, VendorHistory.this);
        my_order_recycle.setAdapter(adapter);
    }


    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<VendorResponseCollection> responseCall = apiService.getDemandsHistory(MyApplication.getvendor_id());


        responseCall.enqueue(new Callback<VendorResponseCollection>() {
            @Override
            public void onResponse(Call<VendorResponseCollection> call, Response<VendorResponseCollection> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    order_list = response.body().getData();
                    if (order_list.size() > 0) {
                        noFound.setVisibility(View.GONE);
                        my_order_recycle.setVisibility(View.VISIBLE);
                        setAdapter();
                    } else {
                        noFound.setVisibility(View.VISIBLE);
                        my_order_recycle.setVisibility(View.GONE);
                    }
                } else {
                    noFound.setVisibility(View.VISIBLE);
                    my_order_recycle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<VendorResponseCollection> call, Throwable t) {
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
        List<VendorCollectionList> data;

        VendorHistory fragment;

        public MyOrderListAdapter(Context c, List<VendorCollectionList> data, VendorHistory fragment) {
            mContext = c;
            this.data = data;
            this.fragment = fragment;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_history_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            holder.txt_qty.setText(data.get(position).getQuantity());
            holder.txt_price.setText(data.get(position).getPrice() +" Rs");

            try {
                //String time = "06:06:30";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date dt = sdf.parse(data.get(position).getCreated_at());

                // SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                String formatedTime = sdfs.format(dt);
                holder.txt_created.setText("Date:"+formatedTime);
            }catch (Exception e){e.printStackTrace();}

            holder.order_no.setText(data.get(position).getProducts_name() );
//            if (data.get(position).getOrder_status().equalsIgnoreCase("COMPLETED")||data.get(position).getOrder_status().equalsIgnoreCase("DELIVERED")){
//            if (data.get(position).getOrder_status().equalsIgnoreCase("COMPLETED")||data.get(position).getOrder_status().equalsIgnoreCase("DELIVERED")){
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_correct));
//            }else {
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_stop));
//            }
            holder.accept_status.setText(data.get(position).getAccept_status());

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
            LinearLayout llLayout;
            TextView txt_price, txt_qty,order_no,txt_created;
            Button accept_status;
            public CustomViewHolder(View convertView) {
                super(convertView);
                llLayout = (LinearLayout) convertView.findViewById(R.id.llLayout);
                txt_price = (TextView) convertView.findViewById(R.id.txt_price);
                txt_qty = (TextView) convertView.findViewById(R.id.txt_qty);
                order_no = (TextView) convertView.findViewById(R.id.order_no);
                txt_created = (TextView) convertView.findViewById(R.id.txt_created);
                accept_status = (Button) convertView.findViewById(R.id.accept_status);

            }
        }
    }
}
