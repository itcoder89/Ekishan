package com.android.ekishan.ui.notifications;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.fragment.EditLocation;
import com.android.ekishan.fragment.Vegetables;
import com.android.ekishan.model.AlertNotificationModel;
import com.android.ekishan.model.MyAddressListResponse;
import com.android.ekishan.model.NotificationResponse;
import com.android.ekishan.model.OffersNotificationModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    RecyclerView alertRecyclerview;
    RecyclerView offerRecyclerview;
    CategoryListAdapter adapter;
    CategoryListAdapter2 adapter2;
    ArrayList<AlertNotificationModel> alertList=new ArrayList<>();
    ArrayList<OffersNotificationModel> offers_list=new ArrayList<>();
    RelativeLayout alerLayout,offerLayout;
    ApiInterface apiService;
    View view1,view2;
    TextView noFound,noFound1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        noFound=root.findViewById(R.id.noFound);
        noFound1=root.findViewById(R.id.noFound1);
        view1=root.findViewById(R.id.view1);
        view2=root.findViewById(R.id.view2);
        alerLayout=root.findViewById(R.id.alerLayout);
        offerLayout=root.findViewById(R.id.offerLayout);
        alertRecyclerview=root.findViewById(R.id.alertRecycler);
        offerRecyclerview=root.findViewById(R.id.offerRecycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        alertRecyclerview.setLayoutManager(staggeredGridLayoutManager);
        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(
                        1, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        offerRecyclerview.setLayoutManager(staggeredGridLayoutManager1);



        alerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertRecyclerview.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                noFound1.setVisibility(View.GONE);
                offerRecyclerview.setVisibility(View.GONE);
                if (alertList.size()>0){
                    noFound.setVisibility(View.GONE);
                    alertRecyclerview.setVisibility(View.VISIBLE);
                }else {
                    noFound.setVisibility(View.VISIBLE);
                    alertRecyclerview.setVisibility(View.GONE);
                }
            }
        });
        offerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerRecyclerview.setVisibility(View.VISIBLE);
                alertRecyclerview.setVisibility(View.GONE);
                noFound.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                if (offers_list.size()>0){
                    noFound1.setVisibility(View.GONE);
                    offerRecyclerview.setVisibility(View.VISIBLE);

                }else {
                    noFound1.setVisibility(View.VISIBLE);
                    offerRecyclerview.setVisibility(View.GONE);
                }
            }
        });
        notifications();
        return root;
    }
    private void notifications() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<NotificationResponse> responseCall = apiService.notifications(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    alertList=response.body().getData().getAlert();
                    offers_list=response.body().getData().getOffers();
                    if (alertList!=null && alertList.size()>0){
                        adapter = new CategoryListAdapter(getActivity(), alertList,NotificationsFragment.this);
                        alertRecyclerview.setAdapter(adapter);
                        noFound.setVisibility(View.GONE);
                        alertRecyclerview.setVisibility(View.VISIBLE);
                    }else {
                        noFound.setVisibility(View.VISIBLE);
                        alertRecyclerview.setVisibility(View.GONE);
                    }
                    if (offers_list!=null && offers_list.size()>0){
                        adapter2 = new CategoryListAdapter2(getActivity(), offers_list,NotificationsFragment.this);
                        offerRecyclerview.setAdapter(adapter2);
                    }

                }else {
                    noFound.setVisibility(View.VISIBLE);
                    alertRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                noFound.setVisibility(View.VISIBLE);
                alertRecyclerview.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder>  {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<AlertNotificationModel> data;
        NotificationsFragment fragment;

        public CategoryListAdapter(Context c, List<AlertNotificationModel> data,NotificationsFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }

        @Override
        public CategoryListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alert_notification_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter.CustomViewHolder viewHolder = new CategoryListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter.CustomViewHolder holder, final int position) {
            holder.title.setText(data.get(position).getTitle());
            holder.message.setText(data.get(position).getMessage());
            holder.date.setText(data.get(position).getCreated_at());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {

            CardView crdmain;
            TextView title,message,date;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain =   convertView.findViewById(R.id.crdmain);
                title =   convertView.findViewById(R.id.title);
                message =   convertView.findViewById(R.id.message);
                date =   convertView.findViewById(R.id.date);
            }

        }




    }

    public class CategoryListAdapter2 extends RecyclerView.Adapter<CategoryListAdapter2.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<OffersNotificationModel> data;
        NotificationsFragment fragment;

        public CategoryListAdapter2(Context c, List<OffersNotificationModel> data,NotificationsFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public CategoryListAdapter2.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_notification_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter2.CustomViewHolder viewHolder = new CategoryListAdapter2.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter2.CustomViewHolder holder, final int position) {
                holder.title.setText(data.get(position).getMessage());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            CardView crdmain;
            TextView title;

            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain =   convertView.findViewById(R.id.crdmain);
                title =   convertView.findViewById(R.id.title);
            }

        }






    }

}

