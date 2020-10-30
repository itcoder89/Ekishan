package com.android.ekishan.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.AddressData;
import com.android.ekishan.model.CartListResponse;
import com.android.ekishan.model.CheckOutResponse;
import com.android.ekishan.model.TimeSlotData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryOptionFragment extends Fragment {
    LinearLayout edit,proceed;
    ApiInterface apiService;
    AddressData addressData;
    TextView address;
    ArrayList<TimeSlotData> timeSlotData;
    AppCompatSpinner spinner;
    ImageView datepick;
    EditText et_date;
    TextView delivery_charge;
    String isCanOrder="0";
    Date defaultSelectedDate = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.delivery_option_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        spinner=root.findViewById(R.id.spinner);
        address=root.findViewById(R.id.address);
        edit=root.findViewById(R.id.edit);
        proceed=root.findViewById(R.id.proceed);
        datepick=root.findViewById(R.id.datepick);
        et_date=root.findViewById(R.id.et_date);
        delivery_charge=root.findViewById(R.id.delivery_charge);
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startdate();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EditLocation());
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(),"Please add address first",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_date.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(),"Please select date first",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isCanOrder.equalsIgnoreCase("0")){
                    Toast.makeText(getActivity(), "Order not possible on this location,We will start orders on this location soon", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof PaymentOptionFragment)) {
                    MyApplication.date=et_date.getText().toString();
                    loadFragment(new PaymentOptionFragment());
                }

            }
        });
        checkout();
        return root;
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
    public void startdate() {
        try {
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            Date current = c.getTime();
            c.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = c.getTime();
            c.add(Calendar.DAY_OF_YEAR, 1);
            Date nextDay = c.getTime();
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            try {
                                //                            edtdate.setText(dayOfMonth + "-"
                                //                                    + (monthOfYear + 1) + "-" + year);
                                Calendar c = Calendar.getInstance();
                                c.setTime(defaultSelectedDate);
                                c.add(Calendar.DAY_OF_YEAR,2);
                                if (getSelectedDate(year, (monthOfYear), dayOfMonth).after(c.getTime())){
                                    Toast.makeText(getActivity(),"After two day delivery, Product price can change.",Toast.LENGTH_SHORT).show();
                                }
                                et_date.setText(formatDate(year, (monthOfYear), dayOfMonth));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date timeseven = dateFormat.parse("07:00 PM");

                Date CurrentTime=dateFormat.parse(dateFormat.format(new Date()));
                defaultSelectedDate=tomorrow;
                dpd.getDatePicker().setMinDate(tomorrow.getTime());
//
//                if (CurrentTime.after(timeseven)) {
//                    defaultSelectedDate=nextDay;
//                    dpd.getDatePicker().setMinDate(nextDay.getTime());
//                }else {
//                    defaultSelectedDate=tomorrow;
//                    dpd.getDatePicker().setMinDate(tomorrow.getTime());
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Date getSelectedDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();

        return date;
    }
    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
    private void checkout() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<CheckOutResponse> responseCall = apiService.checkout(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<CheckOutResponse>() {
            @Override
            public void onResponse(Call<CheckOutResponse> call, Response<CheckOutResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    addressData=response.body().getData().getAddress();
                    timeSlotData=response.body().getData().getTimeslot();
                    if (addressData!=null) {
                        address.setText(addressData.getGoogleLocation());
                        MyApplication.addressId=addressData.getAddress_book_id();
                        MyApplication.shippingCost=response.body().getData().getDelivery_charge();
                        MyApplication.totalprice =String.valueOf(Integer.parseInt(MyApplication.totalprice)+Integer.parseInt(response.body().getData().getDelivery_charge()));
                        delivery_charge.setText("Delivery Charge : "+response.body().getData().getDelivery_charge()+" Rs");
                        isCanOrder=response.body().getData().getDelivery_available();
                    }
                    if (timeSlotData.size()>0) {
                        MyApplication.selectedTimeSlot = timeSlotData.get(0).getId();
                        ArrayAdapter<TimeSlotData> spinnerArrayAdapter = new ArrayAdapter<TimeSlotData>(getActivity(), android.R.layout.simple_spinner_item, timeSlotData);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerArrayAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                MyApplication.selectedTimeSlot=timeSlotData.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckOutResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
