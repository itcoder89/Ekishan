package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.CurrentLocationActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.activity.EditMapsActivity;
import com.android.ekishan.activity.MapsActivity;
import com.android.ekishan.model.AddressListModel;
import com.android.ekishan.model.CartListResponse;
import com.android.ekishan.model.MyAddressListResponse;
import com.android.ekishan.model.UpdateProfileResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLocation extends Fragment {
    ArrayList<AddressListModel> notification_list=new ArrayList<>();
    RecyclerView itemRecycler;
    CategoryListAdapter adapter;
    ApiInterface apiService;
    Button saveAddress;
    String addressid="0";
    Button add_new_btn;
    LinearLayout currentLocation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.location, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        itemRecycler=root.findViewById(R.id.itemRecycler);
        add_new_btn=root.findViewById(R.id.add_new_btn);
        currentLocation=root.findViewById(R.id.currentLocation);
        add_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
                //startActivity(new Intent(getActivity(), CurrentLocationActivity.class));
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        saveAddress=root.findViewById(R.id.saveAddress);
        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDefaultAddress(addressid);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllAddress();
    }

    private void updateDefaultAddress(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.updateDefaultAddress(MyApplication.getCustomerId(),id);


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
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

    private void getAllAddress() {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        itemRecycler.setLayoutManager(staggeredGridLayoutManager);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<MyAddressListResponse> responseCall = apiService.getAllAddress(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<MyAddressListResponse>() {
            @Override
            public void onResponse(Call<MyAddressListResponse> call, Response<MyAddressListResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    notification_list.clear();
                    notification_list=response.body().getData();
                    if (notification_list.size()>0) {
                        saveAddress.setVisibility(View.VISIBLE);
                        itemRecycler.setVisibility(View.VISIBLE);
                        adapter = new CategoryListAdapter(getActivity(), notification_list, EditLocation.this);
                        itemRecycler.setAdapter(adapter);
                    }else {
                        saveAddress.setVisibility(View.GONE);
                    }
                }else {
                    notification_list.clear();
                    if (adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                    saveAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyAddressListResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<AddressListModel> data;
        EditLocation fragment;

        public CategoryListAdapter(Context c, List<AddressListModel> data,EditLocation fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public CategoryListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_list_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter.CustomViewHolder viewHolder = new CategoryListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter.CustomViewHolder holder, final int position) {
            holder.address.setText(data.get(position).getGoogleLocation());
            if (data.get(position).getAddress_id().equalsIgnoreCase(data.get(position).getDefault_address())){
                holder.radioButton.setChecked(true);
                addressid=data.get(position).getAddress_id();
            }else {
                holder.radioButton.setChecked(false);
            }
            holder.edt_Address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyApplication.edit_model=data.get(position);
                    //Intent intent=new Intent(getActivity(),MapsActivity.class);
                    Intent intent=new Intent(getActivity(), EditMapsActivity.class);
                    intent.putExtra("action","Edit");
                    intent.putExtra("address_id",data.get(position).getAddress_id());
                    intent.putExtra("lat",data.get(position).getLatitude());
                    intent.putExtra("lng",data.get(position).getLongitude());
                    intent.putExtra("firstname",data.get(position).getFirstname());
                    intent.putExtra("lastname",data.get(position).getLastname());
                    intent.putExtra("mobile",data.get(position).getMobile());
                    intent.putExtra("postcode",data.get(position).getPostcode());
                    intent.putExtra("city",data.get(position).getCity());
                    intent.putExtra("street",data.get(position).getStreet());
                    intent.putExtra("house_no",data.get(position).getHouse_no());
                    intent.putExtra("google_location",data.get(position).getGoogleLocation());
                    startActivity(intent);
                }
            });
            holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        for (int i=0; i<data.size(); i++){
                            data.get(i).setDefault_address("0");
                        }
                        addressid=data.get(position).getAddress_id();
                        data.get(position).setDefault_address(data.get(position).getAddress_id());
                    itemRecycler.post(new Runnable()
                    {
                        @Override
                        public void run() {
                            notifyDataSetChanged();

                        }
                    });
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteAddress(data.get(position).getAddress_id());
                }
            });
        }
        private void DeleteAddress(String id) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<UpdateProfileResponse> responseCall = apiService.deleteAddress(MyApplication.getCustomerId(),id);


            responseCall.enqueue(new Callback<UpdateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                    progressDialog.dismiss();

                    if (response.body().getSuccess().equals("1")) {
                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        getAllAddress();
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
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            CardView crdmain;
            TextView address;
            ImageView delete;
            RadioButton radioButton;
            ImageView edt_Address;
            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain =   convertView.findViewById(R.id.crdmain);
                address =   convertView.findViewById(R.id.address);
                radioButton=convertView.findViewById(R.id.radio);
                delete=convertView.findViewById(R.id.delete);
                edt_Address=convertView.findViewById(R.id.edt_Address);
            }

        }






    }
}
