package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.ResponseGetReview;
import com.android.ekishan.model.UpdateProfileResponse;
import com.squareup.picasso.Picasso;

public class EditReviewRatingFragment extends Fragment {
    ApiInterface apiService;
    EditText edt_review, edt_title;
    RelativeLayout rledit;
    TextView txt_title, decription;
    RatingBar ratingBar;
    String type;
    RelativeLayout rl_edit;
    TextView tvLogin;
    CircleImageView image;

    public EditReviewRatingFragment(String add) {
        this.type=add;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_review_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        edt_review = root.findViewById(R.id.edt_review);
        rl_edit = root.findViewById(R.id.rl_edit);
        edt_title = root.findViewById(R.id.edt_title);
        tvLogin = root.findViewById(R.id.tvLogin);
        rledit = root.findViewById(R.id.rledit);
        txt_title = root.findViewById(R.id.txt_title);
        decription = root.findViewById(R.id.description);
        ratingBar = root.findViewById(R.id.ratingBar);
        image = root.findViewById(R.id.image);
        if(type.equalsIgnoreCase("Add")){
            tvLogin.setText("Add");
        }else{
            tvLogin.setText("Update");
        }
        txt_title.setText("" + MyApplication.edit_review_model.getProducts_name());
        decription.setText("" + MyApplication.edit_review_model.getProducts_description());
        Picasso.with(getActivity())
                .load(MyApplication.edit_review_model.getProducts_image())
                .placeholder(R.drawable.place_holder) // optional
                .error(R.drawable.place_holder)         // optional
                .into(image);
        rledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equalsIgnoreCase("Add")) {
                    addReview(""+ratingBar.getRating(),edt_title.getText().toString(),edt_review.getText().toString());
                } else {
                    update_review(MyApplication.edit_review_model.getReview_id(), "" + ratingBar.getRating(), edt_title.getText().toString(), edt_review.getText().toString());
                }
            }
        });
        if(type.equalsIgnoreCase("edit")){
            ratingBar.setRating(Float.parseFloat(MyApplication.edit_review_model.getReview().getReviews_rating()));
            edt_title.setText(""+MyApplication.edit_review_model.getReview().getTitle());
            edt_review.setText(""+MyApplication.edit_review_model.getReview().getReview());

        }

            return root;
    }

    private void update_review(String review_id, String rating, String title, String review) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.updateReview(
                review_id, rating, title, review, "");


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addReview( String rating, String title, String review) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.addReview(
                MyApplication.getCustomerId(),MyApplication.edit_review_model.getProducts_id() ,rating, MyApplication.edit_review_model.getOrders_id(),title, review, "");


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }else{
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
