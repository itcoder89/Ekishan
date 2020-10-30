package com.android.ekishan.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.OrderSuccesFragment;
import com.android.ekishan.model.ResponseLogin;
import com.android.ekishan.model.SocialLoginRequest;
import com.android.ekishan.model.SocialLoginResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.ekishan.view.CommonUtil.isValidMatch;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout rlLogin;
    ApiInterface apiService;
    EditText edt_mobile,edtname,edtemail,edtcode;
    TextView header;
    LinearLayout llname,llemail;
    TextView click_here,txt_new_user,tvLogin;
    CardView googleLogin,fbLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1001;
    private CallbackManager callbackManager;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        setContentView(R.layout.kihsan_login);
        getSupportActionBar().hide();
        googleLogin = findViewById(R.id.googleLogin);
        fbLogin = findViewById(R.id.fbLogin);
        rlLogin = findViewById(R.id.rlLogin);
        edt_mobile = findViewById(R.id.edt_mobile);
        header = findViewById(R.id.header);
        edtcode = findViewById(R.id.edtcode);
        llname = findViewById(R.id.llname);
        llemail = findViewById(R.id.llemail);
        click_here = findViewById(R.id.click_here);
        txt_new_user = findViewById(R.id.txt_new_user);
        tvLogin = findViewById(R.id.tvLogin);
        edtname = findViewById(R.id.edtname);
        edtemail = findViewById(R.id.edtemail);
        callbackManager = CallbackManager.Factory.create();
        SharedPreferences preferences =getSharedPreferences(MyApplication.MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        getToken();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(header.getText().toString().equalsIgnoreCase(getString(R.string.login))){
                    header.setText(getString(R.string.register));
                    llemail.setVisibility(View.VISIBLE);
                    llname.setVisibility(View.VISIBLE);
                    txt_new_user.setText(getString(R.string.already_account));
                    tvLogin.setText(getString(R.string.register));
                }else{
                    header.setText(getString(R.string.login));
                    llemail.setVisibility(View.GONE);
                    llname.setVisibility(View.GONE);
                    txt_new_user.setText(getString(R.string.new_user));
                    tvLogin.setText(getString(R.string.send_otp));
                }
            }
        });

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(header.getText().toString().equalsIgnoreCase(getString(R.string.login))) {
                    if (edt_mobile.getText().toString().length() == 10) {
                        login(edt_mobile.getText().toString());
                    } else {
                        Toast.makeText(LoginActivity.this, "Please enter valid mobile", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(edt_mobile.getText().toString().length()==10 && edtname.toString().length()>0){
                        if(isValidEmail(edtemail.getText().toString())) {
                            register(edtname.getText().toString(), edtemail.getText().toString(), edt_mobile.getText().toString());
                        }else{
                            Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Please enter valid mobile", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                final SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                Log.v("FBLoginActivity", response.toString());
                                                JSONObject json = response.getJSONObject();
                                                //socialLoginRequest.setEmail(json.optString("email").equals("")?json.optString("id")+"@facebook.com":json.optString("email"));
                                                socialLoginRequest.setEmail(json.optString("email"));
                                                socialLoginRequest.setName(json.optString("name"));
                                                socialLoginRequest.setImage("https://graph.facebook.com/" + json.optString("id") + "/picture?width=150&width=150");
                                                socialLoginRequest.setSocialLoginType("facebook");
                                                socialLoginRequest.setSocial_id(json.optString("id"));
//                                                startActivity(new Intent(LoginActivity.this, VendorMainActivity.class));
//                                                MyApplication.setIsLogin(true);
//                                                MyApplication.setMobile("8890956027");
//                                                MyApplication.setCustomerId("32");
//                                                finish();
                                                socialLogin(socialLoginRequest);
//                                                socialLoginRequest.setFcmToken(fcmToken);
//                                                socialLoginRequest.setDeviceId(deviceId);
//                                                loginUserWithSocial(socialLoginRequest);

                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(LoginActivity.this, "Login Cancelled.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(LoginActivity.this, "Can't connect facebook, Please try later !! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public static boolean isValidEmail(String email) {
        try {
            if (isValidMatch(REGEX_EMAIL, email))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            ///Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                //Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "Sorry unable to fetch details", Toast.LENGTH_SHORT).show();
                return;
            }
            signOut();
        } catch (ApiException e) {
            Toast.makeText(LoginActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    private void login(String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseLogin> responseCall = apiService.login(mobile,token);


        responseCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    MyApplication.setCustomerId(response.body().getData().getCustomers_id());
                    Intent intent=new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra("mobile",edt_mobile.getText().toString());
                    intent.putExtra("isregister","0");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
               // Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void register(String name,String email,String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseLogin> responseCall = apiService.register(name,email,mobile,"baba",token,edtcode.getText().toString());


        responseCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    if (response.body().getData().getCustomers_id()!=null) {
                        MyApplication.setCustomerId(response.body().getData().getCustomers_id());
                    }
                    Intent intent=new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra("mobile",edt_mobile.getText().toString());
                    intent.putExtra("isregister","1");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void socialLogin(SocialLoginRequest loginRequest) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<SocialLoginResponse> responseCall = apiService.googleRegistration(loginRequest.getSocial_id(),loginRequest.getName(),loginRequest.getSocialLoginType(),loginRequest.getEmail(),"","",token);


        responseCall.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                progressDialog.dismiss();
                if (response.body()!=null) {
                    if (response.body().getSuccess().equals("1")||response.body().getSuccess().equals("2")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        MyApplication.setIsLogin(true);
                        MyApplication.setMobile(response.body().getData().get(0).getCustomers_phone());
                        MyApplication.setCustomerId(response.body().getData().get(0).getCustomers_id());
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
