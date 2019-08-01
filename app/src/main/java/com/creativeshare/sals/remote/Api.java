package com.creativeshare.sals.remote;


import com.creativeshare.sals.services.Service;
import com.creativeshare.sals.tags.Tags;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit(String baseUrl)
    {


        Interceptor interceptor   = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request accept = request.newBuilder()
                        .addHeader("ACCEPT", "application/json")
                        .build();
                return chain.proceed(accept);
            }
        };

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();



        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();


        return retrofit;
    }


    public static Service getService()
    {
        return getRetrofit(Tags.base_url).create(Service.class);
    }
    public static Service getService(String base_url)
    {
        return getRetrofit(base_url).create(Service.class);
    }
}
