package com.android.ekishan.delivery;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.DeliverDashboarResponse;
import com.android.ekishan.model.DeliveryDashboardData;
import com.android.ekishan.model.NewDeliveryBoyData;
import com.android.ekishan.model.ResponseDeliveryBoyAcceptOrder;
import com.android.ekishan.vendor.VendorMainActivity;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by ${} on 6/10/2020.
 */
public class DeliveryDashboardActivity extends AppCompatActivity implements LocationManagerInterface {
    RecyclerView Recycler;
    List<DeliveryDashboardData> data = new ArrayList<>();
    List<NewDeliveryBoyData.DataBean> newdata = new ArrayList<>();
    ApiInterface apiService;
    TextView logout,no_found;
    SmartLocationManager mLocationManager;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION =1002 ;
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    ImageView history;
    private String version_name="";
    private String currentVersion="";
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.delivery_dashboard_list);
         Bundle extras = getIntent().getExtras();
         Log.d("Bundle", "VendorMainActivity");
         if (extras != null) {
             version_name = extras.getString("version_name");
             Log.e("version_name"," "+version_name);
         }
         if(!version_name.equals(currentVersion)){
             showDialoge();
         }
        apiService = ApiClient.getClient().create(ApiInterface.class);
         history = findViewById(R.id.history);
        Recycler = findViewById(R.id.Recycler);
        logout = findViewById(R.id.logout);
        no_found = findViewById(R.id.no_found);
        Recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(DeliveryDashboardActivity.this, MainActivity.class));
                finish();
            }
        });
         history.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(DeliveryDashboardActivity.this, DeliveryBoyHistoryActivity.class));
             }
         });

         try {
             currentVersion = DeliveryDashboardActivity.this.getPackageManager().getPackageInfo(DeliveryDashboardActivity.this.getPackageName(), 0).versionName;
             Log.e("currentVersion", ":"+currentVersion);
         } catch (PackageManager.NameNotFoundException e) {
             e.printStackTrace();
         }

    }

    void showDialoge() {
        final Dialog dialog = new Dialog(DeliveryDashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_update_app);
        dialog.setCanceledOnTouchOutside(false);
        //TextView tv_later = (TextView) dialog.findViewById(R.id.tv_later);
        TextView tvUpdateContent = (TextView) dialog.findViewById(R.id.tvUpdateContent);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
       // tvUpdateContent.setText(updateMsg);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + DeliveryDashboardActivity.this.getPackageName())));
                    //Toast.makeText(getApplicationContext(), "There is newer version of this application available.please upgrade now.", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException anfe) {

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //data.clear();
        newdata.clear();
        dashboard();
    }

    private void requestLocationPermission() {

        // Check for permission
        if (ContextCompat.checkSelfPermission(DeliveryDashboardActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }else{
            mLocationManager = new SmartLocationManager(getApplicationContext(), DeliveryDashboardActivity.this, DeliveryDashboardActivity.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE); // init location manager

        }

    }

    // Get permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    mLocationManager = new SmartLocationManager(getApplicationContext(), DeliveryDashboardActivity.this, DeliveryDashboardActivity.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE); // init location manager

                } else {
                    // permission was denied
                }
                return;
            }
        }
    }
    boolean is_touch;


    protected void onStart() {
        super.onStart();
        if (mLocationManager != null)
            mLocationManager.startLocationFetching();
    }

    protected void onStop() {
        super.onStop();
        if (mLocationManager != null)
            mLocationManager.abortLocationFetching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null)
            mLocationManager.pauseLocationFetching();
    }
    double c_lat,c_long;
    @Override
    public void locationFetched(Location mLocal, Location oldLocation, String time, String locationProvider) {
//        Toast.makeText(getApplication(), "Lat : " + mLocal.getLatitude() + " Lng : " + mLocal.getLongitude(), Toast.LENGTH_LONG).show();
//        mLocalTV.setText("Lat : " + mLocal.getLatitude() + " Lng : " + mLocal.getLongitude());
//        edt_location.setText(locationProvider);
//        mlocationTimeTV.setText(time);
        c_lat=mLocal.getLatitude();
        c_long=mLocal.getLongitude();
        mLocationManager.pauseLocationFetching();
        mLocationManager.abortLocationFetching();
        if (data.size()>0) {
            setAdapter();
        }else {
            Recycler.setVisibility(View.GONE);
            no_found.setVisibility(View.VISIBLE);
        }

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

//        try {
//            addresses = geocoder.getFromLocation(mLocal.getLatitude(), mLocal.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();
//            edt_location.setText(address);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private void dashboard() {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryDashboardActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<DeliverDashboarResponse> responseCall = apiService.deliveryBoyOrdersRequest(MyApplication.get_delivery_id(),MyApplication.get_delivery_type());


        responseCall.enqueue(new Callback<DeliverDashboarResponse>() {
            @Override
            public void onResponse(Call<DeliverDashboarResponse> call, Response<DeliverDashboarResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    data.clear();
                    newdata.clear();
                    data = response.body().getData();
                    requestLocationPermission();
                    if (data.size()>0) {
//                        setAdapter();

                    }else {
                        Recycler.setVisibility(View.GONE);
                        no_found.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(DeliveryDashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<DeliverDashboarResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
              //  Toast.makeText(DeliveryDashboardActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deliveryBoyAcceptDeclineOrder(NewDeliveryBoyData.DataBean dataBean, String agro_item_id, String from, int position, String id, String resp, String order_id, String reason, String subscription_id, String package_delivery_id, String vendor_sales_id, String order_type) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryDashboardActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseDeliveryBoyAcceptOrder> responseCall = apiService.deliveryBoyAcceptDeclineOrder(agro_item_id,MyApplication.get_delivery_id(), resp, order_id,subscription_id,package_delivery_id, reason,vendor_sales_id,order_type);
        responseCall.enqueue(new Callback<ResponseDeliveryBoyAcceptOrder>() {
            @Override
            public void onResponse(Call<ResponseDeliveryBoyAcceptOrder> call, Response<ResponseDeliveryBoyAcceptOrder> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess() == 1) {
                    if (from.equalsIgnoreCase("ACCEPT")){
                        MyApplication.vendor_sales_id=vendor_sales_id;
                        Intent intent = new Intent(DeliveryDashboardActivity.this, MApActivity.class);
                        intent.putExtra("id", MyApplication.get_delivery_id());
                        intent.putExtra("order_id", dataBean.getOrders_id());
                        intent.putExtra("agro_item_id", dataBean.getAgro_item_id());
                        intent.putExtra("subscription_id", dataBean.getSubscription_id());
                        intent.putExtra("package_delivery_id", dataBean.getPackage_delivery_id());
                        intent.putExtra("payment_status", dataBean.getPayment_status());
                        intent.putExtra("order_type", dataBean.getOrder_type());
                        intent.putExtra("name", dataBean.getName());
                        intent.putExtra("mobile", dataBean.getMobile());
                        intent.putExtra("drop_lat", dataBean.getLat());
                        intent.putExtra("drop_long", dataBean.getLongX());
                        startActivity(intent);
                    }else {
                        dashboard();
                        Toast.makeText(DeliveryDashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeliveryDashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseDeliveryBoyAcceptOrder> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                //Toast.makeText(DeliveryDashboardActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    MyOrderListAdapter adapter;

    private void setAdapter() {
        if (data != null && data.size() > 0) {
            for(int i=0;i<data.size();i++){
                double lat= Double.parseDouble(data.get(i).getLat());
                double longi= Double.parseDouble(data.get(i).getLongs());
                double distance_in_km=distance(lat,c_lat,longi,c_long);
                if (lat==0.0 & longi==0.0) {
                    data.get(i).setDistance("0");
                }else {
                    data.get(i).setDistance("" + distance_in_km);
                }
                List<DeliveryDashboardData> list = new ArrayList<>();
                //Here, I provide a Comparator to sort Items by number.

            }
            Collections.sort(data, new Comparator<DeliveryDashboardData>() {
                @Override
                public int compare(DeliveryDashboardData o1, DeliveryDashboardData o2) {
                    return ((int)((Double.parseDouble(o2.getDistance())) - (int)(Double.parseDouble(o1.getDistance()))));
                }
            });
            Collections.reverse(data);

            for(int i=0;i<data.size();i++){
                NewDeliveryBoyData.DataBean newDeliveryBoyData= new NewDeliveryBoyData.DataBean();
              if(data.get(i).getOrder_status().equals("Delivered")){
                  //nothing to do
              }else{
                  newDeliveryBoyData.setVendors_sales_id(data.get(i).getVendors_sales_id());
                  newDeliveryBoyData.setOrders_date(data.get(i).getOrders_date());
                  newDeliveryBoyData.setPayment_status(data.get(i).getPayment_status());
                  newDeliveryBoyData.setOrder_status(data.get(i).getOrder_status());
                  newDeliveryBoyData.setName(data.get(i).getName());
                  newDeliveryBoyData.setLat(data.get(i).getLat());
                  newDeliveryBoyData.setLongX(data.get(i).getLongs());
                  newDeliveryBoyData.setMobile(data.get(i).getMobile());
                  newDeliveryBoyData.setSubscription_id(data.get(i).getSubscription_id());
                  newDeliveryBoyData.setPackage_delivery_id(data.get(i).getPackage_delivery_id());
                  newDeliveryBoyData.setOrders_id(data.get(i).getOrders_id());
                  newDeliveryBoyData.setOrder_type(data.get(i).getOrder_type());
                  newDeliveryBoyData.setAgro_item_id(data.get(i).getAgro_item_id());
                  newdata.add(newDeliveryBoyData);
              }
            }
            Log.e("newarraysize","size "+newdata.size());
            if (newdata.size()>0) {
                adapter = new MyOrderListAdapter(getApplication(), newdata);
                Recycler.setAdapter(adapter);
            }else {
                Recycler.setVisibility(View.GONE);
                no_found.setVisibility(View.VISIBLE);
            }

        }
    }

    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
       // List<DeliveryDashboardData> data;
        List<NewDeliveryBoyData.DataBean> data;
        String password="";
        public MyOrderListAdapter(Context c, List<NewDeliveryBoyData.DataBean> data) {
            mContext = c;
            this.data = data;
        }

        @Override
        public MyOrderListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delivery_dashboard_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            MyOrderListAdapter.CustomViewHolder viewHolder = new MyOrderListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyOrderListAdapter.CustomViewHolder holder, final int position) {
            if (data.get(position).getOrder_status().equalsIgnoreCase("assigned")||data.get(position).getOrder_status().equalsIgnoreCase("pending")){
                holder.accept.setVisibility(View.VISIBLE);
                holder.btn_decline.setVisibility(View.VISIBLE);
                holder.declined.setVisibility(View.GONE);
                holder.accepted.setVisibility(View.GONE);
                holder.delivered.setVisibility(View.GONE);
                holder.viewDetails.setVisibility(View.GONE);
            }else if (data.get(position).getOrder_status().equalsIgnoreCase("declined")){
                holder.accept.setVisibility(View.GONE);
                holder.btn_decline.setVisibility(View.GONE);
                holder.declined.setVisibility(View.VISIBLE);
                holder.accepted.setVisibility(View.GONE);
                holder.delivered.setVisibility(View.GONE);
                holder.viewDetails.setVisibility(View.GONE);
            }else if (data.get(position).getOrder_status().equalsIgnoreCase("accepted")){
                holder.accept.setVisibility(View.GONE);
                holder.btn_decline.setVisibility(View.GONE);
                holder.declined.setVisibility(View.GONE);
                holder.accepted.setVisibility(View.VISIBLE);
                holder.viewDetails.setVisibility(View.VISIBLE);
                holder.delivered.setVisibility(View.GONE);
            }else if (data.get(position).getOrder_status().equalsIgnoreCase("delivered")){
                holder.accept.setVisibility(View.GONE);
                holder.btn_decline.setVisibility(View.GONE);
                holder.declined.setVisibility(View.GONE);
                holder.accepted.setVisibility(View.GONE);
                holder.viewDetails.setVisibility(View.GONE);
                holder.delivered.setVisibility(View.GONE);
            }
            holder.order_no.setText("" + data.get(position).getName());
            holder.txt_mobile.setText("" + data.get(position).getMobile());
            holder.tv_order_date.setText(data.get(position).getOrders_date());

            if(data.get(position).getDistance() != null)
                holder.distance.setText(data.get(position).getDistance().split("\\.")[0]+"Km");

            if (!data.get(position).getOrders_id().equalsIgnoreCase("0")){
                holder.order_id.setText("Order No." + data.get(position).getOrders_id());
            }else if (!data.get(position).getSubscription_id().equalsIgnoreCase("0")){
                holder.order_id.setText("Subscription No. " + data.get(position).getSubscription_id());
            }else if (!data.get(position).getAgro_item_id().equalsIgnoreCase("0")){
                holder.order_id.setText("Agro Item No. " + data.get(position).getAgro_item_id());
            }else if (data.get(position).getOrder_type().equalsIgnoreCase("DEMAND")){
                holder.order_id.setText("Demand No. " + data.get(position).getVendors_sales_id());
            }else {
                holder.order_id.setText("Sell No. " + data.get(position).getVendors_sales_id());
            }
//            holder.order_id.setText(data.get(position).getSubscription_id().equalsIgnoreCase("0")?"Order No. " + data.get(position).getOrders_id():"Subscription No. " + data.get(position).getSubscription_id());


            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(mContext,MApActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    deliveryBoyAcceptDeclineOrder(data.get(position),data.get(position).getAgro_item_id(),"ACCEPT",position,MyApplication.get_delivery_id(),"1",data.get(position).getOrders_id()+"","",data.get(position).getSubscription_id(),data.get(position).getPackage_delivery_id(),data.get(position).getVendors_sales_id(),data.get(position).getOrder_type());
                }
            });
            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeliveryDashboardActivity.this, MApActivity.class);
                    intent.putExtra("id", MyApplication.get_delivery_id());
                    intent.putExtra("order_id", data.get(position).getOrders_id());
                    intent.putExtra("agro_item_id", data.get(position).getAgro_item_id());
                    intent.putExtra("subscription_id", data.get(position).getSubscription_id());
                    intent.putExtra("package_delivery_id", data.get(position).getPackage_delivery_id());
                    intent.putExtra("payment_status", data.get(position).getPayment_status());
                    intent.putExtra("name", data.get(position).getName());
                    intent.putExtra("mobile", data.get(position).getMobile());
                    intent.putExtra("drop_lat", data.get(position).getLat());
                    intent.putExtra("drop_long", data.get(position).getLongX());
                    intent.putExtra("order_type", data.get(position).getOrder_type());
                    MyApplication.vendor_sales_id=data.get(position).getVendors_sales_id();
                    startActivity(intent);
                }
            });
            holder.btn_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeliveryDashboardActivity.this);
                    alertDialog.setMessage("Enter Reason");
                    final EditText input = new EditText(DeliveryDashboardActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    password = input.getText().toString();
                                    deliveryBoyAcceptDeclineOrder(data.get(position),data.get(position).getAgro_item_id(),"DECLINE",position,MyApplication.get_delivery_id(),"0",data.get(position).getOrders_id()+"",password,data.get(position).getSubscription_id(),data.get(position).getPackage_delivery_id(),data.get(position).getVendors_sales_id(),data.get(position).getOrder_type());

                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }


            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            Button accept,btn_decline;
            Button accepted,declined,delivered,viewDetails;
            LinearLayout llLayout;
            TextView order_id, order_no, txt_mobile,distance,tv_order_date;

            public CustomViewHolder(View convertView) {
                super(convertView);
                llLayout = convertView.findViewById(R.id.llLayout);
                accept = convertView.findViewById(R.id.accept);
                btn_decline = convertView.findViewById(R.id.btn_decline);
                order_id = convertView.findViewById(R.id.order_id);
                order_no = convertView.findViewById(R.id.order_no);
                txt_mobile = convertView.findViewById(R.id.txt_mobile);
                accepted = convertView.findViewById(R.id.accepted);
                declined = convertView.findViewById(R.id.declined);
                delivered = convertView.findViewById(R.id.delivered);
                viewDetails = convertView.findViewById(R.id.viewDetails);
                distance = convertView.findViewById(R.id.distance);
                tv_order_date = convertView.findViewById(R.id.tv_order_date);
            }

        }
    }
    private double distance(double lat1, double lat2, double lon1, double lon2) {
        double distance;
        Location locationA = new Location("Point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);

        Location locationB = new Location("Point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);

// distance = locationA.distanceTo(locationB);   // in meters
        distance = locationA.distanceTo(locationB)/1000;
        return distance;
    }
//    private double distance(double lat1, double lon1, double lat2, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        System.exit(0);
        super.onBackPressed();
    }
}
