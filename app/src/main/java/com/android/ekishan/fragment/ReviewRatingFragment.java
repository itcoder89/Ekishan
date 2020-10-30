package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.ReponseProfile;
import com.android.ekishan.model.ResponseGetReview;
import com.android.ekishan.model.ReviewedList;
import com.android.ekishan.model.ToBeReviewedList;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewRatingFragment extends Fragment {
    RecyclerView to_be_reviewed_Recycler;
    RecyclerView offerRecyclerview;
    TobeReviewedListAdapter adapter;
    CategoryListAdapter2 adapter2;
    ArrayList<String> notification_list=new ArrayList<>();
    RelativeLayout alerLayout,offerLayout;
    View view1,view2;
    LinearLayout reviewedll;
    ApiInterface apiService;
    TextView noFound,noFound1;
    ArrayList<ReviewedList> alertList=new ArrayList<>();
    ArrayList<ReviewedList> offer_list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.review_rating_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        noFound=root.findViewById(R.id.noFound);
        noFound1=root.findViewById(R.id.noFound1);
        reviewedll=root.findViewById(R.id.reviewedll);
        view1=root.findViewById(R.id.view1);
        view2=root.findViewById(R.id.view2);
        alerLayout=root.findViewById(R.id.alerLayout);
        offerLayout=root.findViewById(R.id.offerLayout);
        to_be_reviewed_Recycler=root.findViewById(R.id.to_be_reviewed_Recycler);
        offerRecyclerview=root.findViewById(R.id.offerRecycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        to_be_reviewed_Recycler.setLayoutManager(staggeredGridLayoutManager);
        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(
                        1, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        offerRecyclerview.setLayoutManager(staggeredGridLayoutManager1);

        alerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_be_reviewed_Recycler.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                noFound1.setVisibility(View.GONE);
                reviewedll.setVisibility(View.GONE);
                if (alertList.size()>0){
                    noFound.setVisibility(View.GONE);
                    to_be_reviewed_Recycler.setVisibility(View.VISIBLE);
                }else {
                    noFound.setVisibility(View.VISIBLE);
                    to_be_reviewed_Recycler.setVisibility(View.GONE);
                }
            }
        });
        offerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_be_reviewed_Recycler.setVisibility(View.GONE);
                noFound.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                if (offer_list.size()>0){
                    noFound1.setVisibility(View.GONE);
                    offerRecyclerview.setVisibility(View.VISIBLE);
                    reviewedll.setVisibility(View.VISIBLE);
                }else {
                    noFound1.setVisibility(View.VISIBLE);
                    offerRecyclerview.setVisibility(View.GONE);
                    reviewedll.setVisibility(View.GONE);
                }
            }
        });
        get_review();
        return root;
    }


    private void get_review() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseGetReview> responseCall = apiService.getReview(
                MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<ResponseGetReview>() {
            @Override
            public void onResponse(Call<ResponseGetReview> call, Response<ResponseGetReview> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    if(response.body().getData().getTobereviewed()!=null && response.body().getData().getTobereviewed().size()>0) {
                        alertList=response.body().getData().getTobereviewed();
                        adapter = new TobeReviewedListAdapter(getActivity(), alertList, ReviewRatingFragment.this);
                        to_be_reviewed_Recycler.setAdapter(adapter);
                        noFound.setVisibility(View.GONE);
                        to_be_reviewed_Recycler.setVisibility(View.VISIBLE);
                    }else {
                        noFound.setVisibility(View.VISIBLE);
                        to_be_reviewed_Recycler.setVisibility(View.GONE);
                    }
                    if(response.body().getData().getReviewed()!=null && response.body().getData().getReviewed().size()>0) {
                        offer_list=response.body().getData().getReviewed();
                        adapter2 = new CategoryListAdapter2(getActivity(), offer_list, ReviewRatingFragment.this);
                        offerRecyclerview.setAdapter(adapter2);
                    }
                }else {
                    noFound.setVisibility(View.VISIBLE);
                    to_be_reviewed_Recycler.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResponseGetReview> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                noFound.setVisibility(View.VISIBLE);
                to_be_reviewed_Recycler.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class TobeReviewedListAdapter extends RecyclerView.Adapter<TobeReviewedListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<ReviewedList> data;
        List<ReviewedList> list;
        ReviewRatingFragment fragment;

        public TobeReviewedListAdapter(Context c, List<ReviewedList> data, ReviewRatingFragment fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment=fragment;


        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item_list_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            holder.txt_title.setText(""+data.get(position).getProducts_name());
            holder.description.setText(""+data.get(position).getProducts_description());
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof EditReviewRatingFragment)) {
                        MyApplication.edit_review_model=data.get(position);
                        loadFragment(new EditReviewRatingFragment("Add"));
                    }
                }
            });
            Picasso.with(getActivity())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);

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
                        List<ReviewedList> filteredList = new ArrayList<>();
                        for (ReviewedList row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getProducts_name().contains(charString.toLowerCase())) {
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
                    data = (ArrayList<ReviewedList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView  txt_title, description, chat, call, order_status;
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
            boolean is_touch=true;
            TextView pay_now;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout add;
            TextView update;
            ImageView image;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain =   convertView.findViewById(R.id.crdmain);
                image =   convertView.findViewById(R.id.image);
                txt_title =   convertView.findViewById(R.id.txt_title);
                description =   convertView.findViewById(R.id.description);
                add =   convertView.findViewById(R.id.add);
            }

        }
    }

    public class CategoryListAdapter2 extends RecyclerView.Adapter<CategoryListAdapter2.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<ReviewedList> data;
        List<ReviewedList> list;
        ReviewRatingFragment fragment;

        public CategoryListAdapter2(Context c, List<ReviewedList> data, ReviewRatingFragment fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment=fragment;


        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviewed_item_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            holder.txt_title.setText(""+data.get(position).getProducts_name());
            holder.description.setText(""+data.get(position).getProducts_description());
//            holder.status.setText(""+data.get(position).get());
            if(data.get(position).getReview()!=null) {
                holder.ratingBar.setRating(Float.parseFloat((data.get(position).getReview().getReviews_rating())));
            }
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof EditReviewRatingFragment)) {
                            MyApplication.edit_review_model=data.get(position);
                            loadFragment(new EditReviewRatingFragment("Edit"));
                        }
                    }
                });
            Picasso.with(getActivity())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);
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
                        List<ReviewedList> filteredList = new ArrayList<>();
                        for (ReviewedList row : data) {

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
                    data = (ArrayList<ReviewedList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            LinearLayout edit;
            TextView txt_title,description,status;
            RatingBar ratingBar;
            CircleImageView image;
            public CustomViewHolder(View convertView) {
                super(convertView);
                edit =   convertView.findViewById(R.id.edit);
                txt_title =   convertView.findViewById(R.id.txt_title);
                description =   convertView.findViewById(R.id.description);
                status =   convertView.findViewById(R.id.status);
                ratingBar =   convertView.findViewById(R.id.ratingBar);
                image =   convertView.findViewById(R.id.image);
            }

        }






    }

}
