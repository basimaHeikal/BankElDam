package com.ipda3.bankeldam.data.rest;

import com.ipda3.bankeldam.data.model.cities.CitiesResponse;
import com.ipda3.bankeldam.data.model.contact.ContactResponse;
import com.ipda3.bankeldam.data.model.donationDetails.DonationDetailsResponse;
import com.ipda3.bankeldam.data.model.donationRequestCreate.DonationRequestCreateResponse;
import com.ipda3.bankeldam.data.model.donationRequests.DonationRequestsResponse;
import com.ipda3.bankeldam.data.model.governorates.GovernoratesResponse;
import com.ipda3.bankeldam.data.model.login.LoginResponse;
import com.ipda3.bankeldam.data.model.myFavouritesList.MyFavouritesListResponse;
import com.ipda3.bankeldam.data.model.newPassword.NewPasswordResponse;
import com.ipda3.bankeldam.data.model.notificationsCount.NotificationsCountResponse;
import com.ipda3.bankeldam.data.model.notificationsList.NotificationsListResponse;
import com.ipda3.bankeldam.data.model.notificationsSettings.NotificationsSettingsResponse;
import com.ipda3.bankeldam.data.model.postDetails.PostDetailsResponse;
import com.ipda3.bankeldam.data.model.postToggleFavourite.PostToggleFavouriteResponse;
import com.ipda3.bankeldam.data.model.posts.PostsResponse;
import com.ipda3.bankeldam.data.model.register.RegisterResponse;
import com.ipda3.bankeldam.data.model.registerToken.RegisterTokenResponse;
import com.ipda3.bankeldam.data.model.removeToken.RemoveTokenResponse;
import com.ipda3.bankeldam.data.model.report.ReportResponse;
import com.ipda3.bankeldam.data.model.resetPassword.ResetPasswordResponse;
import com.ipda3.bankeldam.data.model.settings.SettingsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //cities list api
    @GET("cities")
    Call<CitiesResponse> getCitiesList(@Query("governorate_id") String governorate_id);


    //governorates list api
    @GET("governorates")
    Call<GovernoratesResponse> getGovernoratesList();


    //donation details api
    @GET("donation-request")
    Call<DonationDetailsResponse> getDonationDetails(@Query("api_token") String api_token,
                                                     @Query("donation_id") String donation_id);

    //posts api
    @GET("posts")
    Call<PostsResponse> getPosts(@Query("api_token") String api_token,
                                 @Query("page") String page);

    //post details api
    @GET("post")
    Call<PostDetailsResponse> getPostDetails(@Query("api_token") String api_token,
                                             @Query("post_id") String post_id);

    //donation requests api
    @GET("donation-requests")
    Call<DonationRequestsResponse> getDonationRequests(@Query("api_token") String api_token);

    //notifications count api
    @GET("notifications-count")
    Call<NotificationsCountResponse> getNotificationsCount(@Query("api_token") String api_token);

    //notifications list api
    @GET("notifications")
    Call<NotificationsListResponse> getNotificationsList(@Query("api_token") String api_token);

    //my favourites list api
    @GET("my-favourites")
    Call<MyFavouritesListResponse> getMyFavouritesList(@Query("api_token") String api_token);


    //settings  api
    @GET("settings")
    Call<SettingsResponse> getSettings(@Query("api_token") String api_token);

    //donation request create api
    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestCreateResponse> DonationRequestCreate(@Field("api_token") String api_token,
                                                              @Field("patient_name") String patient_name,
                                                              @Field("patient_age") String patient_age,
                                                              @Field("blood_type") String blood_type,
                                                              @Field("bags_num") String bags_num,
                                                              @Field("hospital_name") String hospital_name,
                                                              @Field("hospital_address") String hospital_address,
                                                              @Field("city_id") String city_id,
                                                              @Field("phone") String phone,
                                                              @Field("notes") String notes,
                                                              @Field("latitude") String latitude,
                                                              @Field("longitude") String longitude);

    //register api
    @POST("register")
    @FormUrlEncoded
    Call<RegisterResponse> Register(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("birth_date") String birth_date,
                                    @Field("city_id") String city_id,
                                    @Field("phone") String phone,
                                    @Field("donation_last_date") String donation_last_date,
                                    @Field("password") String password,
                                    @Field("password_confirmation") String password_confirmation,
                                    @Field("blood_type") String blood_type);

    //login api
    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> Login(@Field("phone") String phone,
                              @Field("password") String password);

    //reset password api
    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPasswordResponse> ResetPassword(@Field("phone") String phone);

    //new password api
    @POST("new-password")
    @FormUrlEncoded
    Call<NewPasswordResponse> NewPassword(@Field("password") String password,
                                          @Field("password_confirmation") String password_confirmation,
                                          @Field("pin_code") String pin_code);

    //Register Token api
    @POST("register-token")
    @FormUrlEncoded
    Call<RegisterTokenResponse> RegisterToken(@Field("token") String token,
                                            @Field("api_token") String api_token,
                                            @Field("platform") String platform);

    //Remove Token api
    @POST("remove-token")
    @FormUrlEncoded
    Call<RemoveTokenResponse> RemoveToken(@Field("token") String token,
                                          @Field("api_token") String api_token);

    //post toggle favourite api
    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavouriteResponse> PostToggleFavourite(@Field("post_id") String post_id,
                                                          @Field("api_token") String api_token);

    //contact api
    @POST("contact")
    @FormUrlEncoded
    Call<ContactResponse> Contact(@Field("title") String title,
                                  @Field("api_token") String api_token,
                                  @Field("message") String message);

    //report api
    @POST("report")
    @FormUrlEncoded
    Call<ReportResponse> Report(@Field("api_token") String api_token,
                                @Field("message") String message);

    //notifications settings api
    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettingsResponse> NotificationsSettings(@Field("title") String title,
                                                              @Field("cities[]") List<String> cities,
                                                              @Field("blood_types[]") List<String> blood_types[]);

}
