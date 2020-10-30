package com.android.ekishan.delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.SocialLoginRequest;
import com.android.ekishan.model.SocialLoginResponse;
import com.android.ekishan.model.UpdateProfileResponse;
import com.android.ekishan.model.VendorResponseRegister;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryLoginActivity extends AppCompatActivity {
    RelativeLayout rlLogin;
    ApiInterface apiService;
    EditText edt_mobile, edtname, edtemail,edtregmobile,edtaadhar;
    TextView header;
    LinearLayout llname, llemail, ll_login_mobile,ll_register;
    //    CardView googleLogin,fbLogin;
//    private GoogleSignInClient mGoogleSignInClient;
//    private int RC_SIGN_IN = 1001;
//    private CallbackManager callbackManager;
    TextView txt_register,tvLogin,txt_label,txt_fill;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        setContentView(R.layout.delivery_login);
        getSupportActionBar().hide();
//        googleLogin = findViewById(R.id.googleLogin);
//        fbLogin = findViewById(R.id.fbLogin);
        rlLogin = findViewById(R.id.rlLogin);
        edt_mobile = findViewById(R.id.edt_mobile);
        txt_register = findViewById(R.id.txt_register);
        txt_label = findViewById(R.id.txt_label);
        txt_fill = findViewById(R.id.txt_fill);
        header = findViewById(R.id.header);
        llname = findViewById(R.id.llname);
        llemail = findViewById(R.id.llemail);
        ll_login_mobile = findViewById(R.id.ll_login_mobile);
        ll_register = findViewById(R.id.ll_register);
        tvLogin = findViewById(R.id.tvLogin);
        edtname = findViewById(R.id.edtname);
        edtemail = findViewById(R.id.edtemail);
        edtregmobile = findViewById(R.id.edtregmobile);
        edtaadhar = findViewById(R.id.edtaadhar);
//        callbackManager = CallbackManager.Factory.create();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        getToken();
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                header.setText(getString(R.string.register));
                ll_register.setVisibility(View.VISIBLE);
                txt_fill.setVisibility(View.VISIBLE);
                txt_label.setText(getString(R.string.new_vendor));
                ll_login_mobile.setVisibility(View.GONE);
                tvLogin.setText(getString(R.string.register));
            }
        });


        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (header.getText().toString().equalsIgnoreCase("login")) {
//                    if (edt_mobile.getText().toString().length() == 10) {
//                        login(edt_mobile.getText().toString());
//                    } else {
//                        Toast.makeText(DeliveryLoginActivity.this, "Please enter valid mobile", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
                    if (edt_mobile.getText().toString().length() == 10 && edt_mobile.toString().length() > 0) {
//                        startActivity(new Intent(DeliveryLoginActivity.this,DeliverOtpActivity.class));
//                        finish();
                        login(edt_mobile.getText().toString());
                    }else {
                        Toast.makeText(DeliveryLoginActivity.this, "Please enter valid mobile", Toast.LENGTH_SHORT).show();
                    }
//                }

            }
        });
