package com.android.ekishan.fragment;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.OrderDetailsModel;
import com.android.ekishan.model.OrderDetailsProductListModel;
import com.android.ekishan.model.OrderDetailsResponse;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsFragment extends Fragment {
    ArrayList<OrderDetailsProductListModel> product_list=new ArrayList<>();
    RecyclerView itemRecycler;
    CategoryListAdapter adapter;
    RelativeLayout alerLayout,offerLayout;
    LinearLayout summeryLayout;
    View view1,view2;
    ApiInterface apiService;
    LinearLayout llSlotLayout,pkgDetailsll;
    TextView date,time,orderStatus,ddSlot,pkgDetails;
    TextView name,address,mobile;
    TextView order_no,invoice_no,payment_option,itemCount,orderPrice;
    TextView package_name,starting_date,no_of_del,package_status;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.order_details_frag_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        package_name=root.findViewById(R.id.package_name);
        starting_date=root.findViewById(R.id.starting_date);
        no_of_del=root.findViewById(R.id.no_of_del);
        package_status=root.findViewById(R.id.package_status);
        date=root.findViewById(R.id.date);
        time=root.findViewById(R.id.time);
        llSlotLayout=root.findViewById(R.id.llSlotLayout);
        pkgDetailsll=root.findViewById(R.id.pkgDetailsll);
        orderStatus=root.findViewById(R.id.orderStatus);
        ddSlot=root.findViewById(R.id.ddSlot);
        pkgDetails=root.findViewById(R.id.pkgDetails);
        name=root.findViewById(R.id.name);
        address=root.findViewById(R.id.address);
        mobile=root.findViewById(R.id.mobile);
        order_no=root.findViewById(R.id.order_no);
        orderPrice=root.findViewById(R.id.orderPrice);
        invoice_no=root.findViewById(R.id.invoice_no);
        payment_option=root.findViewById(R.id.payment_option);
        itemCount=root.findViewById(R.id.itemCount);
        summeryLayout=root.findViewById(R.id.summeryLayout);
        view1=root.findViewById(R.id.view1);
        view2=root.findViewById(R.id.view2);
        alerLayout=root.findViewById(R.id.alerLayout);
        offerLayout=root.findViewById(R.id.offerLayout);
        itemRecycler=root.findViewById(R.id.itemRecycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        itemRecycler.setLayoutManager(staggeredGridLayoutManager);

        alerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summeryLayout.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                itemRecycler.setVisibility(View.GONE);
            }
        });
        offerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemRecycler.setVisibility(View.VISIBLE);
                summeryLayout.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
            }
        });
        if (!MyApplication.isFrom.equalsIgnoreCase("ORDER")){
            pkgDetails.setVisibility(View.VISIBLE);
            pkgDetailsll.setVisibility(View.VISIBLE);
            ddSlot.setVisibility(View.GONE);
            llSlotLayout.setVisibility(View.GONE);
        }
        getOrders();
        return root;
    }

    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<OrderDetailsResponse> responseCall;
        if (!MyApplication.isFrom.equalsIgnoreCase("ORDER")){
            responseCall = apiService.getSubscriptionDetails(MyApplication.subscription_id);
        }else {
            responseCall = apiService.getOrderDetails(MyApplication.ORDER_ID);
        }

        responseCall.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    summeryLayout.setVisibility(View.VISIBLE);
                    product_list=response.body().getData().getProducts();
                    setData(response.body().getData());
                    adapter = new CategoryListAdapter(getActivity(), product_list,OrderDetailsFragment.this);
                    itemRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void setData(OrderDetailsModel orderDetailsModel){
        date.setText(orderDetailsModel.getOrder_booking_date());
        time.setText(orderDetailsModel.getOrder_booking_time());
        orderStatus.setText("Order Status : "+orderDetailsModel.getOrder_status());
        name.setText(orderDetailsModel.getDelivery_name());
        address.setText(orderDetailsModel.getDelivery_street_address());
        mobile.setText("Mo No. "+orderDetailsModel.getCustomers_telephone());
        order_no.setText(orderDetailsModel.getOrders_id());
        invoice_no.setText("Invoice_34550");
        payment_option.setText(orderDetailsModel.getPayment_method());
        itemCount.setText(orderDetailsModel.getProducts_count());
        orderPrice.setText(orderDetailsModel.getOrder_price());
        package_name.setText("Package : "+orderDetailsModel.getPackage_name());
        starting_date.setText(orderDetailsModel.getPackage_delivery_date());
        no_of_del.setText("No of Deliveries : "+ orderDetailsModel.getPackage_number_of_deliveries());
        package_status.setText("Package Status : "+orderDetailsModel.getPackage_status());
    }

    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<OrderDetailsProductListModel> data;
        OrderDetailsFragment fragment;

        public CategoryListAdapter(Context c, List<OrderDetailsProductListModel> data,OrderDetailsFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }

        @Override
        public CategoryListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_details_items_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter.CustomViewHolder viewHolder = new CategoryListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter.CustomViewHolder holder, final int position) {
                holder.txt_title.setText(data.get(position).getProducts_name());
                if (!data.get(position).getSubscription_type().equalsIgnoreCase("")){
                    holder.price.setText(data.get(position).getSubscription_type());
                }else {
                    holder.price.setText("₹"+data.get(position).getProducts_price());
                }
                holder.quantity.setText("Quantity : "+data.get(position).getProducts_quantity());
                holder.product_type.setText(data.get(position).getVariety_name());
                holder.description.setText(data.get(position).getProducts_description());
                Picasso.with(getActivity())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            CardView crdmain;
            TextView product_type,txt_title;
            TextView description,price,quantity;
            CircleImageView image;
            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain =   convertView.findViewById(R.id.crdmain);
                product_type =   convertView.findViewById(R.id.product_type);
                txt_title =   convertView.findViewById(R.id.txt_title);
                description =   convertView.findViewById(R.id.description);
                price =   convertView.findViewById(R.id.price);
                quantity =   convertView.findViewById(R.id.quantity);
                image =   convertView.findViewById(R.id.image);
            }

        }






    }
}
