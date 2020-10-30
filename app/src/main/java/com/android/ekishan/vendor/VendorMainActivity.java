package com.android.ekishan.vendor;

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
import com.android.ekishan.AppSignatureHelper;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.LoginActivity;
import com.android.ekishan.activity.TabCollections;
import com.android.ekishan.chat.SingleChatActivity;
import com.android.ekishan.chat.UserListActivity;
import com.android.ekishan.fragment.CollectionsReportFragment;
import com.android.ekishan.fragment.Dashbord;
import com.android.ekishan.fragment.MyCart;
import com.android.ekishan.fragment.MyOrder;
import com.android.ekishan.fragment.OrderSuccesFragment;
import com.android.ekishan.fragment.Profile;
import com.android.ekishan.fragment.SalesReportFragment;
import com.android.ekishan.fragment.TodayCollectionsFragment;
import com.android.ekishan.fragment.Vegetables;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.android.ekishan.vendor.fragment.ContactUsFragement;
import com.android.ekishan.vendor.fragment.VendorDashbord;
import com.android.ekishan.vendor.fragment.VendorProfileFragment;
import com.android.ekishan.view.CommonUtil;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorMainActivity extends AppCompatActivity {
    ImageView ll_my_order;
    ImageView cart, ivDrawer;
    LinearLayout llprofile, llconversation, llsetting;
    LinearLayout homeLayout, profileLayout,homedashboard;
    LinearLayout orderLayout, logoutLayout, buyButton;
    TextView login;
    ApiInterface apiService;
    TextView txt_lang,txt_home,txt_profile,txt_edt_veg,txt_help,txt_todaycollections,txt_collections,txt_salerequest,txt_collections_tab;
    LinearLayout ll_language,todaycollectionLayout,collectionLayout,todaysalerequestLayout,collectionLayoutTab,chatsupport;
    boolean is_hi;
    private String version_name="";
    private String currentVersion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_main_layout);
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
        MyApplication.setVendorIsLogin(true);
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(VendorMainActivity.this);
        Log.e("appSignatureHelper",":"+appSignatureHelper);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        txt_todaycollections = findViewById(R.id.txt_todaycollections);
        txt_collections_tab = findViewById(R.id.txt_collections_tab);
        txt_salerequest = findViewById(R.id.txt_salerequest);
        txt_collections = findViewById(R.id.txt_collections);
        login = findViewById(R.id.login);
        homeLayout = findViewById(R.id.homeLayout);
        homedashboard = findViewById(R.id.homedashboard);
        profileLayout = findViewById(R.id.profileLayout);
        orderLayout = findViewById(R.id.orderLayout);
        logoutLayout = findViewById(R.id.logoutLayout);
        buyButton = findViewById(R.id.buyButton);
        cart = findViewById(R.id.cart);
        ivDrawer = findViewById(R.id.ivDrawer);
        ll_my_order = findViewById(R.id.ll_my_order);
        llsetting = findViewById(R.id.llsetting);
        llprofile = findViewById(R.id.llprofile);
        llconversation = findViewById(R.id.llconversation);
        txt_lang = findViewById(R.id.txt_lang);
        ll_language = findViewById(R.id.ll_language);
        chatsupport = findViewById(R.id.chatsupport);
        chatsupport.setVisibility(View.GONE);
        //hide collection tab
        collectionLayoutTab = findViewById(R.id.collectionLayoutTab);
        collectionLayoutTab.setVisibility(View.GONE);
        //hide sale request
        todaysalerequestLayout = findViewById(R.id.todaysalerequestLayout);
        todaysalerequestLayout.setVisibility(View.GONE);
        //hide today collection
        todaycollectionLayout = findViewById(R.id.todaycollectionLayout);
        todaycollectionLayout.setVisibility(View.GONE);
        //hide only collection view
        collectionLayout = findViewById(R.id.collectionLayout);
        collectionLayout.setVisibility(View.GONE);

        txt_home = findViewById(R.id.txt_home);
        txt_profile = findViewById(R.id.txt_profile);
        txt_edt_veg = findViewById(R.id.txt_edt_veg);
        txt_help = findViewById(R.id.txt_help);
