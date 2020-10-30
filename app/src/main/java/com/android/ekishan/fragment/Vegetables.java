package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.adapter.BasketBannerADapter;
import com.android.ekishan.adapter.SliderPagerAdapter;
import com.android.ekishan.model.BasketBannerList;
import com.android.ekishan.model.ResponseBasket;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.model.SmallBasketList;
import com.android.ekishan.model.SmallBasketProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Vegetables extends Fragment {
    ImageView location_edit;
    ViewPager vp_slider;
    int page_position = -1;
    ArrayList<BasketBannerList> slider_image_list = new ArrayList<>();
    RecyclerView category_recycle;
    ArrayList<String> order_category_recyclelist = new ArrayList<>();
    LinearLayout customBasket, smallBasket;
    LinearLayout mediumBasket, LargeBasket,buyButton;
    TextView smallTxt, mediumTxt;
    TextView largeTxt;
    TextView edt_amount,txt_des;
    String smalldescription;
    String mediumdescription;
    String largedescription;
    String smallBasketId;
    String mediumBasketId;
    String largeBasketId;
    TextView basketName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vegetales, container, false);
        //slider
        apiService = ApiClient.getClient().create(ApiInterface.class);
        buyButton=getActivity().findViewById(R.id.buyButton);
        smallTxt = view.findViewById(R.id.smallTxt);
        txt_des = view.findViewById(R.id.txt_des);
        edt_amount = view.findViewById(R.id.edt_amount);
        mediumTxt = view.findViewById(R.id.mediumTxt);
        largeTxt = view.findViewById(R.id.largeTxt);
        customBasket = view.findViewById(R.id.customBasket);
        smallBasket = view.findViewById(R.id.smallBasket);
        mediumBasket = view.findViewById(R.id.mediumBasket);
        LargeBasket = view.findViewById(R.id.LargeBasket);
        vp_slider = view.findViewById(R.id.vp_slider);
        basketName = view.findViewById(R.id.basketName);
        buyButton.setVisibility(View.VISIBLE);
        category_recycle = view.findViewById(R.id.category_recycle);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        2, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        category_recycle.setLayoutManager(staggeredGridLayoutManager);

        customBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof CustomBasketFragment)) {
                    MyApplication.IS_CUSTOM_BASKET="ALL";
                    loadFragment(new CustomBasketFragment());
                }
            }
        });
        smallBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    basketName.setText("Small Basket");
                    smallTxt.setTextColor(getResources().getColor(R.color.color_white));
                    mediumTxt.setTextColor(getResources().getColor(R.color.black));
                    largeTxt.setTextColor(getResources().getColor(R.color.black));
                    txt_des.setText(smalldescription);
                    smallBasket.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7C21C")));
                    mediumBasket.setBackgroundTintList(null);
                    LargeBasket.setBackgroundTintList(null);
                    MyApplication.basketId =smallBasketId;
                    setAdapter(small_basket_list);
                    if (small_product_price!=null) {
                        if (!small_product_price.equalsIgnoreCase("")) {
                            edt_amount.setText("₹" + small_product_price);
                        } else {
                            edt_amount.setText("₹0");
                        }
                    }else {
                        edt_amount.setText("₹0");
                    }
                }
            }
        });
        mediumBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    basketName.setText("Medium Basket");
                    smallTxt.setTextColor(getResources().getColor(R.color.black));
                    mediumTxt.setTextColor(getResources().getColor(R.color.color_white));
                    largeTxt.setTextColor(getResources().getColor(R.color.black));
                    mediumBasket.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7C21C")));
                    smallBasket.setBackgroundTintList(null);
                    LargeBasket.setBackgroundTintList(null);
                    txt_des.setText(mediumdescription);
                    MyApplication.basketId =mediumBasketId;
                    setAdapter(medium_basket_list);
                    if (medium_product_price!=null&&!medium_product_price.equalsIgnoreCase("")) {
                        if (Integer.parseInt(medium_product_price) > 0) {
                            edt_amount.setText("₹" + medium_product_price);
                        } else {
                            edt_amount.setText("₹0");
                        }
                    }else {
                        edt_amount.setText("₹0");
                    }
                }
            }
        });
        LargeBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    basketName.setText("Large Basket");
                    smallTxt.setTextColor(getResources().getColor(R.color.black));
                    mediumTxt.setTextColor(getResources().getColor(R.color.black));
                    largeTxt.setTextColor(getResources().getColor(R.color.color_white));
                    LargeBasket.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7C21C")));
                    smallBasket.setBackgroundTintList(null);
                    mediumBasket.setBackgroundTintList(null);
                    txt_des.setText(largedescription);
                    MyApplication.basketId =largeBasketId;
                    setAdapter(large_basket_list);
                    if (large_product_price!=null) {
                        if (!large_product_price.equalsIgnoreCase("")) {
                            edt_amount.setText("₹" + large_product_price);
                        } else {
                            edt_amount.setText("₹0");
                        }
                    }else {
                        edt_amount.setText("₹0");
                    }

                }
            }
        });
        getbaskets();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        buyButton.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        buyButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        buyButton.setVisibility(View.GONE);
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

    String small_product_price = "";
    String medium_product_price = "";
    String large_product_price = "";
    ApiInterface apiService;

    private void getbaskets() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBasket> responseCall = apiService.basket();


        responseCall.enqueue(new Callback<ResponseBasket>() {
            @Override
            public void onResponse(Call<ResponseBasket> call, Response<ResponseBasket> response) {
                progressDialog.dismiss();
                if (response.body().getSuccess().equals("1")) {
                    slider_image_list = response.body().getData().getBanners();
                    if (response.body().getData().getSmallbasket() != null) {
                        MyApplication.basketId = response.body().getData().getSmallbasket().get(0).getProducts_id();
                        smalldescription = response.body().getData().getSmallbasket().get(0).getDescription();
                        smallBasketId = response.body().getData().getSmallbasket().get(0).getProducts_id();
                        small_basket_list = response.body().getData().getSmallbasket().get(0).getBasket_product();
                        edt_amount.setText("₹"+response.body().getData().getSmallbasket().get(0).getProducts_price());
                        small_product_price = response.body().getData().getSmallbasket().get(0).getProducts_price();
                        txt_des.setText(smalldescription);
                        setbannerdata();
                        setAdapter(small_basket_list);
                    }
                    if (response.body().getData().getMediumbasket() != null && response.body().getData().getMediumbasket().size()>0) {
                        mediumdescription = response.body().getData().getMediumbasket().get(0).getDescription();
                        mediumBasketId = response.body().getData().getMediumbasket().get(0).getProducts_id();
                        medium_basket_list = response.body().getData().getMediumbasket().get(0).getBasket_product();
                        medium_product_price = response.body().getData().getMediumbasket().get(0).getProducts_price();
                    }
                    if (response.body().getData().getLargebasket() != null && response.body().getData().getLargebasket().size()>0) {
                        largedescription = response.body().getData().getLargebasket().get(0).getDescription();
                        largeBasketId = response.body().getData().getLargebasket().get(0).getProducts_id();
                        large_basket_list = response.body().getData().getLargebasket().get(0).getBasket_product();
                        large_product_price = response.body().getData().getLargebasket().get(0).getProducts_price();
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBasket> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    Runnable update;
    Handler handler;
    private void setbannerdata() {
        if (handler!=null) {
            handler.removeCallbacks(update);
        }
        handler = new Handler();
        update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                if (getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (page_position > 0) {
//                                txt_des.setText(slider_image_list.get(page_position - 1).getDescription());
                            }
                        }
                    });
                }
                vp_slider.setCurrentItem(page_position, true);
                handler.postDelayed(update,2000);
            }
        };
        handler.postDelayed(update,2000);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 100, 2500);

        vp_slider.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vp_slider.setPadding(60, 0, 60, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vp_slider.setPageMargin(20);

        if (slider_image_list != null && slider_image_list.size() > 0) {
            BasketBannerADapter sliderPagerAdapter = new BasketBannerADapter(getActivity(), slider_image_list);
            vp_slider.setAdapter(sliderPagerAdapter);
        }else {
            vp_slider.setVisibility(View.GONE);
        }
        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    CategoryListAdapter adapter;
    ArrayList<BasketBannerList> banner_list = new ArrayList<>();
    ArrayList<SmallBasketProductList> small_basket_list = new ArrayList<>();
    ArrayList<SmallBasketProductList> medium_basket_list = new ArrayList<>();
    ArrayList<SmallBasketProductList> large_basket_list = new ArrayList<>();


    private void setAdapter(ArrayList<SmallBasketProductList> _basket_list) {

        if (_basket_list != null && _basket_list.size() > 0) {
            adapter = new CategoryListAdapter(getActivity(), _basket_list, Vegetables.this);
            category_recycle.setAdapter(adapter);
        }else{
            _basket_list.clear();
            adapter = new CategoryListAdapter(getActivity(), _basket_list, Vegetables.this);
            category_recycle.setAdapter(adapter);
        }
    }


    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<SmallBasketProductList> data;
        List<SmallBasketProductList> list;
        Vegetables fragment;

        public CategoryListAdapter(Context c, List<SmallBasketProductList> data, Vegetables fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment = fragment;


        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            Picasso.with(getActivity())
                    .load(data.get(position).getImage())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);

            holder.txt_title.setText(data.get(position).getWeight());
            holder.txt_prductname.setText(data.get(position).getProducts_name());
//            holder.ll_drop_down.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(mContext,VehicleViewServiceMgmt.class);
//                    mContext.startActivity(intent);
//                }
//            });
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
                        List<SmallBasketProductList> filteredList = new ArrayList<>();
                        for (SmallBasketProductList row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getWeight().toLowerCase().contains(charString.toLowerCase())) {
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
                    data = (ArrayList<SmallBasketProductList>) filterResults.values;
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
            TextView order_id_txt, service_type_txt, order_status_txt, chat, call, order_status;
            TextView order_id, service_type;
            TextView reason;
            TextView order_form, type_of_sub_service, invoice_no;
            TextView quote, payment_mode, amount, sla_txt, ved_txt;
            TextView product_types, service_date_time, payment_status, edit_instruction_txt;
            ImageView drop_down;
            LinearLayout is_details_show, ll_order_detail, ll_assign_schedule, llassigned;
            CardView emp_doc_click, trade_lic_click, esta_card_click, service_letter_click, agreement_click;
            CardView emp_doc, service_letter_agreement, upload_premises_pass;
            RecyclerView assigned_staff_recycler;
            Spinner spnr_status;
            boolean is_touch = true;
            TextView txt_title,txt_prductname;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout ll_drop_down;
            CircleImageView image;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain = convertView.findViewById(R.id.crdmain);
                image = convertView.findViewById(R.id.image);
                txt_title = convertView.findViewById(R.id.txt_title);
                txt_prductname = convertView.findViewById(R.id.txt_prductname);
//            ll_order_detail =   convertView.findViewById(R.id.ll_order_detail);
//            ll_assign_schedule =   convertView.findViewById(R.id.ll_assign_schedule);

            }

        }


    }


}
