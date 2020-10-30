package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.GetProductsResponse;
import com.android.ekishan.model.HomeProductList;
import com.android.ekishan.model.reponseHome;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    RecyclerView offerRecyclerview;
    ArrayList<HomeProductList> product_list = new ArrayList<>();
    ApiInterface apiService;
    String search_text="";
    EditText searchEdt;
    VegetablesListAdapter adapter;
    TextView cart_quantity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fragment_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        offerRecyclerview=root.findViewById(R.id.offerRecycler);
        searchEdt=root.findViewById(R.id.searchEdt);
        cart_quantity = getActivity().findViewById(R.id.cart_quantity);
        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(
                        2, //The numbe      r of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        offerRecyclerview.setLayoutManager(staggeredGridLayoutManager1);
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_text=s.toString();
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        get_products();
        return root;
    }
    private void get_products() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<GetProductsResponse> responseCall = apiService.getProductBySearch(MyApplication.getCustomerId(),"SEARCH",search_text);


        responseCall.enqueue(new Callback<GetProductsResponse>() {
            @Override
            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
//                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                    product_list.clear();
                    product_list = response.body().getData().getProducts();
                    setAdapter();

                }

            }

            @Override
            public void onFailure(Call<GetProductsResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        adapter = new VegetablesListAdapter(getActivity(), product_list, SearchFragment.this);
        offerRecyclerview.setAdapter(adapter);
    }
    public class VegetablesListAdapter extends RecyclerView.Adapter<VegetablesListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<HomeProductList> data;
        List<HomeProductList> list;


        SearchFragment fragment;

        public VegetablesListAdapter(Context c, List<HomeProductList> data, SearchFragment fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment = fragment;
        }

        @Override
        public VegetablesListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vegetables_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            VegetablesListAdapter.CustomViewHolder viewHolder = new VegetablesListAdapter.CustomViewHolder(view);
            return viewHolder;
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
        public void onBindViewHolder(final VegetablesListAdapter.CustomViewHolder holder, final int position) {
            if (data.get(position).getOut_of_stock().equalsIgnoreCase("1")){
                holder.outOfstock.setVisibility(View.VISIBLE);
            }else {
                holder.outOfstock.setVisibility(View.GONE);
            }
            holder.crdmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ProductSingleFragment)) {
                        loadFragment(new ProductSingleFragment(data.get(position).getId()));
                    }
                }
            });
            holder.weight.setText(data.get(position).getWeight());
            holder.variety_name.setText(data.get(position).getVariety_name());
            holder.txt_title.setText(data.get(position).getProducts_name());
            holder.description.setText(data.get(position).getProducts_description());
            holder.price.setText("₹"+data.get(position).getProducts_price().split("\\.")[0]);
            Picasso.with(getActivity())
                    .load(data.get(position).getProducts_image())
                    .placeholder(R.drawable.place_holder) // optional
                    .error(R.drawable.place_holder)         // optional
                    .into(holder.image);
            if (data.get(position).getQuantity()==null){
                data.get(position).setQuantity("0");
            }
            holder.quantity.setText(data.get(position).getQuantity());
            if (Integer.parseInt(data.get(position).getQuantity())>0){
                holder.ll_cart.setVisibility(View.VISIBLE);
                holder.rlLogin.setVisibility(View.GONE);
            }else {
                holder.ll_cart.setVisibility(View.GONE);
                holder.rlLogin.setVisibility(View.VISIBLE);
            }
            holder.rlLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isLogin()) {
                        addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"1",position);
                    }else {
                        MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                    }

                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isLogin()) {
                        addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"0",position);
                    }else {
                        MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                    }

                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.isLogin()) {
                        addToCart(data.get(position).getId(),data.get(position).getVariety_id(),"1",position);
                    }else {
                        MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                    }

                }
            });
        }
        private void addToCart(String product_id,String variety_id, final String isadd, final int pos) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
            progressDialog.setMessage("Adding to cart...");
            progressDialog.setCancelable(false);
            progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
            Call<ResponseBody> responseCall = apiService.addToCart(MyApplication.getCustomerId(),product_id,variety_id,isadd,"0");


            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String res=response.body().string();
                        JSONObject jsonObject=new JSONObject(res);
                        if (jsonObject.getString("success").equals("1")) {
                            if (isadd.equalsIgnoreCase("1")){
                                if (Integer.parseInt(data.get(pos).getQuantity())<1) {
                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) + 1));
                                }
                                data.get(pos).setQuantity(String.valueOf(Integer.parseInt(data.get(pos).getQuantity())+1));
                            }else {
                                if (Integer.parseInt(data.get(pos).getQuantity())==1) {
                                    cart_quantity.setText(String.valueOf(Integer.parseInt(cart_quantity.getText().toString()) - 1));
                                }
                                data.get(pos).setQuantity(String.valueOf(Integer.parseInt(data.get(pos).getQuantity())-1));
                            }
                            notifyItemChanged(pos);
                        }else {
                            Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
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


        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        data = list;
                    } else {
                        List<HomeProductList> filteredList = new ArrayList<>();
                        for (HomeProductList row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getProducts_name().toLowerCase().contains(charString.toLowerCase()) || row.getProducts_price().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        data = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = data;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    data = (ArrayList<HomeProductList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView weight,txt_title, description, price, variety_name, call, order_status;
            TextView order_id, service_type;
            TextView reason;
            TextView order_form, type_of_sub_service, invoice_no;
            TextView quote, payment_mode, amount, sla_txt, ved_txt;
            TextView product_types, service_date_time, payment_status, edit_instruction_txt;
            ImageView image;
            LinearLayout is_details_show, ll_order_detail, ll_assign_schedule, llassigned;
            CardView emp_doc_click, trade_lic_click, esta_card_click, service_letter_click, agreement_click;
            CardView emp_doc, service_letter_agreement, upload_premises_pass;
            RecyclerView assigned_staff_recycler;
            Spinner spnr_status;
            boolean is_touch = true;
            TextView pay_now;
            CardView crdmain;
            CardView crd_order_history;
            private LinearLayout ll_drop_down;
            TextView update,quantity;
            TextView minus,plus;
            RelativeLayout rlLogin,outOfstock;
            LinearLayout ll_cart;
            public CustomViewHolder(View convertView) {
                super(convertView);
                crdmain = convertView.findViewById(R.id.crdmain);
                txt_title = convertView.findViewById(R.id.txt_title);
                description =   convertView.findViewById(R.id.description);
                price =   convertView.findViewById(R.id.price);
                image =   convertView.findViewById(R.id.image);
                quantity=convertView.findViewById(R.id.quantity);
                ll_cart=convertView.findViewById(R.id.ll_cart);
                rlLogin=convertView.findViewById(R.id.rlLogin);
                minus=convertView.findViewById(R.id.minus);
                plus=convertView.findViewById(R.id.plus);
                variety_name=convertView.findViewById(R.id.variety_name);
                weight=convertView.findViewById(R.id.weight);
                outOfstock=convertView.findViewById(R.id.outOfstock);
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
