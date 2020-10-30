package com.android.ekishan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ekishan.R;
import com.android.ekishan.model.CollectionsListData;
import com.android.ekishan.model.OrderDetailsData;
import com.android.ekishan.model.VendorTransactionDetailData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.MyViewHolder> {
    private Activity activity;
    private Context context;
    List<VendorTransactionDetailData.DataBean> collection_list;
    CollectionsListData.DataBean dataBean;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView particulars, tvProductName,tvStatus,tvWeight,tvPrice;
        LinearLayout ll_view_single_post;
        public MyViewHolder(View view) {
            super(view);
            particulars = (TextView) view.findViewById(R.id.particulars);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            /*tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvWeight = (TextView) view.findViewById(R.id.tvWeight);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ll_view_single_post = (LinearLayout) view.findViewById(R.id.ll_view_single_post);*/
        }
    }


    public ProductsListAdapter(Activity activity, List<VendorTransactionDetailData.DataBean> collection_list) {
        this.activity = activity;
      //  this.context = context;
        this.collection_list = collection_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvProductName.setText(collection_list.get(position).getProducts_name()+"");
        holder.particulars.setText(collection_list.get(position).getQuantity()+" * "+collection_list.get(position).getPrice());
       /* holder.tvCollectionDateTime.setText(collection_list.get(position).getCollection_date()+"");
        holder.tvStatus.setText(collection_list.get(position).getStatus()+"");
        holder.tvWeight.setText(activity.getString(R.string.weight_hint)+":"+collection_list.get(position).getWeight()+"");
        holder.tvPrice.setText(activity.getString(R.string.price_hint)+":"+collection_list.get(position).getPrice()+"");

        holder.ll_view_single_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, CollectionDetailsActivity.class);
                intent.putExtra("order_id",collection_list.get(position).getId()+"");
                activity.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return collection_list.size();
    }
}