//        Locale current = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
//        if (current.getDisplayLanguage().equalsIgnoreCase("english")) {
//            txt_lang.setText("Hindi");
////            is_hi = false;
//            MyApplication.updateLocale("en", VendorMainActivity.this);
//
//        } else {
//            txt_lang.setText("English");
//            MyApplication.updateLocale("hi", VendorMainActivity.this);
//
////            is_hi = true;
//        }

        try {
            currentVersion = VendorMainActivity.this.getPackageManager().getPackageInfo(VendorMainActivity.this.getPackageName(), 0).versionName;
            Log.e("currentVersion", ":"+currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ll_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.get_lang().equalsIgnoreCase("en")) {
                    MyApplication.set_lang("hi");
                    CommonUtil.setLocale("hi", VendorMainActivity.this);
                    txt_lang.setText("English");
                    MyApplication.updateLocale("hi", VendorMainActivity.this);
                } else {
                    MyApplication.set_lang("en");
                    CommonUtil.setLocale("en", VendorMainActivity.this);
                    MyApplication.updateLocale("en", VendorMainActivity.this);
                    txt_lang.setText("Hindi");
                }
                txt_home.setText(MyApplication.change_to_hindi(R.string.title_home, VendorMainActivity.this));
                txt_collections_tab.setText(MyApplication.change_to_hindi(R.string.tab_collections, VendorMainActivity.this));
                txt_salerequest.setText(MyApplication.change_to_hindi(R.string.sale_request, VendorMainActivity.this));
                txt_todaycollections.setText(MyApplication.change_to_hindi(R.string.today_collections, VendorMainActivity.this));
                txt_collections.setText(MyApplication.change_to_hindi(R.string.collection, VendorMainActivity.this));
                txt_profile.setText(MyApplication.change_to_hindi(R.string.profile, VendorMainActivity.this));
                txt_edt_veg.setText(MyApplication.change_to_hindi(R.string.edit_veg, VendorMainActivity.this));
                txt_help.setText(MyApplication.change_to_hindi(R.string.help, VendorMainActivity.this));

                login.setText(MyApplication.change_to_hindi(R.string.logout, VendorMainActivity.this));

                loadDashboard(new VendorDashbord());
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
        homedashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof VendorDashbord)) {
                    loadFragment(new VendorDashbord());
                }
            }
        });
        todaycollectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof TodayCollectionsFragment)) {
                    loadFragment(new TodayCollectionsFragment());
                }
            }
        });
        collectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof CollectionsReportFragment)) {
                    loadFragment(new CollectionsReportFragment());
                }
            }
        });
        /*todaysalerequestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof SalesReportFragment)) {
                    loadFragment(new SalesReportFragment());
                }
            }
        });*/
        collectionLayoutTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(VendorMainActivity.this, TabCollections.class));
            }
        });
        /*chatsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                //startActivity(new Intent(VendorMainActivity.this, UserListActivity.class));
                startActivity(new Intent(VendorMainActivity.this, SingleChatActivity.class));
            }
        });*/

        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
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
        llconversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof VendorProfileFragment)) {
                    loadDashboard(new VendorProfileFragment());
                }
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(VendorMainActivity.this, AddEditVegetableActivity.class);
                intent.putExtra("is_from","EDIT");
                startActivity(intent);
            }
        });
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ContactUsFragement)) {
                    loadDashboard(new ContactUsFragement());
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
                startActivity(new Intent(VendorMainActivity.this, MainActivity.class));
                finish();
            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);
        PrintHashKey();
        loadDashboard(new VendorDashbord());
    }

    void showDialoge() {
        final Dialog dialog = new Dialog(VendorMainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_update_app);
        dialog.setCanceledOnTouchOutside(false);
        //TextView tv_later = (TextView) dialog.findViewById(R.id.tv_later);
        TextView tvUpdateContent = (TextView) dialog.findViewById(R.id.tvUpdateContent);
        TextView tv_update = (TextView) dialog.findViewById(R.id.tv_update);
        //tvUpdateContent.setText(updateMsg);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + VendorMainActivity.this.getPackageName())));
                    //Toast.makeText(getApplicationContext(), "There is newer version of this application available.please upgrade now.", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException anfe) {

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addToCart() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Adding to cart...");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.addToCart(MyApplication.getCustomerId(), MyApplication.basketId, "0", "1","0");


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        getSupportFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

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

    private void PrintHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.print("Key hash is : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
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
        if (MyApplication.isVendorLogin()) {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        } else {
            MyApplication.showAlert(this,"Please login first!");
        }
        return false;
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
            } else if (getSupportFragmentManager().getBackStackEntryCount() > 1 && !(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof Dashbord)) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
//                super.onBackPressed();
            }
        }
    }
}
