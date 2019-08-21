package com.creativeshare.sals.services;


import com.creativeshare.sals.models.Address_Model;
import com.creativeshare.sals.models.Address_Models;
import com.creativeshare.sals.models.Bike_Model;
import com.creativeshare.sals.models.CityModel;
import com.creativeshare.sals.models.Country_Model;
import com.creativeshare.sals.models.Help_Cat_Model;
import com.creativeshare.sals.models.Other_Services_Model;
import com.creativeshare.sals.models.Pay_Model;
import com.creativeshare.sals.models.Payment_Result_Model;
import com.creativeshare.sals.models.PlaceGeocodeData;
import com.creativeshare.sals.models.Prectage_Model;
import com.creativeshare.sals.models.Questions_Model;
import com.creativeshare.sals.models.Quote_Model;
import com.creativeshare.sals.models.Sercvices_Centers;
import com.creativeshare.sals.models.Shipment_Response_Model;
import com.creativeshare.sals.models.UserModel;
import com.creativeshare.sals.models.Visit_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Service {
    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoDatapos(@Query(value = "address") String address,
                                         @Query(value = "language") String language,
                                         @Query("ensor") Boolean ensor,
                                         @Query(value = "key") String key);

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
    @GET("api/get-rate")
    Call<Prectage_Model> getappcommission(
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

    @FormUrlEncoded
    @POST("api/profile/address/add")
    Call<Address_Model> Addadress(
            @Header("Authorization") String Authorization,

            @Field("building_number") String building_number,
            @Field("floor_number") String floor_number,
            @Field("flat_number") String flat_number,

            @Field("notes") String notes,
            @Field("address_type") String address_type,
            @Field("address") String address,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("is_primary") String is_primary
    );

    @FormUrlEncoded
    @POST("api/profile/address")
    Call<Address_Models> Singleadress(
            @Header("Authorization") String Authorization,

            @Field("address_id") String address_id

    );

    @POST("api/profile/address/main")
    Call<Address_Models> Singleadress(
            @Header("Authorization") String Authorization


    );

    @FormUrlEncoded
    @POST("api/profile/address/update")
    Call<Address_Model> updateadress(
            @Header("Authorization") String Authorization,

            @Field("building_number") String building_number,
            @Field("floor_number") String floor_number,
            @Field("flat_number") String flat_number,

            @Field("notes") String notes,
            @Field("address_type") String address_type,
            @Field("address") String address,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("is_primary") String is_primary,
            @Field("address_id") String address_id
    );

    @GET("api/sizes")
    Call<Bike_Model> getBike(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @GET("api/faqs")
    Call<Questions_Model> getQuestions(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @GET("api/faqs/categories")
    Call<Help_Cat_Model> getHelpcat(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @GET("api/other-services")
    Call<Other_Services_Model> getOtherservices(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @GET("api/centers")
    Call<Sercvices_Centers> getservicescenter(

            @Header("lang") String lang

    );

    @GET("api/countries")
    Call<Country_Model> getCoutry(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @GET("/api/cities")
    Call<CityModel> getCity(
            @Header("Authorization") String Authorization,
            @Header("lang") String lang

    );

    @FormUrlEncoded
    @POST("api/get-quote")
    Call<Quote_Model> get_quote(
            @Header("Authorization") String Authorization,
            @Field("date") String date,
            @Field("pieces[][weight]") List<String> piece,
            @Field("is_dutiable") String is_dutiable,
            @Field("ready_time") String ready_time,
            @Field("ready_time_gmt_offset") String ready_time_gmt_offset,
            @Field("dimension_unit") String dimension_unit,
            @Field("weight_unit") String weight_unit,
            @Field("payment_country_code") String payment_country_code,
            @Field("from_country_code") String from_country_code,
            @Field("from_city") String from_city,
            @Field("to_city") String to_city,
            @Field("to_country_code") String to_country_code

    );

    @FormUrlEncoded
    @POST("api/firebase-token")
    Call<ResponseBody> updateToken(
            @Header("Authorization") String Authorization,
            @Field("number_token") String number_token
    );
    @POST("api/make-payment")
    Call<Payment_Result_Model> Payship(
            @Header("Authorization") String Authorization,
            @Body Pay_Model pay_model
    );
    @FormUrlEncoded
    @POST("api/dhl-make-shipment")
    Call<Shipment_Response_Model> makeshipment(
            @Header("Authorization") String Authorization,

            @Field("is_parcel") String is_parcel,
            @Field("from_company_name") String from_company_name,
            @Field("from_address") String from_address,

            @Field("from_city") String from_city,
            @Field("from_postal_code") String from_postal_code,
            @Field("from_country_code") String from_country_code,
            @Field("from_country_name") String from_country_name,
            @Field("from_person_name") String from_person_name,
            @Field("from_phone_number") String from_phone_number,
            @Field("from_phone_ext") String from_phone_ext,
            @Field("from_email") String from_email,
            @Field("to_company_name") String to_company_name,

            @Field("to_phone_number") String to_phone_number,
            @Field("to_phone_ext") String to_phone_ext,
            @Field("to_email") String to_email,
            @Field("shipment_date") String shipment_date,
            @Field("desc") String desc,
            @Field("total_weight") String total_weight,
            @Field("to_address") String to_address,
            @Field("to_city") String to_city,
            @Field("to_postal_code") String to_postal_code,
            @Field("to_country_code") String to_country_code,
            @Field("to_country_name") String to_country_name,
            @Field("to_person_name") String to_person_name,
            @Field("total_items") String total_items,
            @Field("pieces[0][weight]") List<String> weight,
            @Field("pieces[0][dim_weight]") List<String> dim_weight,
            @Field("pieces[0][width]") List<String> width,
            @Field("pieces[0][height]") List<String> height,
            @Field("pieces[0][depth]") List<String> depth

            // @FieldMap Map<String, List> hashMap


            );
}
