package com.android.ekishan.vendor.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.HomeProductList;
import com.android.ekishan.model.ResponseVendorNews;
import com.android.ekishan.model.VendorNewsList;
import com.android.ekishan.model.VendorResponseAgroItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AgroNewsFragment extends Fragment {
    RecyclerView offerRecyclerview;
    ArrayList<VendorNewsList> product_list = new ArrayList<>();
    ApiInterface apiService;
    TextView nodata;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agro_news_layout, container, false);
        offerRecyclerview=view.findViewById(R.id.offerRecycler);
         nodata=view.findViewById(R.id.nodata);
        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        offerRecyclerview.setLayoutManager(staggeredGridLayoutManager1);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        getOrders();
        return view;
    }

    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseVendorNews> responseCall = apiService.vendor_news();


        responseCall.enqueue(new Callback<ResponseVendorNews>() {
            @Override
            public void onResponse(Call<ResponseVendorNews> call, Response<ResponseVendorNews> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    product_list = response.body().getData();
                    if (product_list.size() > 0) {
                        nodata.setVisibility(View.GONE);
                        offerRecyclerview.setVisibility(View.VISIBLE);
                        setAdapter();
                    } else {
                        nodata.setVisibility(View.VISIBLE);
                        offerRecyclerview.setVisibility(View.GONE);
                    }
                } else {
                    nodata.setVisibility(View.VISIBLE);
                    offerRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseVendorNews> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                nodata.setVisibility(View.VISIBLE);
                offerRecyclerview.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapter(){
        VegetablesListAdapter adapter = new VegetablesListAdapter(getActivity(), product_list,  this);
        offerRecyclerview.setAdapter(adapter);
    }
    public class VegetablesListAdapter extends RecyclerView.Adapter<VegetablesListAdapter.CustomViewHolder> implements Filterable {
        private Context mContext;
        private LayoutInflater layoutInflater;
        List<VendorNewsList> data;
        List<VendorNewsList> list;


        AgroNewsFragment fragment;

        public VegetablesListAdapter(Context c, List<VendorNewsList> data, AgroNewsFragment fragment) {
            mContext = c;
            this.data = data;
            this.list = data;
            this.fragment = fragment;
        }

        @Override
        public  VegetablesListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agro_news_item_layout, null, false);
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
            holder.txt_title.setText(""+data.get(position).getNews_name());
            holder.description.setText(""+ Html.fromHtml(data.get(position).getNews_description()));
            holder.date.setText(""+data.get(position).getNews_date_added());
            Picasso.with(getActivity())
                    .load(data.get(position).getNews_image())
                    .placeholder(R.drawable.agro_news) // optional
                    .error(R.drawable.agro_news)         // optional
                    .into(holder.image);
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
                        List<VendorNewsList> filteredList = new ArrayList<>();
                        for (VendorNewsList row : data) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getNews_name().toLowerCase().contains(charString.toLowerCase())) {
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
                    data = (ArrayList<VendorNewsList>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, description, price, date, call, order_status;
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
            RelativeLayout rlLogin;
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
                date=convertView.findViewById(R.id.date);

            }

        }


    }
}
