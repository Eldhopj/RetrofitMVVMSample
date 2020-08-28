package com.example.retrofitmvvmsample.apiInterface

import com.example.retrofitmvvmsample.modelClass.UsersBaseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Users {
    @GET("api/users")
    suspend fun getUsers(
        @Query("page") pageNum: Int): Response<UsersBaseModel>
}