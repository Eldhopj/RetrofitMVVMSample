package com.example.retrofitmvvmsample;

import com.example.retrofitmvvmsample.modelClass.UsersBaseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Users {
    @GET("api/users")
    Call<UsersBaseModel> getUsers(
            @Query("page") int pageNum);
}
