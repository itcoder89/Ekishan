package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.chat.SingleChatActivity;
import com.android.ekishan.fragment.CustomBasketFragment;
import com.android.ekishan.fragment.ProductSingleFragment;
import com.android.ekishan.fragment.Vegetables;
import com.android.ekishan.model.HomeProductList;
import com.android.ekishan.vendor.VendorMainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KisanSuvidhaFragment extends Fragment {
    CardView agroItem,contact_us,agroNews,chat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kisan_suvidha_layout, container, false);
        agroItem=view.findViewById(R.id.agroItem);
        contact_us=view.findViewById(R.id.contact_us);
        agroNews=view.findViewById(R.id.agroNews);
        chat=view.findViewById(R.id.chat);
        agroItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof AgroItemsFragment)) {
                    loadFragment(new AgroItemsFragment());
                }
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ContactUsFragement)) {
                    loadFragment(new ContactUsFragement());
                }
            }
        });
        agroNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof AgroNewsFragment)) {
                    loadFragment(new AgroNewsFragment());
                }
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof VendorChatFragment)) {
//                    loadFragment(new VendorChatFragment());
//                }
               // Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SingleChatActivity.class));
            }
        });
        return view;
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
