package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.AllProductDataModel;
import com.android.ekishan.model.GetVegResponse;
import com.android.ekishan.model.RegisterVegResponse;
import com.android.ekishan.model.UnitsModel;
import com.android.ekishan.model.VegListModel;
import com.android.ekishan.vendor.AddEditVegetableActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleFragment extends Fragment {
    View view1,view2,view3;
    RelativeLayout vegetable,fruits,products,rlupdate;
    RecyclerView my_order_recycle,fruitsRecycler,productsRecycler;
    ApiInterface apiService;
    ArrayList<VegListModel> veg_list=new ArrayList<>();
    ArrayList<VegListModel> fruits_list=new ArrayList<>();
    ArrayList<VegListModel> product_list=new ArrayList<>();
    ArrayList<UnitsModel> units_list=new ArrayList<>();
    ArrayList<AllProductDataModel> sent_product_list=new ArrayList<>();
    JSONArray productsList=new JSONArray();
    MyOrderListAdapter adapter;
    FruitsListAdapter fruitsListAdapter;
    ProductsListAdapter productsListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sale_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        vegetable=view.findViewById(R.id.vegetable);
        fruits=view.findViewById(R.id.fruits);
        products=view.findViewById(R.id.products);
        view1=view.findViewById(R.id.view1);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);
        rlupdate=view.findViewById(R.id.rlupdate);
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
        my_order_recycle=view.findViewById(R.id.Recycler);
        my_order_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        fruitsRecycler=view.findViewById(R.id.fruitsRecycler);
        fruitsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        productsRecycler=view.findViewById(R.id.productsRecycler);
        productsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        getVegetables();

        rlupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
//                    String unit=veg_list.get(i).getUnit_id();
                    String product_id=veg_list.get(i).getId();
                    String variety_id=veg_list.get(i).getVariety_id();
                    if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")){
                        try {
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("products_quantity",period_txt);
                            jsonObject.put("products_price",farm_size_txt);
                            jsonObject.put("products_id",product_id);
                            jsonObject.put("products_image","");
                            jsonObject.put("products_name",veg_list.get(i).getProducts_name());
                            jsonObject.put("products_remarks","");
                            jsonObject.put("products_unit_id",veg_list.get(i).getUnit_id());
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
//                    String unit=fruits_list.get(i).getUnit_id();
                        String product_id=fruits_list.get(i).getId();
                        String variety_id=fruits_list.get(i).getVariety_id();
                        if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")){
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("products_quantity",period_txt);
                                jsonObject.put("products_price",farm_size_txt);
                                jsonObject.put("products_id",product_id);
                                jsonObject.put("products_image","");
                                jsonObject.put("products_name",fruits_list.get(i).getProducts_name());
                                jsonObject.put("products_remarks","");
                                jsonObject.put("products_unit_id",fruits_list.get(i).getUnit_id());
                                productsList.put(jsonObject);
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
//                    String unit=product_list.get(i).getUnit_id();
                        String product_id=product_list.get(i).getId();
                        String variety_id=product_list.get(i).getVariety_id();
                        if (!farm_size_txt.equalsIgnoreCase("")&&!period_txt.equalsIgnoreCase("")){
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("products_quantity",period_txt);
                                jsonObject.put("products_price",farm_size_txt);
                                jsonObject.put("products_id",product_id);
                                jsonObject.put("products_image","");
                                jsonObject.put("products_name",product_list.get(i).getProducts_name());
                                jsonObject.put("products_remarks","");
                                jsonObject.put("products_unit_id",product_list.get(i).getUnit_id());
                                productsList.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }catch (Exception e){}
                if (productsList.length()>0) {
                    saleProducts(progressDialog, productsList.toString());
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Please add minimum one product to sell", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
    private void saleProducts(final ProgressDialog progressDialog, String sJson) {
        Call<RegisterVegResponse> responseCall = apiService.saleProducts(MyApplication.getvendor_id(),sJson);
        responseCall.enqueue(new Callback<RegisterVegResponse>() {
            @Override
            public void onResponse(Call<RegisterVegResponse> call, Response<RegisterVegResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    getFragmentManager().popBackStack();
                }else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterVegResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getVegetables() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<GetVegResponse> responseCall = apiService.getVendorRegisteredVegetables(MyApplication.getvendor_id());


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
                    fruitsRecycler.setVisibility(View.GONE);
                    productsRecycler.setVisibility(View.GONE);
                }else {

                }
            }

            @Override
            public void onFailure(Call<GetVegResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter() {
        adapter = new MyOrderListAdapter(getActivity(), veg_list, this);
        my_order_recycle.setAdapter(adapter);
    }
    private void setFruitsAdapter() {
        fruitsListAdapter = new FruitsListAdapter(getActivity(), fruits_list, this);
        fruitsRecycler.setAdapter(fruitsListAdapter);
    }
    private void setProductsAdapter() {
        productsListAdapter = new ProductsListAdapter(getActivity(), product_list, this);
        productsRecycler.setAdapter(productsListAdapter);
    }
    public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        SaleFragment fragment;
        public MyOrderListAdapter(Context c, List<VegListModel> data, SaleFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public MyOrderListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_item_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            MyOrderListAdapter.CustomViewHolder viewHolder = new MyOrderListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyOrderListAdapter.CustomViewHolder holder, final int position) {
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
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    data.get(position).setUnit_id(units_list.get(pos).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());

        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            EditText farm_size,period;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
            }
        }
    }

    public class FruitsListAdapter extends RecyclerView.Adapter<FruitsListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        SaleFragment fragment;
        public FruitsListAdapter(Context c, List<VegListModel> data, SaleFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public FruitsListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_item_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            FruitsListAdapter.CustomViewHolder viewHolder = new FruitsListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final FruitsListAdapter.CustomViewHolder holder, final int position) {
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
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    data.get(position).setUnit_id(units_list.get(pos).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());

        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            EditText farm_size,period;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
            }
        }
    }

    public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.CustomViewHolder>{
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VegListModel> data;

        SaleFragment fragment;
        public ProductsListAdapter(Context c, List<VegListModel> data, SaleFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;
        }
        @Override
        public ProductsListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_item_layout, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            ProductsListAdapter.CustomViewHolder viewHolder = new ProductsListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ProductsListAdapter.CustomViewHolder holder, final int position) {
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
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    data.get(position).setUnit_id(units_list.get(pos).getProducts_options_values_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            data.get(position).setUnit_id(units_list.get(0).getProducts_options_values_id());

        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView vegName;
            LinearLayout entryLayout;
            Spinner spinner;
            EditText farm_size,period;
            public CustomViewHolder(View convertView) {
                super(convertView);
                vegName=convertView.findViewById(R.id.vegName);
                entryLayout=convertView.findViewById(R.id.entryLayout);
                spinner = convertView.findViewById(R.id.spinner);
                farm_size = convertView.findViewById(R.id.farm_size);
                period = convertView.findViewById(R.id.period);
            }
        }
    }

}
