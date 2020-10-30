package com.android.ekishan.fragment;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ekishan.R;
import com.android.ekishan.model.VendorOrderDetailData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by sanjay on 9/1/18.
 */

public class ProductSearch extends Fragment {

    VendorOrderDetailData.DataBean searchData;
    SearchAdapter searchAdapter;
    RecyclerView recycler_view;
    public List<VendorOrderDetailData.DataBean.ProductsBean> arrProductList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.post_search_layout, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recyPostSearch);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter(searchData);
    }

    public void updateAdapter(VendorOrderDetailData.DataBean searchData){
        this.searchData = searchData;
        if(searchData != null) {
            searchAdapter = new SearchAdapter(getActivity(),searchData.getProducts());
            recycler_view.setAdapter(searchAdapter);
        }
    }

    //Adapter for Grid layout
    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

        List<VendorOrderDetailData.DataBean.ProductsBean> arrProductList;
        Activity activity;
        public RecyclerView recyclerView;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public CircleImageView image;
            public TextView txt_title,description,price,quantity;
            public CardView crdmain;
            public MyViewHolder(View view) {
                super(view);
                image = (CircleImageView) view.findViewById(R.id.image);
                description = (TextView) view.findViewById(R.id.description);
                price = (TextView) view.findViewById(R.id.price);
                txt_title = (TextView) view.findViewById(R.id.txt_title);
                quantity = (TextView) view.findViewById(R.id.quantity);
                crdmain = (CardView) view.findViewById(R.id.crdmain);
            }
        }

        public SearchAdapter(Activity activity, List<VendorOrderDetailData.DataBean.ProductsBean> arrProductList) {
            this.activity = activity;
            this.arrProductList = arrProductList;
        }

        @Override
        public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_details_items_layout, parent, false);

            return new SearchAdapter.MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {

            holder.txt_title.setText(arrProductList.get(position).getProducts_name());
            holder.description.setText(arrProductList.get(position).getProducts_description());
            holder.price.setText("₹"+arrProductList.get(position).getProducts_price());
            holder.quantity.setText("Quantity : "+arrProductList.get(position).getProducts_quantity());
            Picasso.with(activity)
                    .load(arrProductList.get(position).getProducts_image())
                    .placeholder(R.drawable.veg) // optional
                    .error(R.drawable.veg)         // optional
                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            try{
                return arrProductList.size();
            }catch (Exception e){
                return 0;
            }
        }
    }
}

