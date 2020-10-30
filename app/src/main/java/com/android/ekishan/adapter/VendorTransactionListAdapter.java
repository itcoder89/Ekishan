package com.android.ekishan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ekishan.R;
import com.android.ekishan.activity.VendorTransactionDetailsActivity;
import com.android.ekishan.activity.VendorTransactionSaleDetailsActivity;
import com.android.ekishan.model.CollectionsListData;
import com.android.ekishan.model.VendorTransactionListData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class VendorTransactionListAdapter extends RecyclerView.Adapter<VendorTransactionListAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    List<VendorTransactionListData.DataBean.TransactionsBean> collection_list;
    CollectionsListData.DataBean dataBean;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, name,txt_id,tvStatus,tvWeight,txt_amount;
        LinearLayout ll_view_single_post;
        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            name = (TextView) view.findViewById(R.id.name);
            txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            txt_id = (TextView) view.findViewById(R.id.txt_id);


            //tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            //tvWeight = (TextView) view.findViewById(R.id.tvWeight);
            ll_view_single_post = (LinearLayout) view.findViewById(R.id.ll_view_single_post);
        }
    }


    public VendorTransactionListAdapter(Activity activity, List<VendorTransactionListData.DataBean.TransactionsBean> collection_list) {
        this.activity = activity;
      //  this.context = context;
        this.collection_list = collection_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_wallet_transaction_list_item, parent, false);//member_item_layout

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(collection_list.get(position).getName()+"");
        holder.txt_id.setText("TransactionID: #"+collection_list.get(position).getId()+"");
        try {
            //String time = "06:06:30";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dt = sdf.parse(collection_list.get(position).getTransaction_date().getDate());

            // SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            String formatedTime = sdfs.format(dt);
            holder.date.setText(""+formatedTime);
        }catch (Exception e){e.printStackTrace();}
        //holder.tvCollectionDateTime.setText(collection_list.get(position).getCollection_date()+"");
       // holder.tvStatus.setText(collection_list.get(position).getStatus()+"");
       // holder.tvWeight.setText(activity.getString(R.string.weight_hint)+":"+collection_list.get(position).getWeight()+"");
        holder.txt_amount.setText(activity.getString(R.string.price_hint)+":"+collection_list.get(position).getPrice()+"");

        holder.ll_view_single_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(collection_list.get(position).getStatus().equalsIgnoreCase("demand")) {
                    Intent intent = new Intent(activity, VendorTransactionDetailsActivity.class);//VendorOrderDetailsActivity  ,
                    intent.putExtra("order_id", collection_list.get(position).getId() + "");
                    intent.putExtra("status", collection_list.get(position).getStatus() + "");
                    activity.startActivity(intent);
                }else{
                    Intent intent = new Intent(activity, VendorTransactionSaleDetailsActivity.class);//VendorOrderDetailsActivity  ,
                    intent.putExtra("order_id", collection_list.get(position).getId() + "");
                    intent.putExtra("status", collection_list.get(position).getStatus() + "");
                    activity.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return collection_list.size();
    }
}
