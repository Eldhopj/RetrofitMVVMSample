package com.example.retrofitmvvmsample.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.retrofitmvvmsample.modelClass.State
import com.example.retrofitmvvmsample.modelClass.UsersBaseModel
import com.example.retrofitmvvmsample.utils.RetrofitClient

private const val TAG = "UsersRepo"

class UsersRepo private constructor(private val retrofitClient: RetrofitClient) {

    fun getUsers(page: Int): LiveData<State<UsersBaseModel>> = liveData {
        emit(State.Loading)
        try {
            val response = retrofitClient.users.getUsers(page)
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
            } else {
                Log.e(TAG, "onResponse: " + "ErrorCode : " + response.code())
                emit(State.Error(response.message()))
            }
        } catch (t: Throwable) {
            emit(State.Error(t.localizedMessage!!))
        }
    }

    companion object {
        private var repoInstance: UsersRepo? = null
        fun getInstance(retrofitClient: RetrofitClient): UsersRepo? {
            if (repoInstance == null) {
                repoInstance = UsersRepo(retrofitClient)
            }
            return repoInstance
        }
    }
}