package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.OrderDetailsFragment;
import com.android.ekishan.fragment.OrderSuccesFragment;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.model.VendorCollectionList;
import com.android.ekishan.model.VendorResponseCollection;
import com.android.ekishan.payment.PaytmMerchantActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Demand extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<VendorCollectionList> order_list=new ArrayList<>();
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

        adapter = new MyOrderListAdapter(getActivity(), order_list, Demand.this);
        my_order_recycle.setAdapter(adapter);
    }


    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<VendorResponseCollection> responseCall = apiService.getDemandsList(MyApplication.getvendor_id());
      //  Call<VendorResponseCollection> responseCall = apiService.getDemandsList("3");


        responseCall.enqueue(new Callback<VendorResponseCollection>() {
            @Override
            public void onResponse(Call<VendorResponseCollection> call, Response<VendorResponseCollection> response) {
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


    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VendorCollectionList> data;

        Demand fragment;
        public MyOrderListAdapter(Context c, List<VendorCollectionList> data, Demand fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public  CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_demand_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new  CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final  CustomViewHolder holder, final int position) {
            holder.txt_price.setText(data.get(position).getPrice()+" Rs");

            try {
                //String time = "06:06:30";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date dt = sdf.parse(data.get(position).getCreated_at());

                // SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                String formatedTime = sdfs.format(dt);
                holder.txt_created.setText("Date:"+formatedTime);
            }catch (Exception e){e.printStackTrace();}


            holder.txt_qty.setText(data.get(position).getQuantity());
            holder.order_no.setText(data.get(position).getProducts_name());
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Please Enter Available Quantity");
                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 50);
                    input.setHint("Ex. 1KG");
                    input.setLayoutParams(lp);
//                    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!input.getText().toString().equalsIgnoreCase("")&&!input.getText().toString().equalsIgnoreCase("0")) {
                                        acceptDeclineDemand(data.get(position).getVendors_demands_id(),1,input.getText().toString(),"");
                                    }else {
                                        Toast.makeText(getActivity(), "Please enter valid quantity", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
//                    Toast.makeText(mContext, "Demand Accepted Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            holder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Please Enter Reason For Decline");
                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 50);
                    input.setLayoutParams(lp);
//                    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!input.getText().toString().equalsIgnoreCase("")&&!input.getText().toString().equalsIgnoreCase("0")) {
                                        acceptDeclineDemand(data.get(position).getVendors_demands_id(),0,"",input.getText().toString());
                                    }else {
                                        Toast.makeText(getActivity(), "Please enter reason", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
//                    Toast.makeText(mContext, "Demand declined Successfully", Toast.LENGTH_SHORT).show();
                }
            });
//            if (data.get(position).getOrder_status().equalsIgnoreCase("COMPLETED")||data.get(position).getOrder_status().equalsIgnoreCase("DELIVERED")){
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_correct));
//            }else {
//                holder.llicon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_stop));
//            }

        }
        private void acceptDeclineDemand(String demand_id,int accept_status,String available_weight,String decline_comment) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.acceptDeclineDemand(MyApplication.getvendor_id(), String.valueOf(accept_status),demand_id,available_weight,decline_comment);


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        if (response!=null) {
                            String res = response.body().string();
                            JSONObject jsonObject = new JSONObject(res);
                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            getOrders();
                        }else {
                            Toast.makeText(mContext, "Something Wrong. Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                    noFound.setVisibility(View.VISIBLE);
                    my_order_recycle.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
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
            LinearLayout llLayout;
            TextView txt_price,txt_qty,order_no,txt_created;
            Button accept,decline;
              public CustomViewHolder(View convertView) {
                super(convertView);
                 llLayout=(LinearLayout)convertView.findViewById(R.id.llLayout);
                  txt_created=(TextView) convertView.findViewById(R.id.txt_created);
                  txt_price=(TextView) convertView.findViewById(R.id.txt_price);
                  txt_qty=(TextView)convertView.findViewById(R.id.txt_qty);
                  order_no=(TextView)convertView.findViewById(R.id.order_no);
                  accept=(Button)convertView.findViewById(R.id.accept);
                  decline=(Button)convertView.findViewById(R.id.decline);

            }
        }
    }
}
