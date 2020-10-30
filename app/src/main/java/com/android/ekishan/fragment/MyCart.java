package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.CartDataModel;
import com.android.ekishan.model.CartListModel;
import com.android.ekishan.model.CartListResponse;
import com.android.ekishan.model.ResponseOrder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart extends Fragment {
    RecyclerView my_order_recycle;
    ArrayList<CartListModel> order_list=new ArrayList<>();
    LinearLayout checkout;
    ApiInterface apiService;
    CartDataModel cartDataModel;
    TextView total_item,total_price,total_save,noFound;
    TextView cart_quantity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cart, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        checkout=view.findViewById(R.id.checkout);
        total_item=view.findViewById(R.id.total_item);
        total_price=view.findViewById(R.id.total_price);
        total_save=view.findViewById(R.id.total_save);
        noFound=view.findViewById(R.id.noFound);
        cart_quantity = getActivity().findViewById(R.id.cart_quantity);
        my_order_recycle=view.findViewById(R.id.my_order_recycle);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order_list.size()>0){
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof DeliveryOptionFragment)) {
                        loadFragment(new DeliveryOptionFragment());
                    }
                }else {
                    Toast.makeText(getActivity(),"Please add to cart first!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        getCartList();
        return view;
    }
    private void getCartList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<CartListResponse> responseCall = apiService.getCartList(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<CartListResponse>() {
            @Override
            public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                progressDialog.dismiss();
                order_list.clear();
                if (response.body().getSuccess().equals("1")) {
                    order_list=response.body().getProducts();
                    cartDataModel=response.body().getData();
                    MyApplication.totalItem=cartDataModel.getTotal_item();
                    MyApplication.totalprice=cartDataModel.getPrice();
                    total_item.setText(cartDataModel.getTotal_item());
                    total_price.setText(cartDataModel.getPrice());
                    total_save.setText(cartDataModel.getSave_price());
                    setAdapter();
                }else {
                    MyApplication.totalItem="0";
                    MyApplication.totalprice="0";
                    total_item.setText("0");
                    total_price.setText("0");
                    total_save.setText("0");
                    noFound.setVisibility(View.VISIBLE);
                    my_order_recycle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CartListResponse> call, Throwable t) {
                noFound.setVisibility(View.VISIBLE);
                my_order_recycle.setVisibility(View.GONE);
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
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
     MyOrderListAdapter adapter;

    private void setAdapter() {
        if (order_list.size()>0) {
            noFound.setVisibility(View.GONE);
            my_order_recycle.setVisibility(View.VISIBLE);
            adapter = new MyOrderListAdapter(getActivity(), order_list,MyCart.this);
            my_order_recycle.setAdapter(adapter);
        }else {
            noFound.setVisibility(View.VISIBLE);
            my_order_recycle.setVisibility(View.GONE);
            total_item.setText("0");
            total_price.setText("0");
            total_save.setText("0");
        }
    }



    //


    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<CartListModel> data;


        MyCart fragment;

        public MyOrderListAdapter(Context c, List<CartListModel> data,MyCart fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public  CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycart_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
             CustomViewHolder viewHolder = new  CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {

            holder.p_name.setText(data.get(position).getProducts_name());
            float total_price=Float.parseFloat(data.get(position).getSpecial_price())*Float.parseFloat(data.get(position).getCustomers_basket_quantity());
            holder.price.setText(""+total_price);

            holder.special_price.setText(""+data.get(position).getPrice());
            holder.p_type.setText(data.get(position).getProduct_type());
            holder.weight.setText(data.get(position).getWeight());
            if (data.get(position).getSpecial_price().equalsIgnoreCase(data.get(position).getPrice())){
                holder.ll_offer.setVisibility(View.GONE);
            }
            Picasso.with(getActivity())
                    .load(data.get(position).getImage())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.img);
            if (data.get(position).getCustomers_basket_quantity()==null){
                data.get(position).setCustomers_basket_quantity("0");
            }
            holder.quantity.setText(data.get(position).getCustomers_basket_quantity());
            if (Integer.parseInt(data.get(position).getCustomers_basket_quantity())==0){
                data.remove(position);
                fragment.setAdapter();
            }

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getProducts_id().equalsIgnoreCase("0")) {
                        addToCart("0", data.get(position).getVariety_id(), "0", position,data.get(position).getBasket_id());
                    }else {

                        addToCart(data.get(position).getProducts_id(), data.get(position).getVariety_id(), "0", position,"0");
                    }
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getProducts_id().equalsIgnoreCase("0")) {
                        addToCart("0", data.get(position).getVariety_id(), "1", position,data.get(position).getBasket_id());
                    }else {

                        addToCart(data.get(position).getProducts_id(), data.get(position).getVariety_id(), "1", position,"0");
                    }
                }
            });


        }
        private void addToCart(String product_id,String variety_id, final String isadd, final int pos,String basket_id) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Adding to cart...");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.addToCart(MyApplication.getCustomerId(),product_id,variety_id,isadd,basket_id);


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String res=response.body().string();
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("success").equals("1")) {
                            if (isadd.equalsIgnoreCase("1")){
//                                if (Integer.parseInt(data.get(pos).getCustomers_basket_quantity())<1) {
//                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) + 1));
//                                }
//                                data.get(pos).setCustomers_basket_quantity(String.valueOf(Integer.parseInt(data.get(pos).getCustomers_basket_quantity())+1));
                            }else {
                                if (Integer.parseInt(data.get(pos).getCustomers_basket_quantity())==1) {
                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) - 1));
                                }
//                                data.get(pos).setCustomers_basket_quantity(String.valueOf(Integer.parseInt(data.get(pos).getCustomers_basket_quantity())-1));
                            }
//                            notifyItemChanged(pos);
                            getCartList();
                        }else {
                            Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
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
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            CircleImageView img;
            LinearLayout ll_cart;
            RelativeLayout ll_offer;
            TextView p_name,p_type,weight,price,quantity;
            TextView minus,plus,special_price;
            public CustomViewHolder(View convertView) {
                super(convertView);
                img=convertView.findViewById(R.id.img);
                p_name=convertView.findViewById(R.id.p_name);
                p_type=convertView.findViewById(R.id.p_type);
                weight=convertView.findViewById(R.id.weight);
                price=convertView.findViewById(R.id.price);
                quantity=convertView.findViewById(R.id.quantity);
                ll_cart=convertView.findViewById(R.id.ll_cart);
                minus=convertView.findViewById(R.id.minus);
                plus=convertView.findViewById(R.id.plus);
                special_price=convertView.findViewById(R.id.special_price);
                ll_offer=convertView.findViewById(R.id.ll_offer);
            }

        }






    }


}
