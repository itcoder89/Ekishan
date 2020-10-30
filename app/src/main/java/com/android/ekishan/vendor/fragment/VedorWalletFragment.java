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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.TodayCollectionsListAdapter;
import com.android.ekishan.adapter.VendorTransactionListAdapter;
import com.android.ekishan.fragment.OrderDetailsFragment;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.model.ResponseVendorWallet;
import com.android.ekishan.model.VendorTransactionListData;
import com.android.ekishan.model.VendorWalletData;
import com.android.ekishan.model.VendorWalletTransactionList;
import com.android.ekishan.payment.PaytmMerchantActivity;

import java.util.ArrayList;
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

/**
 * Created by ${} on 6/2/2020.
 */
public class VedorWalletFragment extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<VendorWalletTransactionList> order_list = new ArrayList<>();
    ApiInterface apiService;
    TextView noFound;
    TextView name,txt_id,txt_wallet;
    TextView addMoney;
    LinearLayout byCash,byBank;
    List<VendorTransactionListData.DataBean.TransactionsBean> vendorTransactionList;
    VendorTransactionListAdapter vendorTransactionListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_wallet_fragment, container, false);
        noFound = view.findViewById(R.id.noFound);
        my_order_recycle = view.findViewById(R.id.my_order_recycle);
        name = view.findViewById(R.id.name);
        txt_id = view.findViewById(R.id.txt_id);
        byCash = view.findViewById(R.id.byCash);
        byBank = view.findViewById(R.id.byBank);
        txt_wallet = view.findViewById(R.id.txt_wallet);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        apiService = ApiClient.getClient().create(ApiInterface.class);
        addMoney=view.findViewById(R.id.addMoney);
        byCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Enter Amount");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 50);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                withdrawRequest(input.getText().toString(),"cash");
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
        byBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Enter Amount");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 50);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                withdrawRequest(input.getText().toString(),"bank");
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
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Enter Amount");
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 50);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!input.getText().toString().equalsIgnoreCase("")&&!input.getText().toString().equalsIgnoreCase("0")) {
                                    Intent intent = new Intent(getActivity(), PaytmMerchantActivity.class);
                                    intent.putExtra("amount", input.getText().toString());
                                    intent.putExtra("payment_type", "WALLET");
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getActivity(), "Please enter valid amount", Toast.LENGTH_SHORT).show();
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
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();
    }

    MyOrderListAdapter adapter;

    private void setAdapter() {

        adapter = new MyOrderListAdapter(getActivity(), order_list, VedorWalletFragment.this);
        my_order_recycle.setAdapter(adapter);
    }


    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseVendorWallet> responseCall = apiService.vendorBalanceTransaction(MyApplication.getvendor_id());


        responseCall.enqueue(new Callback<ResponseVendorWallet>() {
            @Override
            public void onResponse(Call<ResponseVendorWallet> call, Response<ResponseVendorWallet> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    VendorWalletData model = response.body().getData();
                    name.setText(""+MyApplication.getMobile());
                    txt_id.setText("User ID: "+model.getVendors_id());
                    txt_wallet.setText(""+model.getBalance());
                    //order_list.clear();
                    getVendorTransactionListAPI();
                    /*order_list=model.getTransactions();
                    if (order_list.size() > 0) {
                        noFound.setVisibility(View.GONE);
                        my_order_recycle.setVisibility(View.VISIBLE);
                        //setAdapter();

                    } else {
                        noFound.setVisibility(View.VISIBLE);
                        my_order_recycle.setVisibility(View.GONE);
                    }*/
                } else {
                    noFound.setVisibility(View.VISIBLE);
                    my_order_recycle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseVendorWallet> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                noFound.setVisibility(View.VISIBLE);
                my_order_recycle.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVendorTransactionListAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<VendorTransactionListData> responseCall = apiService.vendorTransaction(MyApplication.getvendor_id());
       // Call<VendorTransactionListData> responseCall = apiService.vendorTransaction("15");


        responseCall.enqueue(new Callback<VendorTransactionListData>() {
            @Override
            public void onResponse(Call<VendorTransactionListData> call, Response<VendorTransactionListData> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    vendorTransactionList=response.body().getData().getTransactions();
                   if (vendorTransactionList.size() > 0) {
                        noFound.setVisibility(View.GONE);
                        my_order_recycle.setVisibility(View.VISIBLE);
                       RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(getActivity());
                       my_order_recycle.setLayoutManager(mLayoutManager10);
                       vendorTransactionListAdapter = new VendorTransactionListAdapter(getActivity(),vendorTransactionList);
                       my_order_recycle.setAdapter(vendorTransactionListAdapter);
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
            public void onFailure(Call<VendorTransactionListData> call, Throwable t) {
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
        List<VendorWalletTransactionList> data;

        VedorWalletFragment fragment;

        public MyOrderListAdapter(Context c, List<VendorWalletTransactionList> data, VedorWalletFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment = fragment;
        }

        @Override
        public  CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendor_wallet_transaction_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
             CustomViewHolder viewHolder = new  CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final  CustomViewHolder holder, final int position) {
            holder.name.setText(data.get(position).getName());
            holder.txt_id.setText("Transaction ID:"+data.get(position).getVendors_transaction_id());
            holder.txt_amount.setText(data.get(position).getAmount()+ " Rs");
            String str[]=data.get(position).getCreated_at().split(" ");
            holder.date.setText(str[0]);

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
            TextView name,txt_id,txt_amount,date;

            public CustomViewHolder(View convertView) {
                super(convertView);
                name =  convertView.findViewById(R.id.name);
                txt_id = (TextView) convertView.findViewById(R.id.txt_id);
                txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
                date = (TextView) convertView.findViewById(R.id.date);

            }
        }
    }
    private void withdrawRequest(String amount,String mode) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.requestAmount(MyApplication.getvendor_id(), amount,mode);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response!=null) {
                        String res = response.body().string();
                        JSONObject jsonObject = new JSONObject(res);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Something Wrong. Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
