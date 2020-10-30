package com.android.ekishan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.R;
import com.android.ekishan.adapter.ProductsListAdapter;
import com.android.ekishan.fragment.ProductSearch;
import com.android.ekishan.fragment.VendorOrderDetails;
import com.android.ekishan.model.OrderDetailsData;
import com.android.ekishan.model.VendorOrderDetailData;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorOrderDetailsActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public static int int_items = 2;
    PagerSlidingTabStrip tabLayout;
    ImageView iv_back;
    EditText edSearchTitle;
    String order_id;
    VendorOrderDetailData searchData;
    RecyclerView recyclerView;
    ProductSearch postSearch;
    VendorOrderDetails peopleSearch;
    TextView tvHeaderName;
    ApiInterface apiService;
    List<VendorOrderDetailData.DataBean.ProductsBean> collection_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_tab_fragments);
        getSupportActionBar().hide();
        order_id = getIntent().getStringExtra("order_id");
        Log.e("order_id","id "+order_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tvHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(1);// no of fragments

        tabLayout.setViewPager(viewPager);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                //tabLayout.setupWithViewPager(viewPager);
                tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    // This method will be invoked when a new page becomes selected.
                    @Override
                    public void onPageSelected(int position) {
                        //   Toast.makeText(getApplicationContext(), "onPageSelected", Toast.LENGTH_SHORT).show();
                    }

                    // This method will be invoked when the current page is scrolled
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        // Code goes here
                        //Toast.makeText(getApplicationContext(), "onPageScrolled", Toast.LENGTH_SHORT).show();
                    }

                    // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
                    @Override
                    public void onPageScrollStateChanged(int state) {
                        // Code goes here
                        //Toast.makeText(getApplicationContext(), "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        getDetails();
    }

    private void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(VendorOrderDetailsActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<VendorOrderDetailData> responseCall = apiService.getVendorOrderDetail(order_id);//39
        responseCall.enqueue(new Callback<VendorOrderDetailData>() {
            @Override
            public void onResponse(Call<VendorOrderDetailData> call, Response<VendorOrderDetailData> response) {
                progressDialog.dismiss();
                //allSearch.updateAdapter(searchData);
                //searchData = new Gson().fromJson(response.toString(), VendorOrderDetailData.class);
                peopleSearch.updateAdapter(response.body().getData());
                postSearch.updateAdapter(response.body().getData());
                //collection_list=response.body().getData().getProducts();

            }

            @Override
            public void onFailure(Call<VendorOrderDetailData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(VendorOrderDetailsActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        /**
         * Return fragment with respect to Position .
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    peopleSearch = new VendorOrderDetails();
                    return peopleSearch; //all
                case 1:
                    postSearch = new ProductSearch();
                    return postSearch; //Category
            }
            return null;
        }


        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Items";
            }
            return null;
        }
    }
}
