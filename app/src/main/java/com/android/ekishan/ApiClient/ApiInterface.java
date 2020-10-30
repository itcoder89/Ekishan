package com.android.ekishan.ApiClient;


import com.android.ekishan.chat.model.APIChatModelData;
import com.android.ekishan.chat.model.APISendMessageData;
import com.android.ekishan.delivery.model.DBHistoryListData;
import com.android.ekishan.model.CartListResponse;
import com.android.ekishan.model.CheckApprovalResponse;
import com.android.ekishan.model.CheckOutResponse;
import com.android.ekishan.model.CollectionsListData;
import com.android.ekishan.model.DeliverDashboarResponse;
import com.android.ekishan.model.DeliveryOrderItemsResponse;
import com.android.ekishan.model.GetProductDetailsResponse;
import com.android.ekishan.model.GetProductsResponse;
import com.android.ekishan.model.GetProfileResponse;
import com.android.ekishan.model.GetVegResponse;
import com.android.ekishan.model.MyAddressListResponse;
import com.android.ekishan.model.NotificationResponse;
import com.android.ekishan.model.OrderDetailsData;
import com.android.ekishan.model.OrderDetailsResponse;
import com.android.ekishan.model.RegisterVegResponse;
import com.android.ekishan.model.ReponseProfile;
import com.android.ekishan.model.ResponseBasket;
import com.android.ekishan.model.ResponseContactUs;
import com.android.ekishan.model.ResponseDeliveryBoyAcceptOrder;
import com.android.ekishan.model.ResponseGetReview;
import com.android.ekishan.model.ResponseLogin;
import com.android.ekishan.model.ResponseOrder;
import com.android.ekishan.model.ResponseVendorNews;
import com.android.ekishan.model.ResponseVendorProfile;
import com.android.ekishan.model.ResponseVendorWallet;
import com.android.ekishan.model.ResponseVerifyOtp;
import com.android.ekishan.model.SalesReportListData;
import com.android.ekishan.model.SocialLoginResponse;
import com.android.ekishan.model.UpdateProfileResponse;
import com.android.ekishan.model.VendorCollectionListData;
import com.android.ekishan.model.VendorOrderDetailData;
import com.android.ekishan.model.VendorResponseAgroItems;
import com.android.ekishan.model.VendorResponseCollection;
import com.android.ekishan.model.VendorResponseRegister;
import com.android.ekishan.model.VendorTransactionDemandData;
import com.android.ekishan.model.VendorTransactionDetailData;
import com.android.ekishan.model.VendorTransactionListData;
import com.android.ekishan.model.VersionData;
import com.android.ekishan.model.reponseHome;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("processLogin")
    Call<ResponseLogin> login(@Field("customers_telephone") String customers_telephone,@Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("processRegistration")
    Call<ResponseLogin> register(@Field("customers_firstname") String customers_firstname,@Field("customers_email_address") String customers_email_address,@Field("customers_telephone") String customers_telephone,@Field("customers_lastname") String customers_lastname,@Field("fcm_token") String fcm_token,@Field("refercode") String refercode);


    @FormUrlEncoded
    @POST("verifyOtp")
    Call<ResponseLogin> verifyOtp(@Field("otp") String otp,@Field("customers_telephone") String customers_telephone,@Field("isregister") String isregister);



    @FormUrlEncoded
    @POST("getProfile")
    Call<ReponseProfile> getProfile(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("vendors/getVendotTodayColletionsList")
    Call<CollectionsListData> getVendotTodayColletionsList(@Field("vendors_id") String customers_id);

    @FormUrlEncoded
    @POST("vendors/getVendotSaleRequest")
    Call<SalesReportListData> getVendotSaleRequest(@Field("vendors_id") String customers_id);


    @FormUrlEncoded
    @POST("getOrderDetails")
    Call<OrderDetailsData> getCollectionOrderDetails(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("getOrderDetails")
    Call<VendorOrderDetailData> getVendorOrderDetail(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("vendors/transactionDetails")
    Call<VendorTransactionDetailData> transactionDetails(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("vendors/transactionDetails")
    Call<VendorTransactionDemandData> transactionDetails1(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("getMessage")
    Call<APIChatModelData> getMessage(@Field("sender_id") String sender_id,@Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("sendMessage")
    Call<APISendMessageData> sendMessage(@Field("sender_id") String sender_id,@Field("message") String message,@Field("user_type") String user_type);

    @FormUrlEncoded
    @POST("vendors/getVendotColletionsList")
    Call<VendorCollectionListData> getVendorColletionsList(@Field("vendors_id") String customers_id);

    @FormUrlEncoded
    @POST("customers-home")
    Call<reponseHome> customers_home(@Field("customers_id") String customers_id,@Field("hashKey") String hashKey);

    @FormUrlEncoded
    @POST("getMySubscription")
    Call<ResponseOrder> getMySubscription(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("getOrders")
    Call<ResponseOrder> getOrderList(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("getOrderDetails")
    Call<OrderDetailsResponse> getOrderDetails(@Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("getSubscriptionDetails")
    Call<OrderDetailsResponse> getSubscriptionDetails(@Field("subscription_id") String subscription_id);

    @FormUrlEncoded
    @POST("getCart")
    Call<CartListResponse> getCartList(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("getAllAddress")
    Call<MyAddressListResponse> getAllAddress(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("updateDefaultAddress")
    Call<UpdateProfileResponse> updateDefaultAddress(@Field("customers_id") String customers_id,@Field("address_book_id") String address_book_id);

    @FormUrlEncoded
    @POST("deleteShippingAddress")
    Call<UpdateProfileResponse> deleteAddress(@Field("customers_id") String customers_id,@Field("address_book_id") String address_book_id);


    @FormUrlEncoded
    @POST("notifications")
    Call<NotificationResponse> notifications(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("getProfileData")
    Call<GetProfileResponse> getProfileData(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("getProductBySearch")
    Call<GetProductsResponse> getProductBySearch(@Field("customers_id") String customers_id,@Field("products_type") String products_type,@Field("search") String search);

    @FormUrlEncoded
    @POST("getCustomerWallet")
    Call<ResponseBody> getCustomerWallet(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("balanceTransaction")
    Call<ResponseVendorWallet> balanceTransaction(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("checkout")
    Call<CheckOutResponse> checkout(@Field("customers_id") String customers_id);


    @FormUrlEncoded
    @POST("updateCustomerInfo")
    Call<UpdateProfileResponse> updateCustomerInfo(@Field("customers_id") String customers_id,
                                                   @Field("customers_firstname") String customers_firstname,
                                                   @Field("customers_newsletter") String customers_newsletter,
                                                   @Field("comapany_name") String comapany_name,
                                                   @Field("zip_code") String zip_code,
                                                   @Field("address") String address,
                                                   @Field("mobile") String mobile,
                                                   @Field("house_no") String house_no,
                                                   @Field("customers_email_address") String customers_email_address,
                                                   @Field("customers_picture") String customers_picture);


    @FormUrlEncoded
    @POST("placeOrder")
    Call<ResponseBody> placeOrder(@Field("customers_id") String customers_id,
                                                   @Field("cashback_used") String cashback_used,
                                                   @Field("totalPrice") String totalPrice,
                                                   @Field("shipping_cost") String shipping_cost,
                                                   @Field("total_tax") String total_tax,
                                                   @Field("is_coupon_applied") String is_coupon_applied,
                                                   @Field("address_id") String address_id,
                                                   @Field("payment_type") String payment_type,
                                                   @Field("order_date") String order_date,
                                                   @Field("timeslot") String timeslot,
                                                   @Field("transaction_id") String transaction_id);

    @FormUrlEncoded
    @POST("getReview")
    Call<ResponseGetReview> getReview(@Field("customers_id") String customers_id);
	
	 @FormUrlEncoded
    @POST("addShippingAddress")
    Call<UpdateProfileResponse> addShippingAddress(@Field("address_book_id") String address_book_id,
                                                   @Field("customers_id") String customers_id,
                                                   @Field("entry_firstname") String entry_firstname,
                                                   @Field("entry_lastname") String entry_lastname,
                                                   @Field("entry_mobile") String entry_mobile,
                                                   @Field("entry_street_address") String entry_street_address,
                                                   @Field("entry_postcode") String entry_postcode,
                                                   @Field("entry_city") String entry_city,
                                                   @Field("entry_state") String entry_state,
                                                   @Field("googleLocation") String googleLocation,
                                                   @Field("house_no") String house_no,
                                                   @Field("latitude") String latitude,
                                                   @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("updateReview")
    Call<UpdateProfileResponse> updateReview(@Field("reviews_id") String reviews_id,
                                                   @Field("reviews_rating") String reviews_rating,
                                                   @Field("title") String title,
                                                   @Field("review") String review,
                                                   @Field("image") String image
    );

    @FormUrlEncoded
    @POST("sendOtpSocialVerify")
    Call<ResponseBody> sendOtpSocialVerify(@Field("customers_id") String customers_id,
                                                   @Field("customers_telephone") String customers_telephone);

    @FormUrlEncoded
    @POST("verifyOtpSocialVerify")
    Call<ResponseBody> verifyOtpSocialVerify(@Field("customers_id") String customers_id,
                                                   @Field("customers_telephone") String customers_telephone,
                                                   @Field("otp") String otp);
    @FormUrlEncoded
    @POST("resendOtp")
    Call<ResponseBody> resendOtp(@Field("customers_id") String customers_id,@Field("isregister") String isregister);
    @FormUrlEncoded
    @POST("addReview")
    Call<UpdateProfileResponse> addReview(@Field("customers_id") String customers_id,@Field("products_id") String products_id,
                                                   @Field("rating") String rating,
                                                   @Field("order_id") String order_id,
                                                   @Field("title") String title,
                                                   @Field("review") String review,
                                                   @Field("image") String image
    );
    @FormUrlEncoded
    @POST("addToCart")
    Call<ResponseBody> addToCart(@Field("customers_id") String customers_id,@Field("products_id") String products_id,@Field("variety_id") String variety_id,
                                                   @Field("isadd") String isadd,@Field("basket_id") String basket_id);
	
	@POST("basket")
    Call<ResponseBasket> basket();

    @FormUrlEncoded
    @POST("googleRegistration")
    Call<SocialLoginResponse> googleRegistration(@Field("socialId") String socialId, @Field("givenName") String givenName,
                                                 @Field("socialType") String socialType,
                                                 @Field("email") String email,
                                                 @Field("phone") String phone,
                                                 @Field("idToken") String idToken,
                                                 @Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("getProductDetails")
    Call<GetProductDetailsResponse> getProductDetails(@Field("products_id") String products_id);




    // vendor
    @FormUrlEncoded
    @POST("vendors/processLogin")
    Call<UpdateProfileResponse> vendor_login(@Field("vendors_phone") String vendors_phone,@Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("vendors/sendNewOtp")
    Call<UpdateProfileResponse> vendor_sendNewOtp(@Field("vendors_phone") String vendors_phone,@Field("isregister") String isregister);

    @FormUrlEncoded
    @POST("vendors/buyAgroItems")
    Call<UpdateProfileResponse> buyAgroItems(@Field("vendors_id") String vendors_id,@Field("agro_items_id") String agro_items_id);

    @FormUrlEncoded
    @POST("vendors/processRegistration")
    Call<VendorResponseRegister> vendor_register(@Field("vendors_firstname") String vendors_firstname, @Field("vendors_aadhar_number") String vendors_aadhar_number, @Field("vendors_email_address") String vendors_email_address, @Field("vendors_phone") String vendors_phone,@Field("fcm_token") String fcm_token);


    @FormUrlEncoded
    @POST("vendors/verifyOtp")
    Call<ResponseVerifyOtp> vendor_verifyOtp(@Field("vendors_phone") String vendors_phone, @Field("otp") String otp, @Field("isregister") String isregister);

    @FormUrlEncoded
    @POST("vendors/getColletionsList")
    Call<VendorResponseCollection> getColletionsList(@Field("vendors_id") String vendors_id);


    @FormUrlEncoded
    @POST("vendors/getVegetables")
    Call<GetVegResponse> getVegetables(@Field("vendors_id") String vendors_id);

    @FormUrlEncoded
    @POST("vendors/getDemandsList")
    Call<VendorResponseCollection> getDemandsList(@Field("vendors_id") String vendors_id);

    @FormUrlEncoded
    @POST("vendors/acceptDeclineDemand")
    Call<ResponseBody> acceptDeclineDemand(@Field("vendors_id") String vendors_id,
                                                       @Field("accept_status") String accept_status,
                                                       @Field("vendors_demands_id") String vendors_demands_id,
                                                       @Field("available_quantity") String available_quantity,
                                                       @Field("vendor_decline_comment") String vendor_decline_comment);

    @FormUrlEncoded
    @POST("vendors/getDemandsHistory")
    Call<VendorResponseCollection> getDemandsHistory(@Field("vendors_id") String vendors_id);

   @FormUrlEncoded
    @POST("vendors/vendorBalanceTransaction")
    Call<ResponseVendorWallet> vendorBalanceTransaction(@Field("vendors_id") String vendors_id);

   @FormUrlEncoded
    @POST("vendors/vendorTransaction")
    Call<VendorTransactionListData> vendorTransaction(@Field("vendors_id") String vendors_id);

   @FormUrlEncoded
    @POST("vendors/profileDetails")
    Call<ResponseVendorProfile> profileDetails(@Field("vendors_id") String vendors_id);

   @FormUrlEncoded
    @POST("vendors/updateProfileDetails")
    Call<UpdateProfileResponse> updateProfileDetails(@Field("vendors_id") String vendors_id,@Field("vendors_firstname") String vendors_firstname,@Field("vendors_aadhar_number") String vendors_aadhar_number,@Field("vendors_email_address") String vendors_email_address,@Field("vendors_phone") String vendors_phone,@Field("image") String image,@Field("address") String address);

   @FormUrlEncoded
    @POST("vendors/agroItems")
    Call<VendorResponseAgroItems> agroItems(@Field("vendors_id") String vendors_id);

    @GET("vendors/News")
    Call<ResponseVendorNews> vendor_news();

    @GET("getAppVersion")
    Call<VersionData> getAppVersion();

    @GET("contactUs")
    Call<ResponseContactUs> contactUs();

    @FormUrlEncoded
    @POST("vendors/sale")
    Call<RegisterVegResponse> saleProducts(@Field("vendors_id") String vendors_id,@Field("products") String products);

    @FormUrlEncoded
    @POST("vendors/requestApproval")
    Call<RegisterVegResponse> requestApproval(@Field("vendors_id") String vendors_id,@Field("approve_by") String approve_by);

    @FormUrlEncoded
    @POST("vendors/checkApproval")
    Call<CheckApprovalResponse> checkApproval(@Field("vendors_id") String vendors_id);

    @FormUrlEncoded
    @POST("vendors/staffApprovalConfirmation")
    Call<CheckApprovalResponse> staffApprovalConfirmation(@Field("vendors_id") String vendors_id,@Field("staff_id") String staff_id);

    @FormUrlEncoded
    @POST("vendors/requestAmount")
    Call<ResponseBody> requestAmount(@Field("vendors_id") String vendors_id,@Field("amount") String amount,@Field("payment_mode") String payment_mode);

    @FormUrlEncoded
    @POST("vendors/vegetablesRegistration")
    Call<RegisterVegResponse> vegetablesRegistration(@Field("vendors_farm_location") String vendors_farm_location,
                                                     @Field("vendors_id") String vendors_id,
                                                     @Field("vendors_bank_name") String vendors_bank_name,
                                                     @Field("vendors_bank_branch_name") String vendors_bank_branch_name,
                                                     @Field("vendors_bank_acc_number") String vendors_bank_acc_number,
                                                     @Field("vendors_acc_holder_name") String vendors_acc_holder_name,
                                                     @Field("vendors_bank_ifsc") String vendors_bank_ifsc,
                                                     @Field("postal_code") String postal_code,
                                                     @Field("address") String address,
                                                     @Field("products") String products);

    @FormUrlEncoded
    @POST("vendors/getVendorRegisteredVegetables")
    Call<GetVegResponse> getVendorRegisteredVegetables(@Field("vendors_id") String vendors_id);


    //Delivery
    @FormUrlEncoded
    @POST("deliveryBoyLogin")
    Call<UpdateProfileResponse> deliveryBoyLogin(@Field("phone") String phone,@Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("deliveryBoyVerifyLogin")
    Call<ResponseVerifyOtp> deliveryBoyVerifyLogin(@Field("telephone") String telephone, @Field("otp") String otp, @Field("isregister") String isregister);


    @FormUrlEncoded
    @POST("deliveryBoySendNewOtp")
    Call<UpdateProfileResponse> deliveryBoySendNewOtp(@Field("telephone") String vendors_phone);

    @FormUrlEncoded
    @POST("deliveryBoyAcceptDeclineOrder")
        Call<ResponseDeliveryBoyAcceptOrder> deliveryBoyAcceptDeclineOrder(@Field("agro_item_id") String agro_item_id,@Field("id") String id, @Field("response") String response, @Field("order_id") String order_id, @Field("subscription_id") String subscription_id, @Field("package_delivery_id") String package_delivery_id, @Field("reason") String reason, @Field("vendors_sales_id") String vendors_sales_id, @Field("order_type") String order_type);

    @FormUrlEncoded
    @POST("deliveryBoyOrdersRequest")
    Call<DeliverDashboarResponse> deliveryBoyOrdersRequest(@Field("id") String id,@Field("type") String type);

    @FormUrlEncoded
    @POST("deliveryBoyOrdersRequest")
    Call<DBHistoryListData> deliveryBoyHistoryList(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("deliveryBoyOrderItems")
    Call<DeliveryOrderItemsResponse> deliveryBoyOrderItems(@Field("agro_item_id") String agro_item_id,@Field("id") String id, @Field("order_id") String order_id, @Field("subscription_id") String subscription_id, @Field("package_delivery_id") String package_delivery_id, @Field("order_type") String order_type, @Field("vendors_sales_id") String vendors_sales_id );

    @FormUrlEncoded
    @POST("deliveryBoyConfirmOrder")
    Call<ResponseDeliveryBoyAcceptOrder> deliveryBoyConfirmOrder(@Field("agro_item_id") String agro_item_id,@Field("id") String id, @Field("order_id") String order_id, @Field("otp") String otp, @Field("payment_by") String payment_by, @Field("subscription_id") String subscription_id, @Field("package_delivery_id") String package_delivery_id, @Field("vendors_sales_id") String vendors_sales_id, @Field("order_type") String order_type, @Field("cash_amount") String cash_amount, @Field("wallet_amount") String wallet_amount);

    @FormUrlEncoded
    @POST("addToCustomerWallet")
    Call<ResponseBody> addToCustomerWallet(@Field("customers_id") String customers_id, @Field("user_type") String user_type, @Field("amount") String amount, @Field("transaction_id") String transaction_id);

    @Multipart
    @POST("uploadImage")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file);
}
