package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.TabCollections;
import com.android.ekishan.adapter.SliderPagerAdapter;
import com.android.ekishan.fragment.CustomBasketFragment;
import com.android.ekishan.fragment.FindPeopleFragment;
import com.android.ekishan.fragment.ProductSingleFragment;
import com.android.ekishan.fragment.SaleFragment;
import com.android.ekishan.fragment.SalesReportFragment;
import com.android.ekishan.fragment.SearchFragment;
import com.android.ekishan.fragment.Vegetables;
import com.android.ekishan.model.CheckApprovalResponse;
import com.android.ekishan.model.HomeBanners;
import com.android.ekishan.model.HomeCategory;
import com.android.ekishan.model.HomeProductList;
import com.android.ekishan.model.reponseHome;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorDashbord extends Fragment {
    ImageView location_edit;
     RecyclerView category_recycle;

    ArrayList<HomeCategory> category_list = new ArrayList<>();
    ArrayList<HomeProductList> product_list = new ArrayList<>();
    ApiInterface apiService;
    ImageView cart,ivCollectionHistory,ivSaleRequestHistory;
    RelativeLayout main_layout,approval_pending,rlupdate;
    NestedScrollView nested;
    EditText staff_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_dashboard, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        nested = view.findViewById(R.id.nested);
        main_layout = view.findViewById(R.id.main_layout);
        rlupdate = view.findViewById(R.id.rlupdate);
        approval_pending = view.findViewById(R.id.approval_pending);
        category_recycle = view.findViewById(R.id.category_recycle);
        staff_id = view.findViewById(R.id.staff_id);
        ivCollectionHistory = getActivity().findViewById(R.id.ivCollectionHistory);
        ivSaleRequestHistory = getActivity().findViewById(R.id.ivSaleRequestHistory);
        cart = getActivity().findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new VendorHistory());
            }
        });
        ivCollectionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TabCollections.class));
            }
        });
        ivSaleRequestHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SalesReportFragment.class));
            }
        });


        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        category_recycle.setLayoutManager(staggeredGridLayoutManager);

        rlupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staff_id.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please enter staff id", Toast.LENGTH_SHORT).show();
                }else {
                    staffApprovalConfirmation();
                }
            }
        });
        return view;
    }

    private void staffApprovalConfirmation() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<CheckApprovalResponse> responseCall = apiService.staffApprovalConfirmation(MyApplication.getvendor_id(),staff_id.getText().toString());


        responseCall.enqueue(new Callback<CheckApprovalResponse>() {
            @Override
            public void onResponse(Call<CheckApprovalResponse> call, Response<CheckApprovalResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    setAdapter();
                }else {
                    main_layout.setVisibility(View.VISIBLE);
                    approval_pending.setVisibility(View.VISIBLE);
                    nested.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<CheckApprovalResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkApproval() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<CheckApprovalResponse> responseCall = apiService.checkApproval(MyApplication.getvendor_id());


        responseCall.enqueue(new Callback<CheckApprovalResponse>() {
            @Override
            public void onResponse(Call<CheckApprovalResponse> call, Response<CheckApprovalResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    if (response.body().getData().getIs_approved().equalsIgnoreCase("1")){
//                        get_profile();
                        setAdapter();
                    }else {
                        main_layout.setVisibility(View.VISIBLE);
                        approval_pending.setVisibility(View.VISIBLE);
                        nested.setVisibility(View.VISIBLE);
                    }
                }else {
                    main_layout.setVisibility(View.VISIBLE);
                    approval_pending.setVisibility(View.VISIBLE);
                    nested.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<CheckApprovalResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void get_profile() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
////        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
////        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
//        Call<reponseHome> responseCall = apiService.customers_home(MyApplication.getvendor_id());
//
//
//        responseCall.enqueue(new Callback<reponseHome>() {
//            @Override
//            public void onResponse(Call<reponseHome> call, Response<reponseHome> response) {
//                progressDialog.dismiss();
//
//                if (response.body().getSuccess().equals("1")) {
////                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
//                    main_layout.setVisibility(View.VISIBLE);
//                    approval_pending.setVisibility(View.GONE);
//                    nested.setVisibility(View.VISIBLE);
//                    category_list.clear();
//                    product_list.clear();
//                     category_list = response.body().getData().getCategories();
//                    product_list = response.body().getData().getProducts();
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<reponseHome> call, Throwable t) {
//                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        cart.setVisibility(View.GONE);
        ivCollectionHistory.setVisibility(View.GONE);
        ivSaleRequestHistory.setVisibility(View.GONE);
        checkApproval();
    }

    CategoryListAdapter adapter;

    private void setAdapter() {

//        if (sbpData.size() > 0) {
//            Collections.reverse(sbpData);
        main_layout.setVisibility(View.VISIBLE);
        approval_pending.setVisibility(View.GONE);
        nested.setVisibility(View.VISIBLE);
        adapter = new CategoryListAdapter(getActivity(), VendorDashbord.this);
        category_recycle.setAdapter(adapter);
//            ordermanagementExpandableOrder.setItemViewCacheSize(sbpData.size() * 3);
//        adapter.getFilter().filter(search_order_id);

//        }
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

    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;


        VendorDashbord fragment;

        public CategoryListAdapter(Context c, VendorDashbord fragment) {
            mContext = c;
            this.fragment = fragment;


        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ctegory_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            if (position == 0) {
                Picasso.with(getActivity())
                        .load(R.drawable.veg)
                        .placeholder(R.drawable.veg) // optional
                        .error(R.drawable.veg)         // optional
                        .into(holder.image);
                holder.txt_title.setText(getString(R.string.demand));
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));
            } else if (position == 1) {
                Picasso.with(getActivity())
                        .load(R.drawable.fruit)
                        .placeholder(R.drawable.fruit) // optional
                        .error(R.drawable.fruit)         // optional
                        .into(holder.image);
                holder.txt_title.setText(getString(R.string.collection));
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else if (position == 2) {
                Picasso.with(getActivity())
                        .load(R.drawable.ic_wallet)
                        .placeholder(R.drawable.sprouts) // optional
                        .error(R.drawable.sprouts)         // optional
                        .into(holder.image);
                holder.txt_title.setText(getString(R.string.wallet));
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue));
            } else  if (position == 3) {
                Picasso.with(getActivity())
                        .load(R.drawable.pack)
                        .placeholder(R.drawable.pack) // optional
                        .error(R.drawable.pack)         // optional
                        .into(holder.image);
                holder.txt_title.setText(getString(R.string.kisan_suvidha));
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow));

            }else {
                Picasso.with(getActivity())
                        .load(R.drawable.pack)
                        .placeholder(R.drawable.pack) // optional
                        .error(R.drawable.pack)         // optional
                        .into(holder.image);
                holder.txt_title.setText(getString(R.string.sale));
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow));

            }

            holder.crdmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof Demand)) {
                            loadFragment(new Demand());
                            cart.setVisibility(View.VISIBLE);
                        }
                    }else if (position == 1) {
                        //if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof VendorCollection)) {
                          //  loadFragment(new VendorCollection());
                            ivCollectionHistory.setVisibility(View.GONE);
                        //}
                        startActivity(new Intent(getActivity(), TabCollections.class));
                    }else if (position == 2) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof VedorWalletFragment)) {
                            loadFragment(new VedorWalletFragment());
                        }
                    }else if (position == 3) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof KisanSuvidhaFragment)) {
                            loadFragment(new KisanSuvidhaFragment());
                        }
                    } else {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof SaleFragment)) {
                            loadFragment(new SaleFragment());
                            ivSaleRequestHistory.setVisibility(View.VISIBLE);
                        }

                    }
                }
            });
