package com.example.retrofitmvvmsample.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvmsample.utils.RetrofitClient

class UserViewModelProviderFactory(
    private val app: Application,
    private val retrofitClient: RetrofitClient
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(app, retrofitClient) as T
    }
}