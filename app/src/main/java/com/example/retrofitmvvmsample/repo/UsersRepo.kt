package com.example.retrofitmvvmsample.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvmsample.modelClass.ApiResponse
import com.example.retrofitmvvmsample.modelClass.UsersBaseModel
import com.example.retrofitmvvmsample.utils.RetrofitClient

private const val TAG = "UsersRepo"

class UsersRepo private constructor(private val context: Context) {


    suspend fun getUsers(page: Int): MutableLiveData<ApiResponse<UsersBaseModel>> {
        val mutableLiveData = MutableLiveData<ApiResponse<UsersBaseModel>>()

        mutableLiveData.postValue(ApiResponse.Loading())
        try {
            val response = RetrofitClient.getInstance(context).users.getUsers(page)
            if (response.isSuccessful) {
                response.body().let {
                    mutableLiveData.postValue(ApiResponse.Success(it!!))
                }
            } else {
                Log.e(TAG, "onResponse: " + "ErrorCode : " + response.code())
                mutableLiveData.postValue(ApiResponse.Error(response.message()))
            }
        } catch (t: Throwable) {
            t.localizedMessage.let {
                mutableLiveData.postValue(ApiResponse.Error(it!!))
            }
        }
        return mutableLiveData
    }

    companion object {
        private var repoInstance: UsersRepo? = null
        fun getInstance(context: Context): UsersRepo? {
            if (repoInstance == null) {
                repoInstance = UsersRepo(context)
            }
            return repoInstance
        }
    }
}