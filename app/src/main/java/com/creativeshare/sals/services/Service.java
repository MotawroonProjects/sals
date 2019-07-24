package com.creativeshare.sals.services;




import com.creativeshare.sals.models.UserModel;

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
    @POST("api/verify")
    Call<UserModel> checkcode(@Field("mobile_code") String mobile_code,
                           @Field("mobile_number") String mobile_number,
                           @Field("verification") String verification
    );

}