//        holder.ll_order_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show_order_detail_BottomSheetDialog();
//            }
//        });
//        holder.ll_assign_schedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mContext,VehicleOrderServiceMgmt.class);
//                intent.putExtra("order_id","35235324534");
//                mContext.startActivity(intent);
//            }
//        });


        }

        @Override
        public int getItemCount() {
            return 5;
        }


        //        public void show_order_detail_BottomSheetDialog() {
//            View view = fragment.getLayoutInflater().inflate(R.layout.vehicle_order_detail, null);
//
//            final BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.MyDialog);
//            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(view);
//            dialog.setCanceledOnTouchOutside(true);
//            LinearLayout ll_cancle=view.findViewById(R.id.ll_cancle);
//            ll_cancle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//
//            dialog.show();
//        }
        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, service_type_txt, order_status_txt, chat, call, order_status;
            TextView order_id, service_type;
            TextView reason;
            TextView order_form, type_of_sub_service, invoice_no;
            TextView quote, payment_mode, amount, sla_txt, ved_txt;
            TextView product_types, service_date_time, payment_status, edit_instruction_txt;
            ImageView image;
            LinearLayout is_details_show, ll_order_detail, ll_assign_schedule, llassigned;
            CardView emp_doc_click, trade_lic_click, esta_card_click, service_letter_click, agreement_click;
            CardView emp_doc, service_letter_agreement, upload_premises_pass;
            RecyclerView assigned_staff_recycler;
            Spinner spnr_status;
            boolean is_touch = true;
            TextView pay_now;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout ll_drop_down;
            TextView update;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain = convertView.findViewById(R.id.crdmain);
                txt_title = convertView.findViewById(R.id.txt_title);
                image = convertView.findViewById(R.id.image);
