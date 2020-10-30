package com.android.ekishan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.ekishan.R;

public class OrderSuccesFragment extends Fragment {
    LinearLayout succes;
    String order_id,order_date;
    String order_price,total_items;
    TextView order_no,date,item,price;

    public OrderSuccesFragment(String order_id,String order_date,String order_price,String total_items){
        this.order_id=order_id;
        this.order_date=order_date;
        this.order_price=order_price;
        this.total_items=total_items;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.order_success_layout, container, false);
        order_no=root.findViewById(R.id.order_no);
        date=root.findViewById(R.id.date);
        item=root.findViewById(R.id.item);
        price=root.findViewById(R.id.price);
        succes=root.findViewById(R.id.succes);
        order_no.setText("Order-NO. : "+order_id);
        date.setText(order_date);
        item.setText("Item : "+total_items);
        price.setText("Rs : "+order_price);
        succes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Dashbord());
            }
        });
        return root;
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
}
