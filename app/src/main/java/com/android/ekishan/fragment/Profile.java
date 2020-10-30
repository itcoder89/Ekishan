package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.CircleTransform;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.LoginActivity;
import com.android.ekishan.activity.OtpActivity;
import com.android.ekishan.model.ProfileData;
import com.android.ekishan.model.ReponseProfile;
import com.android.ekishan.model.ResponseLogin;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile<Textview> extends Fragment {
    ImageView location_edit;
    ApiInterface apiService;
    ProfileData model;
    TextView txt_name;
    RelativeLayout myWallet,rledit;
    LinearLayout myOrders,mySubscription,deliverAddress,notication,logout;
    LinearLayout reviewll;
    CircleImageView customerProfile;
    TextView login,email,mobile,wallet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        rledit=view.findViewById(R.id.rledit);
        deliverAddress=view.findViewById(R.id.deliverAddress);
        email=view.findViewById(R.id.email);
        wallet=view.findViewById(R.id.wallet);
        mobile=view.findViewById(R.id.mobile);
        login=view.findViewById(R.id.login);
        logout=view.findViewById(R.id.logout);
        reviewll=view.findViewById(R.id.reviewll);
        notication=view.findViewById(R.id.notication);
        location_edit=view.findViewById(R.id.location_edit);
        myWallet=view.findViewById(R.id.myWallet);
        myOrders=view.findViewById(R.id.myOrders);
        customerProfile=view.findViewById(R.id.customerProfile);
        mySubscription=view.findViewById(R.id.mySubscription);
        location_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof EditLocation)) {
                    loadFragment(new EditLocation());
                }
            }
        });
        myWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyWalletFragment)) {
                    loadFragment(new MyWalletFragment());
                }
            }
        });
        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof MyOrder)) {
                    loadFragment(new MyOrder());
                }
            }
        });
        deliverAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof EditLocation)) {
                    loadFragment(new EditLocation());
                }
            }
        });
        notication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof NotificationsFragment)) {
                    loadFragment(new NotificationsFragment());
                }
            }
        });
        rledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof EditProfileFragment)) {
                    loadFragment(new EditProfileFragment());
                }

            }
        });
        reviewll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ReviewRatingFragment)) {
                    loadFragment(new ReviewRatingFragment());
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getActivity().getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                if (MyApplication.isLogin()){
                    getActivity().finish();
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });
        mySubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof MySubscription)) {
                    loadFragment(new MySubscription());
                }
            }
        });
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        txt_name=view.findViewById(R.id.txt_name);
        get_profile();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin()){
            login.setText(getString(R.string.logout));
        }else {
            login.setText(getString(R.string.login));
        }
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

    private void get_profile() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ReponseProfile> responseCall = apiService.getProfile(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ReponseProfile>() {
            @Override
            public void onResponse(Call<ReponseProfile> call, Response<ReponseProfile> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    model=response.body().getData();
                    setdata(model);
                }
//                    Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();
//                }
//                    Toast.makeText(LoginActivity.this, "response.body().getError()", Toast.LENGTH_SHORT).show();

//                if (response.body().getStatus()) {
//                    if (response.body().getData() != null) {
//
//                    }
//                } else {
//                }
            }

            @Override
            public void onFailure(Call<ReponseProfile> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setdata(ProfileData model) {
        txt_name.setText(""+model.getCustomers_firstname());
        email.setText(model.getCustomers_email_address());
        mobile.setText(model.getCustomers_telephone());
        wallet.setText("Rs "+model.getWallet());
        if (model.getCustomers_picture()!=null&&!model.getCustomers_picture().equalsIgnoreCase("")) {
            Picasso.with(getActivity())
                    .load(model.getCustomers_picture())
                    .placeholder(R.drawable.defoult_img1) // optional
                    .error(R.drawable.defoult_img1)
                    .transform(new CircleTransform())// optional
                    .into(customerProfile);
        }
    }

}
