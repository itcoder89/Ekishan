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
import com.android.ekishan.activity.CollectionDetailsActivityNew;
import com.android.ekishan.model.CollectionsListData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class TodayCollectionsListAdapter extends RecyclerView.Adapter<TodayCollectionsListAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    List<CollectionsListData.DataBean> collection_list;
    CollectionsListData.DataBean dataBean;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCollectionDateTime,tvTransactionDate, tvProductName,tvStatus,tvWeight,tvPrice;
        LinearLayout ll_view_single_post;
        public MyViewHolder(View view) {
            super(view);
            tvCollectionDateTime = (TextView) view.findViewById(R.id.tvCollectionDateTime);
            tvTransactionDate = (TextView) view.findViewById(R.id.tvTransactionDate);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvWeight = (TextView) view.findViewById(R.id.tvWeight);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ll_view_single_post = (LinearLayout) view.findViewById(R.id.ll_view_single_post);
        }
    }


    public TodayCollectionsListAdapter(Activity activity, List<CollectionsListData.DataBean> collection_list) {
        this.activity = activity;
      //  this.context = context;
        this.collection_list = collection_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.experience_item_layout, parent, false);//member_item_layout

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvProductName.setText(collection_list.get(position).getName()+"");
        try {
            //String time = "06:06:30";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dt = sdf.parse(collection_list.get(position).getCollection_date());

            // SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            String formatedTime = sdfs.format(dt);
            holder.tvTransactionDate.setText(""+formatedTime);
        }catch (Exception e){e.printStackTrace();}
        //holder.tvCollectionDateTime.setText(collection_list.get(position).getCollection_date()+"");
        holder.tvStatus.setText(collection_list.get(position).getStatus()+"");
        holder.tvWeight.setText(activity.getString(R.string.weight_hint)+":"+collection_list.get(position).getWeight()+"");
        holder.tvPrice.setText(activity.getString(R.string.price_hint)+":"+collection_list.get(position).getPrice()+"");

        holder.ll_view_single_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(activity, CollectionDetailsActivityNew.class);//CollectionDetailsActivityNew ,CollectionDetailsActivity
                intent.putExtra("order_id",collection_list.get(position).getId()+"");
                intent.putExtra("status",collection_list.get(position).getStatus()+"");
                activity.startActivity(intent);*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return collection_list.size();
    }
}