//        googleLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleSignIn();
//            }
//        });
//        fbLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginManager.getInstance().logOut();
//
//                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                final SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
//                                GraphRequest request = GraphRequest.newMeRequest(
//                                        loginResult.getAccessToken(),
//                                        new GraphRequest.GraphJSONObjectCallback() {
//                                            @Override
//                                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                                Log.v("FBLoginActivity", response.toString());
//                                                JSONObject json = response.getJSONObject();
//                                                //socialLoginRequest.setEmail(json.optString("email").equals("")?json.optString("id")+"@facebook.com":json.optString("email"));
//                                                socialLoginRequest.setEmail(json.optString("email"));
//                                                socialLoginRequest.setName(json.optString("name"));
//                                                socialLoginRequest.setImage("https://graph.facebook.com/" + json.optString("id") + "/picture?width=150&width=150");
//                                                socialLoginRequest.setSocialLoginType("facebook");
//                                                socialLoginRequest.setSocial_id(json.optString("id"));
////                                                startActivity(new Intent(LoginActivity.this, VendorMainActivity.class));
////                                                MyApplication.setIsLogin(true);
////                                                MyApplication.setMobile("8890956027");
////                                                MyApplication.setCustomerId("32");
////                                                finish();
//                                                socialLogin(socialLoginRequest);
////                                                socialLoginRequest.setFcmToken(fcmToken);
////                                                socialLoginRequest.setDeviceId(deviceId);
////                                                loginUserWithSocial(socialLoginRequest);
//
//                                            }
//                                        });
//
//                                Bundle parameters = new Bundle();
//                                parameters.putString("fields", "id,name,email");
//                                request.setParameters(parameters);
//                                request.executeAsync();
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Toast.makeText(LoginActivity.this, "Login Cancelled.", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                Toast.makeText(LoginActivity.this, "Can't connect facebook, Please try later !! ", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                );
//            }
//        });
    }

    //    private void googleSignIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                //  Log.e("id", account.getId() + "");
                SocialLoginRequest loginRequest = new SocialLoginRequest();
                loginRequest.setName(account.getDisplayName());
                loginRequest.setEmail(account.getEmail());
                if (account.getPhotoUrl() != null)
                    loginRequest.setImage(account.getPhotoUrl().toString());
                loginRequest.setIdToken(account.getIdToken());
                loginRequest.setSocialLoginType("gmail");
//                loginRequest.setFcmToken(fcmToken);
//                loginRequest.setDeviceId(deviceId);
                loginRequest.setSocial_id(account.getId());
                socialLogin(loginRequest);
//                startActivity(new Intent(LoginActivity.this, VendorMainActivity.class));
//                MyApplication.setIsLogin(true);
//                MyApplication.setMobile("8890956027");
//                MyApplication.setCustomerId("32");
//                finish();
//                loginUserWithSocial(loginRequest);
            } else {
                Toast.makeText(DeliveryLoginActivity.this, "Sorry unable to fetch details", Toast.LENGTH_SHORT).show();
                return;
            }
//            signOut();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    //    private void signOut() {
//        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        });
//    }
    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Error", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("Token::", token);
//                        sharedPreferences.edit().putString(Constants.sharedPreferenceToken, token).apply();

//                        Toast.makeText(ActivityLogin.this, ""+token, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                });
    }
    private void login(String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryLoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.deliveryBoyLogin(mobile,token);


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Intent intent = new Intent(DeliveryLoginActivity.this, DeliverOtpActivity.class);
                    intent.putExtra("mobile", edt_mobile.getText().toString());
                    intent.putExtra("is_register", "0");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DeliveryLoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
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
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryLoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String first_name, String addhar_no,String email, String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryLoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<VendorResponseRegister> responseCall = apiService.vendor_register(first_name,addhar_no, email, mobile ,token);


        responseCall.enqueue(new Callback<VendorResponseRegister>() {
            @Override
            public void onResponse(Call<VendorResponseRegister> call, Response<VendorResponseRegister> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Intent intent = new Intent(DeliveryLoginActivity.this, DeliverOtpActivity.class);
                    intent.putExtra("mobile", edt_mobile.getText().toString());
                    intent.putExtra("is_register", "1");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DeliveryLoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<VendorResponseRegister> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryLoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void socialLogin(SocialLoginRequest loginRequest) {
        final ProgressDialog progressDialog = new ProgressDialog(DeliveryLoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<SocialLoginResponse> responseCall = apiService.googleRegistration(loginRequest.getSocial_id(), loginRequest.getName(), loginRequest.getSocialLoginType(), loginRequest.getEmail(), "", "",token);


        responseCall.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess().equals("1") || response.body().getSuccess().equals("2")) {
                        startActivity(new Intent(DeliveryLoginActivity.this, MainActivity.class));
                        MyApplication.setIsLogin(true);
                        MyApplication.setMobile(response.body().getData().get(0).getCustomers_phone());
                        MyApplication.setCustomerId(response.body().getData().get(0).getCustomers_id());
                        finish();
                    } else {
                        Toast.makeText(DeliveryLoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(DeliveryLoginActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
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
            public void onFailure(Call<SocialLoginResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(DeliveryLoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
