package com.android.ekishan.vendor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.AllProductDataModel;
import com.android.ekishan.model.GetVegData;
import com.android.ekishan.model.GetVegResponse;
import com.android.ekishan.model.OrderListModel;
import com.android.ekishan.model.RegisterVegResponse;
import com.android.ekishan.model.UnitsModel;
import com.android.ekishan.model.VegListModel;
import com.android.ekishan.model.VendorResponseCollection;
import com.android.ekishan.vendor.fragment.Demand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditVegetableActivity extends AppCompatActivity {
    RecyclerView my_order_recycle,fruitsRecycler,productsRecycler;
    ArrayList<OrderListModel> order_list=new ArrayList<>();
    MyOrderListAdapter adapter;
    FruitsListAdapter fruitsListAdapter;
    ProductsListAdapter productsListAdapter;
    RelativeLayout vegetable,fruits,products,rl_approval,rlupdate;
    View view1,view2,view3;
    TextView admin,self;
    ApiInterface apiService;
    EditText b_name,branch,account,vendor_name,ifsc,zip_code,address;
    ArrayList<VegListModel> veg_list=new ArrayList<>();
    ArrayList<VegListModel> fruits_list=new ArrayList<>();
    ArrayList<VegListModel> product_list=new ArrayList<>();
    ArrayList<UnitsModel> units_list=new ArrayList<>();
    ArrayList<AllProductDataModel> sent_product_list=new ArrayList<>();
    JSONArray productsList=new JSONArray();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_vegitables_layout);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        b_name=findViewById(R.id.b_name);
        branch=findViewById(R.id.branch);
        account=findViewById(R.id.account);
        vendor_name=findViewById(R.id.vendor_name);
        ifsc=findViewById(R.id.ifsc);
        zip_code=findViewById(R.id.zip_code);
        address=findViewById(R.id.address);
        rlupdate=findViewById(R.id.rlupdate);
        rl_approval=findViewById(R.id.rl_approval);
        vegetable=findViewById(R.id.vegetable);
        fruits=findViewById(R.id.fruits);
        products=findViewById(R.id.products);
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        admin=findViewById(R.id.admin);
        self=findViewById(R.id.self_app);
        if (getIntent().hasExtra("is_from")) {
            if (getIntent().getStringExtra("is_from").equalsIgnoreCase("EDIT")) {
                disableFields();
            }
        }
        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                my_order_recycle.setVisibility(View.VISIBLE);
                productsRecycler.setVisibility(View.GONE);
                fruitsRecycler.setVisibility(View.GONE);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.GONE);
                my_order_recycle.setVisibility(View.GONE);
                productsRecycler.setVisibility(View.GONE);
                fruitsRecycler.setVisibility(View.VISIBLE);
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);
                my_order_recycle.setVisibility(View.GONE);
                productsRecycler.setVisibility(View.VISIBLE);
                fruitsRecycler.setVisibility(View.GONE);
            }
        });

        rlupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (b_name.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter bank name", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (branch.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter branch name", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (account.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter account number", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (vendor_name.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter vendor name", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (ifsc.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter ifsc code", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if (address.getText().toString().equalsIgnoreCase("")){
//                    Toast.makeText(AddEditVegetableActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                final ProgressDialog progressDialog = new ProgressDialog(AddEditVegetableActivity.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.setCancelable(false);
                progressDialog.show();
                for (int i = 0; i < veg_list.size(); i++) {
                    View view = my_order_recycle.getChildAt(i);
                    EditText farm_size = (EditText) view.findViewById(R.id.farm_size);
                    EditText period = (EditText) view.findViewById(R.id.period);
                    EditText remark = (EditText) view.findViewById(R.id.remark);
                    String farm_size_txt = farm_size.getText().toString();
                    String period_txt = period.getText().toString();
                    String remark_txt = remark.getText().toString();
//                    String unit=veg_list.get(i).getUnit_id();
                    String product_id=veg_list.get(i).getId();
                    String variety_id=veg_list.get(i).getVariety_id();
                    if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")&&!remark_txt.equalsIgnoreCase("")){
                        try {
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("products_cult_period",period_txt);
                            jsonObject.put("products_farm_size",farm_size_txt);
                            jsonObject.put("products_id",product_id);
                            jsonObject.put("products_image","");
                            jsonObject.put("products_name",veg_list.get(i).getProducts_name());
                            jsonObject.put("products_remarks",remark_txt);
                            jsonObject.put("variety_id",variety_id);
                            productsList.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                try {
                    for (int i = 0; i < fruits_list.size(); i++) {
                        View view = fruitsRecycler.getChildAt(i);
                        EditText farm_size = (EditText) view.findViewById(R.id.farm_size);
                        EditText period = (EditText) view.findViewById(R.id.period);
                        EditText remark = (EditText) view.findViewById(R.id.remark);
                        String farm_size_txt = farm_size.getText().toString();
                        String period_txt = period.getText().toString();
                        String remark_txt = remark.getText().toString();
//                    String unit=fruits_list.get(i).getUnit_id();
                        String product_id=fruits_list.get(i).getId();
                        String variety_id=fruits_list.get(i).getVariety_id();
                        if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")&&!remark_txt.equalsIgnoreCase("")){
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("products_cult_period",period_txt);
                                jsonObject.put("products_farm_size",farm_size_txt);
                                jsonObject.put("products_id",product_id);
                                jsonObject.put("products_image","");
                                jsonObject.put("products_name",fruits_list.get(i).getProducts_name());
                                jsonObject.put("products_remarks",remark_txt);
                                jsonObject.put("variety_id",variety_id);
                                productsList.put(jsonObject);
                                Log.e("ProductJson","1st "+jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }catch (Exception e){

                }
                try {
                    for (int i = 0; i < product_list.size(); i++) {
                        View view = productsRecycler.getChildAt(i);
                        EditText farm_size = (EditText) view.findViewById(R.id.farm_size);
                        EditText period = (EditText) view.findViewById(R.id.period);
                        EditText remark = (EditText) view.findViewById(R.id.remark);
                        String farm_size_txt = farm_size.getText().toString();
                        String period_txt = period.getText().toString();
                        String remark_txt = remark.getText().toString();
//                    String unit=product_list.get(i).getUnit_id();
                        String product_id=product_list.get(i).getId();
                        String variety_id=product_list.get(i).getVariety_id();
                        if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")&&!remark_txt.equalsIgnoreCase("")){
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("products_cult_period",period_txt);
                                jsonObject.put("products_farm_size",farm_size_txt);
                                jsonObject.put("products_id",product_id);
                                jsonObject.put("products_image","");
                                jsonObject.put("products_name",product_list.get(i).getProducts_name());
                                jsonObject.put("products_remarks",remark_txt);
                                jsonObject.put("variety_id",variety_id);
                                productsList.put(jsonObject);
                                Log.e("ProductJson","2nd "+jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }catch (Exception e){}

                vegetablesRegistration(progressDialog,productsList.toString());

            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approval("admin");
            }
        });
        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                approval("admin");
                startActivity(new Intent(AddEditVegetableActivity.this, VendorMainActivity.class));
                finish();
            }
        });

        my_order_recycle=findViewById(R.id.Recycler);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(this));
        fruitsRecycler=findViewById(R.id.fruitsRecycler);
        fruitsRecycler.setLayoutManager(new LinearLayoutManager(this));
        productsRecycler=findViewById(R.id.productsRecycler);
        productsRecycler.setLayoutManager(new LinearLayoutManager(this));
        getVegetables();
    }

    private void vegetablesRegistration(final ProgressDialog progressDialog, String sJson) {
        Log.e("vegetablesRegistration",""+sJson);
        Call<RegisterVegResponse> responseCall = apiService.vegetablesRegistration(address.getText().toString(),MyApplication.getvendor_id(),b_name.getText().toString(),branch.getText().toString(),account.getText().toString(),vendor_name.getText().toString(),ifsc.getText().toString(),zip_code.getText().toString(),address.getText().toString(),sJson);
        responseCall.enqueue(new Callback<RegisterVegResponse>() {
            @Override
            public void onResponse(Call<RegisterVegResponse> call, Response<RegisterVegResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    if (getIntent().hasExtra("is_from")){
                        if (getIntent().getStringExtra("is_from").equalsIgnoreCase("EDIT")){
                                    finish();
                        }else {
                            rl_approval.setVisibility(View.VISIBLE);
                        }
                    }else {
                        rl_approval.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterVegResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void approval(String approval_by) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<RegisterVegResponse> responseCall = apiService.requestApproval(MyApplication.getvendor_id(),approval_by);
        responseCall.enqueue(new Callback<RegisterVegResponse>() {
            @Override
            public void onResponse(Call<RegisterVegResponse> call, Response<RegisterVegResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    startActivity(new Intent(AddEditVegetableActivity.this, VendorMainActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterVegResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVegetables() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<GetVegResponse> responseCall = apiService.getVegetables(MyApplication.getvendor_id());


        responseCall.enqueue(new Callback<GetVegResponse>() {
            @Override
            public void onResponse(Call<GetVegResponse> call, Response<GetVegResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    veg_list=response.body().getData().getVegetable();
                    fruits_list=response.body().getData().getFruit();
                    product_list=response.body().getData().getProduct();
                    units_list=response.body().getData().getUnits();
                    setAdapter();
                    setFruitsAdapter();
                    setProductsAdapter();
                    setData(response.body().getData());
                    fruitsRecycler.setVisibility(View.GONE);
                    productsRecycler.setVisibility(View.GONE);
                }else {

                }
            }

            @Override
            public void onFailure(Call<GetVegResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setData(GetVegData model){
        b_name.setText(model.getBank_name());
        branch.setText(model.getVendors_bank_branch_name());
        account.setText(model.getVendors_bank_acc_number());
        ifsc.setText(model.getVendors_bank_ifsc());
        vendor_name.setText(model.getVendors_acc_holder_name());
        zip_code.setText(model.getPostal_code());
        address.setText(model.getAddress());
    }
    private void disableFields(){
        b_name.setEnabled(false);
        branch.setEnabled(false);
        account.setEnabled(false);
        ifsc.setEnabled(false);
//        vendor_name.setEnabled(false);
//        zip_code.setEnabled(false);
//        address.setEnabled(false);
//        rlupdate.setVisibility(View.GONE);
    }
    private void setAdapter() {
        adapter = new MyOrderListAdapter(getApplication(), veg_list, this);
        my_order_recycle.setAdapter(adapter);
    }
    private void setFruitsAdapter() {
        fruitsListAdapter = new FruitsListAdapter(getApplication(), fruits_list, this);
        fruitsRecycler.setAdapter(fruitsListAdapter);
    }
    private void setProductsAdapter() {
        productsListAdapter = new ProductsListAdapter(getApplication(), product_list, this);
        productsRecycler.setAdapter(productsListAdapter);
    }
    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        AddEditVegetableActivity fragment;
        public MyOrderListAdapter(Context c, List<VegListModel> data, AddEditVegetableActivity fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public MyOrderListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veg_items_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            MyOrderListAdapter.CustomViewHolder viewHolder = new MyOrderListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyOrderListAdapter.CustomViewHolder holder, final int position) {
            Picasso.with(getApplicationContext())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.product_image);
            holder.vegName.setText((position+1)+". "+data.get(position).getProducts_name());
            holder.vegName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.entryLayout.getVisibility()==View.VISIBLE){
                        holder.entryLayout.setVisibility(View.GONE);
                    }else {
                        holder.entryLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            ArrayAdapter<UnitsModel> arrayAdapter = new ArrayAdapter<UnitsModel>(mContext, android.R.layout.simple_spinner_dropdown_item, units_list);
            holder.spinner.setAdapter(arrayAdapter);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    data.get(position).setUnit_id(units_list.get(position).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());
            holder.farm_size.setText(data.get(position).getFarm_size());
            holder.period.setText(data.get(position).getCult_period());
            holder.remark.setText(data.get(position).getRemarks());
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            CircleImageView product_image;
            EditText farm_size,period,remark;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
                remark = convertView.findViewById(R.id.remark);
                product_image = convertView.findViewById(R.id.product_image);
            }
        }
    }

    public class FruitsListAdapter extends RecyclerView.Adapter<FruitsListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        AddEditVegetableActivity fragment;
        public FruitsListAdapter(Context c, List<VegListModel> data, AddEditVegetableActivity fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public FruitsListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veg_items_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            FruitsListAdapter.CustomViewHolder viewHolder = new FruitsListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final FruitsListAdapter.CustomViewHolder holder, final int position) {
            Picasso.with(getApplicationContext())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.product_image);
            holder.vegName.setText((position+1)+". "+data.get(position).getProducts_name());
            holder.vegName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.entryLayout.getVisibility()==View.VISIBLE){
                        holder.entryLayout.setVisibility(View.GONE);
                    }else {
                        holder.entryLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            ArrayAdapter<UnitsModel> arrayAdapter = new ArrayAdapter<UnitsModel>(mContext, android.R.layout.simple_spinner_dropdown_item, units_list);
            holder.spinner.setAdapter(arrayAdapter);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    data.get(position).setUnit_id(units_list.get(position).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());
            holder.farm_size.setText(data.get(position).getFarm_size());
            holder.period.setText(data.get(position).getCult_period());
            holder.remark.setText(data.get(position).getRemarks());
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            CircleImageView product_image;
            EditText farm_size,period,remark;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
                remark = convertView.findViewById(R.id.remark);
                product_image = convertView.findViewById(R.id.product_image);
            }
        }
    }

    public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        AddEditVegetableActivity fragment;
        public ProductsListAdapter(Context c, List<VegListModel> data, AddEditVegetableActivity fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public ProductsListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veg_items_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            ProductsListAdapter.CustomViewHolder viewHolder = new ProductsListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ProductsListAdapter.CustomViewHolder holder, final int position) {
            Picasso.with(getApplicationContext())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.product_image);
            holder.vegName.setText((position+1)+". "+data.get(position).getProducts_name());
            holder.vegName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.entryLayout.getVisibility()==View.VISIBLE){
                        holder.entryLayout.setVisibility(View.GONE);
                    }else {
                        holder.entryLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            ArrayAdapter<UnitsModel> arrayAdapter = new ArrayAdapter<UnitsModel>(mContext, android.R.layout.simple_spinner_dropdown_item, units_list);
            holder.spinner.setAdapter(arrayAdapter);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    data.get(position).setUnit_id(units_list.get(position).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());
            holder.farm_size.setText(data.get(position).getFarm_size());
            holder.period.setText(data.get(position).getCult_period());
            holder.remark.setText(data.get(position).getRemarks());
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            CircleImageView product_image;
            EditText farm_size,period,remark;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
                remark = convertView.findViewById(R.id.remark);
                product_image = convertView.findViewById(R.id.product_image);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (rl_approval.getVisibility()==View.VISIBLE){
            rl_approval.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
