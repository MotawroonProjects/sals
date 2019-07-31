package com.creativeshare.sals.services;




import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.models.Visit_Model;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Service {
    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> SignIn(@Field("mobile_code") String mobile_code,
                           @Field("mobile_number") String mobile_number,
                           @Field("software_type") String software_type
                           );
    @FormUrlEncoded
    @POST("api/resend")
    Call<UserModel> resendsms(@Field("mobile_code") String mobile_code,
                           @Field("mobile_number") String mobile_number
    );

    @FormUrlEncoded
    @POST("api/verify")
    Call<UserModel> checkcode(@Field("mobile_code") String mobile_code,
                           @Field("mobile_number") String mobile_number,
                           @Field("verification") String verification
    );

    @POST("api/logout")
    Call<ResponseBody> Logout(
            @Header("Authorization") String Authorization

    );

    @FormUrlEncoded
    @POST("api/visits-count")
    Call<Visit_Model> updateVisit(@Field("type") String type, @Field("date") String date);

    @POST("api/profile/notification/update")
    Call<UserModel> changenotifystatus(
            @Header("Authorization") String Authorization

    );
    @FormUrlEncoded
    @POST("api/profile/name/update")
    Call<UserModel> updateName(@Field("first_name") String first_name,
                               @Field("last_name") String last_name,
                                 @Header("Authorization") String Authorization
    );
    @GET("api/profile/address/all")
    Call<Address_Model> getalladdress(
            @Header("Authorization") String Authorization

    );

}
