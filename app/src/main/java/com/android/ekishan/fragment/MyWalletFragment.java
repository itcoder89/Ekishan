package com.android.ekishan.fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.delivery.DeliveryDashboardActivity;
import com.android.ekishan.model.NotificationResponse;
import com.android.ekishan.model.ResponseVendorWallet;
import com.android.ekishan.model.VendorWalletData;
import com.android.ekishan.model.VendorWalletTransactionList;
import com.android.ekishan.payment.PaytmMerchantActivity;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.android.ekishan.vendor.fragment.VedorWalletFragment;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletFragment extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<VendorWalletTransactionList> order_list = new ArrayList<>();
    TextView noFound;
    ApiInterface apiService;
    TextView wallet;
    LinearLayout addMoney;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_wallet_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        noFound = root.findViewById(R.id.noFound);
        my_order_recycle = root.findViewById(R.id.my_order_recycle);
        wallet=(TextView)root.findViewById(R.id.wallet);
        addMoney=root.findViewById(R.id.addMoney);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        return root;
    }
    MyOrderListAdapter adapter;

    private void setAdapter() {

        adapter = new MyOrderListAdapter(getActivity(), order_list, this);
        my_order_recycle.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        getCustomerWallet();
    }

    private void getCustomerWallet() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseVendorWallet> responseCall = apiService.balanceTransaction(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ResponseVendorWallet>() {
            @Override
            public void onResponse(Call<ResponseVendorWallet> call, Response<ResponseVendorWallet> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    VendorWalletData model = response.body().getData();
                    wallet.setText(""+model.getBalance()+" Rs");
                    order_list.clear();
                    order_list=model.getTransactions();
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
            public void onFailure(Call<ResponseVendorWallet> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VendorWalletTransactionList> data;

        MyWalletFragment fragment;

        public MyOrderListAdapter(Context c, List<VendorWalletTransactionList> data, MyWalletFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment = fragment;
        }

        @Override
        public MyOrderListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_transaction_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            MyOrderListAdapter.CustomViewHolder viewHolder = new MyOrderListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyOrderListAdapter.CustomViewHolder holder, final int position) {
            holder.name.setText(data.get(position).getName());
            holder.txt_id.setText("Transaction ID:"+data.get(position).getTransaction_id());
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
}
