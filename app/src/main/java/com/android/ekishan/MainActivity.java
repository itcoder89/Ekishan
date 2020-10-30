package com.android.ekishan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.activity.LoginActivity;
import com.android.ekishan.chat.CustomerSingleChatActivity;
import com.android.ekishan.delivery.DeliveryLoginActivity;
import com.android.ekishan.fragment.Dashbord;
import com.android.ekishan.fragment.MyCart;
import com.android.ekishan.fragment.MyOrder;
import com.android.ekishan.fragment.OrderSuccesFragment;
import com.android.ekishan.fragment.Profile;
import com.android.ekishan.fragment.Vegetables;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.android.ekishan.vendor.VendorLoginActivity;
import com.android.ekishan.vendor.VendorMainActivity;
import com.android.ekishan.vendor.fragment.ContactUsFragement;
import com.android.ekishan.view.CommonUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView ll_my_order;
    ImageView cart, ivDrawer;
    LinearLayout help, llprofile, llconversation, llsetting, vandorLogin, deliveryLogin;
    LinearLayout homeLayout, profileLayout;
    LinearLayout orderLayout, logoutLayout, buyButton;
    TextView login;
    ApiInterface apiService;
    ImageView logoClick;
    LinearLayout ll_language,chatsupportLayout;
    boolean is_hi;
    TextView txt_lang, txt_home,txt_my_prof,txt_my_order,txt_chat_suport,txt_login_for_farmer,txt_login_for_delivery,txt_cs,txt_pro
            ,txt_mo,txt_share,txt_not;
    private String version_name="";
    private String currentVersion="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        Log.d("Bundle", "VendorMainActivity");
        if (extras != null) {
            version_name = extras.getString("version_name");
            Log.e("version_name"," "+version_name);
        }
        if(!version_name.equals(currentVersion)){
            showDialoge();
        }
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        login = findViewById(R.id.login);

        txt_my_prof = findViewById(R.id.txt_my_prof);
        txt_chat_suport = findViewById(R.id.txt_chat_suport);
        txt_my_order = findViewById(R.id.txt_my_order);
        ll_language = findViewById(R.id.ll_language);
        chatsupportLayout = findViewById(R.id.chatsupportLayout);
        txt_lang = findViewById(R.id.txt_lang);
        txt_home = findViewById(R.id.txt_home);
        txt_login_for_farmer = findViewById(R.id.txt_login_for_farmer);
        txt_login_for_delivery = findViewById(R.id.txt_login_for_delivery);
        txt_mo = findViewById(R.id.txt_mo);
        txt_pro = findViewById(R.id.txt_pro);
        txt_cs = findViewById(R.id.txt_cs);
        txt_share = findViewById(R.id.txt_share);
        txt_not = findViewById(R.id.txt_not);