//            drop_down =   convertView.findViewById(R.id.drop_down);
//            ll_order_detail =   convertView.findViewById(R.id.ll_order_detail);
//            ll_assign_schedule =   convertView.findViewById(R.id.ll_assign_schedule);

            }

        }


    }


    public class VegetablesListAdapter extends RecyclerView.Adapter<VegetablesListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<HomeProductList> data;
        List<HomeProductList> list;


        VendorDashbord fragment;

        public VegetablesListAdapter(Context c, List<HomeProductList> data, VendorDashbord fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment = fragment;


        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vegetables_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            holder.crdmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ProductSingleFragment)) {
                        loadFragment(new ProductSingleFragment(data.get(position).getId()));
                    }
                }
            });
            holder.txt_title.setText(data.get(position).getProducts_name());
            holder.description.setText(data.get(position).getProducts_description());
            holder.price.setText("₹"+data.get(position).getProducts_price().split("\\.")[0]);
            Picasso.with(getActivity())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);
            if (data.get(position).getQuantity()==null){
                data.get(position).setQuantity("0");
            }
            holder.quantity.setText(data.get(position).getQuantity());
            if (Integer.parseInt(data.get(position).getQuantity())>0){
                holder.ll_cart.setVisibility(View.VISIBLE);
                holder.rlLogin.setVisibility(View.GONE);
            }else {
                holder.ll_cart.setVisibility(View.GONE);
                holder.rlLogin.setVisibility(View.VISIBLE);
            }
            holder.rlLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"1",position);
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"0",position);
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"1",position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        list = data;
                    } else {
                        List<HomeProductList> filteredList = new ArrayList<>();
                        for (HomeProductList row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getProducts_name().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        list = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = list;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    data = (ArrayList<HomeProductList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, description, price, chat, call, order_status;
            ImageView image;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout ll_drop_down;
            TextView update,quantity;
            TextView minus,plus;
            RelativeLayout rlLogin;
            LinearLayout ll_cart;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain = convertView.findViewById(R.id.crdmain);
                txt_title = convertView.findViewById(R.id.txt_title);
                description =   convertView.findViewById(R.id.description);
                price =   convertView.findViewById(R.id.price);
                image =   convertView.findViewById(R.id.image);
                quantity=convertView.findViewById(R.id.quantity);
                ll_cart=convertView.findViewById(R.id.ll_cart);
                rlLogin=convertView.findViewById(R.id.rlLogin);
                minus=convertView.findViewById(R.id.minus);
                plus=convertView.findViewById(R.id.plus);
            }

        }

        private void addToCart(String product_id,String variety_id, final String isadd, final int pos) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Adding to cart...");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.addToCart(MyApplication.getCustomerId(),product_id,variety_id,isadd,"0");


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String res=response.body().string();
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("success").equals("1")) {
                            if (isadd.equalsIgnoreCase("1")){
                                data.get(pos).setQuantity(String.valueOf(Integer.parseInt(data.get(pos).getQuantity())+1));
                            }else {
                                data.get(pos).setQuantity(String.valueOf(Integer.parseInt(data.get(pos).getQuantity())-1));
                            }
                            notifyItemChanged(pos);
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
    }

}
