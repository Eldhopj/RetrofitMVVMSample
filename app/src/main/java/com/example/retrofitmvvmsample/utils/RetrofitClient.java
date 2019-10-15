package com.example.retrofitmvvmsample.utils;

import android.content.Context;

import com.example.retrofitmvvmsample.Users;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://reqres.in/";

    private static RetrofitClient mInstance;
    private final Retrofit retrofit;

    public static synchronized RetrofitClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(context);
        }
        return mInstance;
    }

    private RetrofitClient(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        /**If there are any headers its adds in here*/
//        httpClient.addInterceptor(chain -> {
//            Request original = chain.request();
//            Request.Builder requestBuilder = original.newBuilder()
//                    .header("Authorization", Utility.getRetrofitHeader()) // Headers
//                    .header(Constants.X_HEADER, Utility.getSecureUniqueHardwareID());
//            Request request = requestBuilder.build();
//            return chain.proceed(request);
//        });

        if (Constants.DEBUG_MODE) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public Users getUsers() {
        return retrofit.create(Users.class);
    }
}
