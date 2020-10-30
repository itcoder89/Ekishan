package com.android.ekishan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ekishan.R;
import com.android.ekishan.model.VendorOrderDetailData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by sanjay on 9/1/18.
 */

public class VendorOrderDetails extends Fragment {

    VendorOrderDetailData.DataBean searchData;
    TextView order_no,invoice_no,payment_option,itemCount,orderPrice;
    TextView name,address,mobile,date,time,orderStatus;
    public List<VendorOrderDetailData.DataBean.ProductsBean> arrProductList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.vendor_order_details_layout, container, false);
        order_no = (TextView) view.findViewById(R.id.order_no);
        invoice_no = (TextView) view.findViewById(R.id.invoice_no);
        payment_option = (TextView) view.findViewById(R.id.payment_option);
        itemCount = (TextView) view.findViewById(R.id.itemCount);
        orderPrice = (TextView) view.findViewById(R.id.orderPrice);

        name = (TextView) view.findViewById(R.id.name);
        address = (TextView) view.findViewById(R.id.address);
        mobile = (TextView) view.findViewById(R.id.mobile);
        date = (TextView) view.findViewById(R.id.date);
        time = (TextView) view.findViewById(R.id.time);
        orderStatus = (TextView) view.findViewById(R.id.orderStatus);


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
            order_no.setText(searchData.getOrders_id()+"");
            invoice_no.setText(searchData.getInvoice());
            payment_option.setText(searchData.getPayment_method());
            itemCount.setText(searchData.getProducts_count()+"");
            orderPrice.setText(searchData.getOrder_price());
            orderStatus.setText("Order Status : "+searchData.getOrder_status());
            date.setText(searchData.getOrder_booking_date());
            time.setText(searchData.getOrder_booking_time());
            mobile.setText("Mob No."+searchData.getCustomers_telephone());
            address.setText(searchData.getDelivery_street_address());
            name.setText(searchData.getDelivery_name());
        }
    }

}

