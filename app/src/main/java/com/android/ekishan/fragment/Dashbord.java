package com.android.ekishan.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
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

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.AppSignatureHelper;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.SliderPagerAdapter;
import com.android.ekishan.model.CheckApprovalResponse;
import com.android.ekishan.model.HomeBanners;
import com.android.ekishan.model.HomeCategory;
import com.android.ekishan.model.HomeProductList;
import com.android.ekishan.model.ReponseProfile;
import com.android.ekishan.model.reponseHome;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashbord extends Fragment {
    ImageView location_edit;
    ViewPager vp_slider,vp_slider1;
    int page_position = -1;
    int page_position1 = -1;
    RecyclerView category_recycle, fruis_vegetables_recycle;
    ArrayList<HomeBanners> banner_list = new ArrayList<>();
    ArrayList<HomeBanners> banner_bottom = new ArrayList<>();
    ArrayList<HomeCategory> category_list = new ArrayList<>();
    ArrayList<HomeProductList> product_list = new ArrayList<>();
    ApiInterface apiService;
    CardView searCard;
    TextView cart_quantity;
    Dialog dialog;
    EditText edt_mobile;
    EditText edt_otp;
    TextView tvLogin;
    LinearLayout ll_otp;
    Handler handler1;
    Runnable update1;
    Handler handler;
    Runnable update;
    TextView txt_cat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //slider
        cart_quantity = getActivity().findViewById(R.id.cart_quantity);
        searCard = view.findViewById(R.id.searCard);
        vp_slider = view.findViewById(R.id.vp_slider);
        vp_slider1 = view.findViewById(R.id.vp_slider1);
        txt_cat = view.findViewById(R.id.txt_cat);
        txt_cat.setText(MyApplication.change_to_hindi(R.string.category,getActivity()));



        vp_slider.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vp_slider.setPadding(60, 0, 60, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vp_slider.setPageMargin(20);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int  position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//
//        //category slider
//        handler1 = new Handler();
//         update1 = new Runnable() {
//            public void run() {
//                if (page_position1 == banner_bottom.size()) {
//                    page_position1 = 0;
//                } else {
//                    page_position1 = page_position1 + 1;
//                }
//                vp_slider1.setCurrentItem(page_position1, true);
//            }
//        };


        vp_slider1.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vp_slider1.setPadding(60, 0, 60, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vp_slider1.setPageMargin(20);


        vp_slider1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int  position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        category_recycle = view.findViewById(R.id.category_recycle);
        fruis_vegetables_recycle = view.findViewById(R.id.fruis_vegetables_recycle);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        category_recycle.setLayoutManager(staggeredGridLayoutManager);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        fruis_vegetables_recycle.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(
                        2, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        fruis_vegetables_recycle.setLayoutManager(staggeredGridLayoutManager1);
        searCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof SearchFragment)) {
                    MyApplication.IS_CUSTOM_BASKET="SEARCH";
                    loadFragment(new SearchFragment());
                }
            }
        });
        get_profile();
        setAdapter();
        setvegetablesAdapter();

        return view;
    }

    private void get_profile() {
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(getActivity());
        Log.e("appSignatureHelper",":"+appSignatureHelper);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<reponseHome> responseCall = apiService.customers_home(MyApplication.getCustomerId(),appSignatureHelper.getAppSignatures().get(0));


        responseCall.enqueue(new Callback<reponseHome>() {
            @Override
            public void onResponse(Call<reponseHome> call, Response<reponseHome> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
//                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    cart_quantity.setText(response.body().getData().getCart_quantity());
                    banner_list.clear();
                    category_list.clear();
                    product_list.clear();
                    banner_list = response.body().getData().getBanners();
                    banner_bottom = response.body().getData().getBanners_bottom();
                    category_list = response.body().getData().getCategories();
                    product_list = response.body().getData().getProducts();
                    setAdapter();
                    setvegetablesAdapter();
                    if(banner_list.size()>0) {
                        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(getActivity(), banner_list,getActivity());
                        vp_slider.setAdapter(sliderPagerAdapter);
                        if (handler!=null) {
                            handler.removeCallbacks(update);
                        }
                        handler = new Handler();
                        update = new Runnable() {
                            public void run() {
                                if (page_position == banner_list.size()) {
                                    page_position = 0;
                                } else {
                                    page_position = page_position + 1;
                                }
                                vp_slider.setCurrentItem(page_position, true);
                                handler.postDelayed(update,2000);
                            }
                        };
                        handler.postDelayed(update,2000);
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                handler.post(update);
//                            }
//                        }, 100, 2500);

                    }else {
                        vp_slider.setVisibility(View.GONE);
                    }
                    if(banner_bottom.size()>0) {
                        SliderPagerAdapter sliderPagerAdapter1 = new SliderPagerAdapter(getActivity(), banner_bottom,getActivity());
                        vp_slider1.setAdapter(sliderPagerAdapter1);
                        if (handler1!=null) {
                            handler1.removeCallbacks(update1);
                        }
                        handler1 = new Handler();
                        update1 = new Runnable() {
                            public void run() {
                                if (page_position1 == banner_bottom.size()) {
                                    page_position1 = 0;
                                } else {
                                    page_position1 = page_position1 + 1;
                                }
                                vp_slider1.setCurrentItem(page_position1, true);
                                handler1.postDelayed(update1,2000);
                            }
                        };
                        handler1.postDelayed(update1,2000);
//
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                handler1.post(update1);
//                            }
//                        }, 100, 2500);
                    }else {
                        vp_slider1.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<reponseHome> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    CategoryListAdapter adapter;

    private void setAdapter() {

//        if (sbpData.size() > 0) {
//            Collections.reverse(sbpData);
        adapter = new CategoryListAdapter(getActivity(), category_list, Dashbord.this);
        category_recycle.setAdapter(adapter);
//            ordermanagementExpandableOrder.setItemViewCacheSize(sbpData.size() * 3);
//        adapter.getFilter().filter(search_order_id);

//        }
    }

    private void setvegetablesAdapter() {

//        if (sbpData.size() > 0) {
//            Collections.reverse(sbpData);
        VegetablesListAdapter adapter = new VegetablesListAdapter(getActivity(), product_list, Dashbord.this);
        fruis_vegetables_recycle.setAdapter(adapter);
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

    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<HomeCategory> data;
        List<HomeCategory> list;


        Dashbord fragment;

        public CategoryListAdapter(Context c, List<HomeCategory> data, Dashbord fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
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
            if (position == 1) {
                Picasso.with(getActivity())
                        .load(data.get(position).getImage())
//                        .placeholder(R.drawable.veg) // optional
//                        .error(R.drawable.veg)         // optional
                        .into(holder.image);
//                holder.txt_title.setText("VEGETABLES");
                holder.txt_title.setText(data.get(position).getDisplay_name().toUpperCase());
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.pink));
            } else if (position == 2) {
                Picasso.with(getActivity())
                        .load(data.get(position).getImage())
//                        .placeholder(R.drawable.fruit) // optional
//                        .error(R.drawable.fruit)         // optional
                        .into(holder.image);
//                holder.txt_title.setText("FRUITS");
                holder.txt_title.setText(data.get(position).getDisplay_name().toUpperCase());
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.grey));
            } else if (position == 3) {
                Picasso.with(getActivity())
                        .load(data.get(position).getImage())
//                        .placeholder(R.drawable.sprouts) // optional
//                        .error(R.drawable.sprouts)         // optional
                        .into(holder.image);
//                holder.txt_title.setText("SPROUTS");
                holder.txt_title.setText(data.get(position).getDisplay_name().toUpperCase());
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.blue));
            } else {
                Picasso.with(getActivity())
                        .load(data.get(position).getImage())
//                        .placeholder(R.drawable.pack) // optional
//                        .error(R.drawable.pack)         // optional
                        .into(holder.image);
//                holder.txt_title.setText("PACKAGES");
                holder.txt_title.setText(data.get(position).getDisplay_name().toUpperCase());
                holder.crdmain.setCardBackgroundColor(mContext.getResources().getColor(R.color.yellow));

            }

            holder.crdmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 1) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof Vegetables)) {
                            loadFragment(new Vegetables());
                        }
                    }else if (position == 0) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof Vegetables)) {
                            MyApplication.IS_CUSTOM_BASKET="FRUITS";
                            loadFragment(new CustomBasketFragment());
                        }
                    }else if (position == 3) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof Vegetables)) {
                            MyApplication.IS_CUSTOM_BASKET="EKISAN";
                            loadFragment(new CustomBasketFragment());
                        }
                    } else {
                        if (MyApplication.isLogin()) {
                            if (MyApplication.getMobile().equalsIgnoreCase("")){
                                verifyMobileDialog();
                                return;
                            }
                            FindPeopleFragment fragment=new FindPeopleFragment();
                            if (fragment != null) {
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .addToBackStack(null)
                                        .replace(R.id.fragment_container, fragment)
                                        .commit();
                            }
                        }else {
                            MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                        }
                        //switching fragment


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
                        List<HomeCategory> filteredList = new ArrayList<>();
                        for (HomeCategory row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getId().toLowerCase().contains(charString.toLowerCase())) {
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
                    data = (ArrayList<HomeCategory>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
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
        public void verifyMobileDialog(){
            dialog=new Dialog(getActivity());
            dialog.setContentView(R.layout.verfiy_mobile_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            RelativeLayout rlLogin=dialog.findViewById(R.id.rlLogin);
            RelativeLayout cancel_action=dialog.findViewById(R.id.cancel_action);
            ll_otp=dialog.findViewById(R.id.ll_otp);
            edt_mobile=dialog.findViewById(R.id.edt_mobile);
            edt_otp=dialog.findViewById(R.id.edt_otp);
            tvLogin=dialog.findViewById(R.id.tvLogin);
            cancel_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            rlLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edt_mobile.getText().toString().equalsIgnoreCase("")&&edt_mobile.getText().toString().length()==10){
                        if (ll_otp.getVisibility()==View.GONE) {
                            edt_mobile.setEnabled(false);
                            sendOtpSocialVerify(edt_mobile.getText().toString());
                        }else {
                            if (!edt_otp.getText().toString().equalsIgnoreCase("")){
                                verifyOtpSocialVerify(edt_mobile.getText().toString(),edt_otp.getText().toString());
                            }else {
                                edt_otp.setError("Please enter valid otp");
                            }
                        }
                    }else {
                        edt_mobile.setError("Please enter valid number");
                    }
                }
            });
            dialog.show();
        }
        private void sendOtpSocialVerify(String mobile) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.sendOtpSocialVerify(MyApplication.getCustomerId(),mobile);


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String res=response.body().string();
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("success").equals("1")) {
                            ll_otp.setVisibility(View.VISIBLE);
                            tvLogin.setText("Submit");
                        }else {
                            Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
        private void verifyOtpSocialVerify(String mobile,String otp) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.verifyOtpSocialVerify(MyApplication.getCustomerId(),mobile,otp);


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String res=response.body().string();
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("success").equals("1")) {
                            if (dialog!=null){
                                dialog.cancel();
                                MyApplication.setMobile(mobile);
                                Toast.makeText(getActivity(), "Mobile verified successfully", Toast.LENGTH_SHORT).show();
                            }
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


    public class VegetablesListAdapter extends RecyclerView.Adapter<VegetablesListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<HomeProductList> data;
        List<HomeProductList> list;


        Dashbord fragment;

        public VegetablesListAdapter(Context c, List<HomeProductList> data, Dashbord fragment) {
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
            if (data.get(position).getOut_of_stock().equalsIgnoreCase("1")){
                holder.outOfstock.setVisibility(View.VISIBLE);
            }else {
                holder.outOfstock.setVisibility(View.GONE);
            }
            holder.crdmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ProductSingleFragment)) {
                        loadFragment(new ProductSingleFragment(data.get(position).getId()));
                    }
                }
            });

            holder.weight.setText(data.get(position).getWeight());
            holder.variety_name.setText(capitizeString(data.get(position).getVariety_name()));
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
                    if (MyApplication.isLogin()) {
                        addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"1",position);
                    }else {
                        MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                    }
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
        private String capitizeString(String name){
            String captilizedString="";
            if(!name.trim().equals("")){
                captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
            }
            return captilizedString;
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
            TextView weight,txt_title, description, price, variety_name, call, order_status;
            ImageView image;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout ll_drop_down;
            TextView update,quantity;
            TextView minus,plus;
            RelativeLayout rlLogin,outOfstock;
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
                outOfstock=convertView.findViewById(R.id.outOfstock);
                minus=convertView.findViewById(R.id.minus);
                plus=convertView.findViewById(R.id.plus);
                variety_name=convertView.findViewById(R.id.variety_name);
                weight=convertView.findViewById(R.id.weight);
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
                                if (Integer.parseInt(data.get(pos).getQuantity())<1) {
                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) + 1));
                                }
                                data.get(pos).setQuantity(String.valueOf(Integer.parseInt(data.get(pos).getQuantity())+1));
                            }else {
                                if (Integer.parseInt(data.get(pos).getQuantity())==1) {
                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) - 1));
                                }
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
