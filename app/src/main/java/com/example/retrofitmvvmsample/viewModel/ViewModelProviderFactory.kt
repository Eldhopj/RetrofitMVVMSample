package com.example.retrofitmvvmsample.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvmsample.repo.UsersRepo
import com.example.retrofitmvvmsample.utils.RetrofitClient

class ViewModelProviderFactory(
    private val app: Application,
) : ViewModelProvider.Factory {

    private val usersRepo: UsersRepo by lazy { UsersRepo.getInstance(RetrofitClient.getInstance(app))!! }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(app, usersRepo) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}