//        Locale current = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
//        if (current.getDisplayLanguage().equalsIgnoreCase("english")) {
//            txt_lang.setText("Hindi");
//            MyApplication.set_lang("en");
//            MyApplication.updateLocale("en", MainActivity.this);
//
//        } else {
//            txt_lang.setText("English");
//            MyApplication.updateLocale("hi", MainActivity.this);
//
//            MyApplication.set_lang("hi");
//        }

        try {
            currentVersion = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
            Log.e("currentVersion", ":"+currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ll_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.get_lang().equalsIgnoreCase("en")) {
                    MyApplication.set_lang("hi");
                    CommonUtil.setLocale("hi", MainActivity.this);
                    txt_lang.setText("English");
                    MyApplication.updateLocale("hi", MainActivity.this);
                } else {
                    MyApplication.set_lang("en");
                    CommonUtil.setLocale("en", MainActivity.this);
                    MyApplication.updateLocale("en", MainActivity.this);
                    txt_lang.setText("Hindi");
                }
                txt_home.setText(MyApplication.change_to_hindi(R.string.title_home, MainActivity.this));

                txt_my_prof.setText(MyApplication.change_to_hindi(R.string.my_profile, MainActivity.this));
                txt_my_order.setText(MyApplication.change_to_hindi(R.string.my_order, MainActivity.this));
                txt_chat_suport.setText(MyApplication.change_to_hindi(R.string.chat_support, MainActivity.this));
                txt_login_for_farmer.setText(MyApplication.change_to_hindi(R.string.login_for_farmer, MainActivity.this));
                txt_login_for_delivery.setText(MyApplication.change_to_hindi(R.string.login_for_deliver, MainActivity.this));
                txt_cs.setText(MyApplication.change_to_hindi(R.string.customer_support, MainActivity.this));
                login.setText(MyApplication.change_to_hindi(R.string.login, MainActivity.this));
                txt_pro.setText(MyApplication.change_to_hindi(R.string.profile, MainActivity.this));
                txt_mo.setText(MyApplication.change_to_hindi(R.string.my_order, MainActivity.this));
                txt_share.setText(MyApplication.change_to_hindi(R.string.share, MainActivity.this));
                txt_not.setText(MyApplication.change_to_hindi(R.string.notification, MainActivity.this));
                loadDashboard(new Dashbord());
            }
        });
        homeLayout = findViewById(R.id.homeLayout);
        vandorLogin = findViewById(R.id.vandorLogin);
        deliveryLogin = findViewById(R.id.deliveryLogin);
        profileLayout = findViewById(R.id.profileLayout);
        orderLayout = findViewById(R.id.orderLayout);

        logoutLayout = findViewById(R.id.logoutLayout);
        buyButton = findViewById(R.id.buyButton);
        cart = findViewById(R.id.cart);
        ivDrawer = findViewById(R.id.ivDrawer);
        ll_my_order = findViewById(R.id.ll_my_order);
        help = findViewById(R.id.help);
        llsetting = findViewById(R.id.llsetting);
        llprofile = findViewById(R.id.llprofile);
        llconversation = findViewById(R.id.llconversation);

        logoClick = findViewById(R.id.logoClick);
        if (getIntent().hasExtra("paymentSuccess")) {
            if (MyApplication.jsonObject1 != null) {
                try {
                    if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof OrderSuccesFragment)) {
                        loadFragment(new OrderSuccesFragment(MyApplication.jsonObject1.getString("orders_id"),MyApplication.jsonObject1.getString("order_date"),MyApplication.jsonObject1.getString("order_price"),MyApplication.jsonObject1.getString("total_items")));
                        MyApplication.jsonObject1=null;
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!MyApplication.isLogin()){
            MyApplication.setCustomerId("0");
            MyApplication.setMobile("");
        }
        if (MyApplication.isLogin()) {
            login.setText(R.string.logout);
        } else {
            login.setText(getString(R.string.login));
        }
        deliveryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeliveryLoginActivity.class));
                finish();
            }
        });
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin()) {
                    if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Vegetables)) {
                        if (MyApplication.basketId!=null) {
                            if (!MyApplication.basketId.equalsIgnoreCase("")) {
                                addToCart();
                            } else {
                                MyApplication.showAlert(MainActivity.this,"This basket not available now!");
                            }
                        }else {
                            MyApplication.showAlert(MainActivity.this,"This basket not available now!");
                        }
                    }else {
                        MyApplication.showAlert(MainActivity.this,"It's only working when buy basket.");
                    }
                }else {
                    MyApplication.showAlert(MainActivity.this,"Please login first!");
                }
            }
        });
        llsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof NotificationsFragment)) {
                    loadFragment(new NotificationsFragment());
                }
            }
        });
        ll_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyOrder)) {
                    loadFragment(new MyOrder());
                }
            }
        });
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin()) {
                    login.setText(getString(R.string.logout));
                } else {
                    login.setText(R.string.login);
                }
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyCart)) {
                    loadFragment(new MyCart());
                }
            }
        });
        llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Profile)) {
                    loadFragment(new Profile());
                }
            }
        });
        chatsupportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin()) {
                    startActivity(new Intent(MainActivity.this, CustomerSingleChatActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"login required",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ContactUsFragement)) {
                    loadDashboard(new ContactUsFragement());
                }
            }
        });
        llconversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin()) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey, Use my refer code " + MyApplication.getReferCode() + " and get 10% discount on your first order. Download EKISAN App at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }else {
                    MyApplication.showAlert(MainActivity.this,"Please login first!");
                }
            }
        });
        logoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Dashbord)) {
                    loadDashboard(new Dashbord());
                }
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Dashbord)) {
                    loadDashboard(new Dashbord());
                }
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Profile)) {
                    loadFragment(new Profile());
                }
            }
        });
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyOrder)) {
                    loadFragment(new MyOrder());
                }
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                SharedPreferences preferences =getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                if (MyApplication.isLogin()){
                    finish();
                }else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
        vandorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, VendorLoginActivity.class));
                finish();

            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);
        PrintHashKey();
        loadDashboard(new Dashbord());
    }
    private void addToCart() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Adding to cart...");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.addToCart(MyApplication.getCustomerId(),"0","0","1",MyApplication.basketId);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        getSupportFragmentManager().popBackStack();
                    }else {
                        Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void showDialoge() {
        final Dialog dialog = new Dialog(MainActivity.this);
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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                    //Toast.makeText(getApplicationContext(), "There is newer version of this application available.please upgrade now.", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException anfe) {

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void PrintHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.print("Key hash is : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private boolean loadDashboard(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (MyApplication.isLogin()) {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        }else {
            MyApplication.showAlert(this,"Please login first!");
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof OrderSuccesFragment)) {
                loadFragment(new Dashbord());
            }else if (getSupportFragmentManager().getBackStackEntryCount() > 1 && !(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Dashbord)) {
                getSupportFragmentManager().popBackStack();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
//                super.onBackPressed();
            }
        }
    }

}
