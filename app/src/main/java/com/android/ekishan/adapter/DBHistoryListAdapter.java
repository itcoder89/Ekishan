package com.android.ekishan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.ekishan.R;
import com.android.ekishan.delivery.model.DBHistoryListData;
import com.android.ekishan.model.NewDeliveryBoyData;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class DBHistoryListAdapter extends RecyclerView.Adapter<DBHistoryListAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    List<NewDeliveryBoyData.DataBean> collection_list;
    DBHistoryListData.DataBean dataBean;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_no, order_id,distance,txt_mobile,tv_order_date;
        LinearLayout ll_view_single_post;
        Button accept;
        public MyViewHolder(View view) {
            super(view);
            order_no = (TextView) view.findViewById(R.id.order_no);
            order_id = (TextView) view.findViewById(R.id.order_id);
            distance = (TextView) view.findViewById(R.id.distance);
            txt_mobile = (TextView) view.findViewById(R.id.txt_mobile);
            tv_order_date = (TextView) view.findViewById(R.id.tv_order_date);
            accept = (Button) view.findViewById(R.id.accept);
        }
    }

    public DBHistoryListAdapter(Activity activity, List<NewDeliveryBoyData.DataBean> collection_list) {
        this.activity = activity;
        this.collection_list = collection_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deliv_boy_history_layout, parent, false);//member_item_layout
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.order_id.setText("Order No." + collection_list.get(position).getOrders_id());

        if (!collection_list.get(position).getOrders_id().equalsIgnoreCase("0")){
            holder.order_id.setText("Order No." + collection_list.get(position).getOrders_id());
        }else if (!collection_list.get(position).getSubscription_id().equalsIgnoreCase("0")){
            holder.order_id.setText("Subscription No. " + collection_list.get(position).getSubscription_id());
        }else if (!collection_list.get(position).getAgro_item_id().equalsIgnoreCase("0")){
            holder.order_id.setText("Agro Item No. " + collection_list.get(position).getAgro_item_id());
        }else if (collection_list.get(position).getOrder_type().equalsIgnoreCase("DEMAND")){
            holder.order_id.setText("Demand No. " + collection_list.get(position).getVendors_sales_id());
        }else {
            holder.order_id.setText("Sell No. " + collection_list.get(position).getVendors_sales_id());
        }

            holder.order_no.setText("" + collection_list.get(position).getName());
            holder.txt_mobile.setText("" + collection_list.get(position).getMobile());
            holder.tv_order_date.setText("" + collection_list.get(position).getOrders_date());
    }

    @Override
    public int getItemCount() {
        return collection_list.size();
    }
}
