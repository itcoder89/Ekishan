package com.android.ekishan.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.CircleTransform;
import com.android.ekishan.FileUtils;
import com.android.ekishan.MainActivity;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.model.GetProfileDataModel;
import com.android.ekishan.model.GetProfileResponse;
import com.android.ekishan.model.MyAddressListResponse;
import com.android.ekishan.model.UpdateProfileResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    RelativeLayout rlupdate;
    EditText name,email,phone_no,company_no,zip_code,house_no,address;
    CheckBox checkbox;
    String customers_newsletter="0";
    ApiInterface apiService;
    CircleImageView image;
    GetProfileDataModel getProfileDataModel;
    String fileName = "";
    String Simage = "";
    private String fileImage = "";

    private static int GALLERY_REQUEST_CODE = 100;
    private static int CAMERA_REQUEST_CODE = 101;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_profile_layout, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        rlupdate=root.findViewById(R.id.rlupdate);
        checkbox=root.findViewById(R.id.checkbox);
        name=root.findViewById(R.id.name);
        email=root.findViewById(R.id.email);
        phone_no=root.findViewById(R.id.phone_no);
        company_no=root.findViewById(R.id.company_no);
        zip_code=root.findViewById(R.id.zip_code);
        house_no=root.findViewById(R.id.house_no);
        address=root.findViewById(R.id.address);
        image=root.findViewById(R.id.image);
        rlupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equalsIgnoreCase("")){
                    name.setError("Please enter name");
                }else {
                    updateCustomerInfo();
                }
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    customers_newsletter="1";
                }else {
                    customers_newsletter="0";
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                        getActivity().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    showImageSelectionDialog();
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
                    }
                }
            }
        });
        getProfileData();
        return root;
    }
    private void showImageSelectionDialog() {

        LayoutInflater inflater1 = getLayoutInflater();
        View alertLayout = inflater1.inflate(R.layout.layout_pic_upload, null);

        final TextView tvGallery = alertLayout.findViewById(R.id.tv_gallery);
        final TextView tvCamera = alertLayout.findViewById(R.id.tv_camera);
        final TextView tvCancel = alertLayout.findViewById(R.id.tv_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertLayout);
        AlertDialog alert = builder.create();


        tvGallery.setOnClickListener(view1 -> {
            alert.dismiss();
            openGallery();
        });


        tvCamera.setOnClickListener(view12 -> {
            alert.dismiss();
            openCamera();
        });

        tvCancel.setOnClickListener(view13 -> alert.dismiss());
        alert.show();


    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
   /*     Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Uri photoURI = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        startActivityForResult(intent, CAMERA_REQUEST_CODE);*/

        dispatchTakePictureIntent();

    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        getActivity().getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean flag = false;
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri image = data.getData();
                flag = true;
                fileImage = image==null?"": FileUtils.getRealPath(getActivity(),image);
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                flag = true;
                fileImage = currentPhotoPath;
            }
        }
        if (fileImage!=null) {
            if (!fileImage.equalsIgnoreCase("")) {
                if (flag) {
                    uploadUserImage();
                    return;
                }
            }
        }
        MyApplication.showAlert((MainActivity) getActivity(), "Can't load this file");
    }
    private void uploadUserImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File file = new File(fileImage);
        long length = (file.length() / (1024 * 1024));
        if (length > 1) {
            file = saveBitmapToFile(file);
            if (file==null){
                return;
            }
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part uploadPic = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<ResponseBody> responseCall = apiService.uploadImage(uploadPic);


        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res=response.body().string();
                    JSONObject jsonObject=new JSONObject(res);
                    if (jsonObject.getString("success").equals("1")) {
                        JSONObject jsonObject1=new JSONObject(jsonObject.getString("data"));
                        Simage=jsonObject1.getString("image");
                        if (Simage!=null&&!Simage.equalsIgnoreCase("")) {
                            Picasso.with(getActivity())
                                    .load(Simage)
                                    .placeholder(R.drawable.defoult_img1) // optional
                                    .error(R.drawable.defoult_img1)
                                    .transform(new CircleTransform())// optional
                                    .into(image);
                        }
                    }else {
                        MyApplication.showAlert(((MainActivity)getActivity()),jsonObject.getString("message"));
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Time out.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
//            file.createNewFile();
            File file_out=saveFileAfterCompress();
            FileOutputStream outputStream = new FileOutputStream(file_out);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file_out;
        } catch (Exception e) {
            MyApplication.showAlert((MainActivity) getActivity(), ""+e);
            return null;
        }
    }
    public File saveFileAfterCompress(){
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss")
                .format(new Date());
        String filename = "image_" + timeStamp;
        String fileNameExtension = ".jpg";
        File imageStorageFolder = new File(Environment
                .getExternalStorageDirectory().toString()
                + File.separator
                + "EKISAN/images");
        if (!imageStorageFolder.exists()) {
            imageStorageFolder.mkdirs();
        }
        return  new File(imageStorageFolder, filename + fileNameExtension);
    }
    private void getProfileData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<GetProfileResponse> responseCall = apiService.getProfileData(MyApplication.getCustomerId());


        responseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    getProfileDataModel=response.body().getData();
                    name.setText(getProfileDataModel.getCustomers_firstname());
                    email.setText(getProfileDataModel.getCustomers_email_address());
                    phone_no.setText(getProfileDataModel.getCustomers_telephone());
                    company_no.setText(getProfileDataModel.getCompany_name());
                    zip_code.setText(getProfileDataModel.getZip_code());
                    house_no.setText(getProfileDataModel.getHouse_no());
                    address.setText(getProfileDataModel.getAddress());
                    if (getProfileDataModel.getCustomers_newsletter().equalsIgnoreCase("1")){
                        checkbox.setChecked(true);
                    }else {
                        checkbox.setChecked(false);
                    }
                    if (getProfileDataModel.getCustomers_picture()!=null&&!getProfileDataModel.getCustomers_picture().equalsIgnoreCase("")) {
                        Picasso.with(getActivity())
                                .load(getProfileDataModel.getCustomers_picture())
                                .placeholder(R.drawable.defoult_img1) // optional
                                .error(R.drawable.defoult_img1)
                                .transform(new CircleTransform())// optional
                                .into(image);
                    }
                }else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateCustomerInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle(getResources().getString(R.string.text_logging_in));
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        startActivity(new Intent(LoginActivity.this,CoursesActivity.class));
        Call<UpdateProfileResponse> responseCall = apiService.updateCustomerInfo(MyApplication.getCustomerId(),name.getText().toString(),customers_newsletter,company_no.getText().toString(),zip_code.getText().toString(),address.getText().toString(),phone_no.getText().toString(),house_no.getText().toString(),email.getText().toString(),Simage);


        responseCall.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess().equals("1")) {
                    MyApplication.setMobile(phone_no.getText().toString());
                    Toast.makeText(getActivity(),"Profile updated successfully",Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
