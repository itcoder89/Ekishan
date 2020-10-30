package com.android.ekishan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.android.ekishan.model.CategoryModel;
import com.android.ekishan.model.GetProductDetailsResponse;
import com.android.ekishan.model.GetProductsResponse;
import com.android.ekishan.model.ProduceByModel;
import com.android.ekishan.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSingleFragment extends Fragment {
    RecyclerView alertRecyclerview;
    RecyclerView offerRecyclerview;
    CategoryListAdapter adapter;
    CategoryListAdapter2 adapter2;
    ApiInterface apiService;
    String product_id;
    String veriety_id="0";
    CircleImageView image;
    TextView productName,description,how_to_grow;
    RelativeLayout rlLogin;
    TextView cart_quantity;
    ArrayList<CategoryModel> varietiesList=new ArrayList<>();

    public ProductSingleFragment(String product_id){
        this.product_id=product_id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_single_fragment, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        rlLogin=root.findViewById(R.id.rlLogin);
        image=root.findViewById(R.id.image);
        cart_quantity = getActivity().findViewById(R.id.cart_quantity);
        description=root.findViewById(R.id.description);
        productName=root.findViewById(R.id.productName);
        how_to_grow=root.findViewById(R.id.how_to_grow);
        alertRecyclerview=root.findViewById(R.id.category_recycle);
        offerRecyclerview=root.findViewById(R.id.produced_recycle);
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
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin()) {
                    if (varietiesList!=null){
                        if (varietiesList.size()>0){
                            veriety_id=varietiesList.get(0).getVariety_id();
                        }
                    }
                    addToCart(product_id,veriety_id,"1");
                }else {
                    MyApplication.showAlert(((MainActivity)getActivity()),"Please login first!");
                }
            }
        });
        get_product_detials();
        return root;
    }private void addToCart(String product_id,String variety_id, final String isadd) {
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
                        getFragmentManager().popBackStack();
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
    private void get_product_detials() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<GetProductDetailsResponse> responseCall = apiService.getProductDetails(product_id);


        responseCall.enqueue(new Callback<GetProductDetailsResponse>() {
            @Override
            public void onResponse(Call<GetProductDetailsResponse> call, Response<GetProductDetailsResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    Picasso.with(getActivity())
                            .load(response.body().getData().getProduct().getProducts_url())
                            .placeholder(R.drawable.place_holder) // optional
                            .error(R.drawable.place_holder)         // optional
                            .into(image);
                    description.setText(response.body().getData().getProduct().getProducts_description());
                    how_to_grow.setText(response.body().getData().getProduct().getHow_to_grow());
                    productName.setText(response.body().getData().getProduct().getProducts_name());
                    varietiesList=response.body().getData().getCategories();
                    if (varietiesList.size()>0) {
                        adapter = new CategoryListAdapter(getActivity(), varietiesList, ProductSingleFragment.this);
                        alertRecyclerview.setAdapter(adapter);
                    }
                    if (response.body().getData().getProduced_by().size()>0) {
                        adapter2 = new CategoryListAdapter2(getActivity(), response.body().getData().getProduced_by(), ProductSingleFragment.this);
                        offerRecyclerview.setAdapter(adapter2);
//                        offerRecyclerview.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetProductDetailsResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<CategoryModel> data;
        ProductSingleFragment fragment;

        public CategoryListAdapter(Context c, List<CategoryModel> data,ProductSingleFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public CategoryListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veg_category_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter.CustomViewHolder viewHolder = new CategoryListAdapter.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter.CustomViewHolder holder, final int position) {
            holder.category_name.setText(data.get(position).getVariety_name());
            holder.price.setText("₹"+data.get(position).getProducts_price());
            if (data.get(position).isChecked()){
                holder.checkbox.setChecked(true);
            }else {
                holder.checkbox.setChecked(false);
            }
            if (data.size()==1){
                holder.checkbox.setChecked(true);
            }
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (data.size()>1) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(position).setChecked(false);
                        }
                        veriety_id = data.get(position).getVariety_id();
                        data.get(position).setChecked(isChecked);
                        notifyItemChanged(position);
                    }else {
                        holder.checkbox.setChecked(true);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView category_name,price;
            CheckBox checkbox;
            public CustomViewHolder(View convertView) {
                super(convertView);
                category_name=convertView.findViewById(R.id.category_name);
                price=convertView.findViewById(R.id.price);
                checkbox=convertView.findViewById(R.id.checkbox);
            }

        }






    }

    public class CategoryListAdapter2 extends RecyclerView.Adapter<CategoryListAdapter2.CustomViewHolder> {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<ProduceByModel> data;
        ProductSingleFragment fragment;

        public CategoryListAdapter2(Context c, List<ProduceByModel> data, ProductSingleFragment fragment) {
            mContext = c;
            this.data = data;
            this.fragment=fragment;


        }

        @Override
        public CategoryListAdapter2.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produced_list_item, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            CategoryListAdapter2.CustomViewHolder viewHolder = new CategoryListAdapter2.CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CategoryListAdapter2.CustomViewHolder holder, final int position) {

            if (data.get(position).getImage()!=null) {
                if (!data.get(position).getImage().equalsIgnoreCase("")) {
                    Picasso.with(getActivity())
                            .load(data.get(position).getImage())
                            .placeholder(R.drawable.place_holder) // optional
                            .error(R.drawable.place_holder)         // optional
                            .into(holder.img);
                }
            }
            holder.name.setText(data.get(position).getName());
            holder.rating.setText(data.get(position).getRating()+"/5");

        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView name,rating;
            public CustomViewHolder(View convertView) {
                super(convertView);
                img=convertView.findViewById(R.id.img);
                name=convertView.findViewById(R.id.name);
                rating=convertView.findViewById(R.id.rating);
            }

        }






    }

}
