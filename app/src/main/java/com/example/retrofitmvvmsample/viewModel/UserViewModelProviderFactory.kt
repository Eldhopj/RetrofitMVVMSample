package com.example.retrofitmvvmsample.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvmsample.repo.UsersRepo

class UserViewModelProviderFactory(
    private val app: Application,
    private val usersRepo: UsersRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(app, usersRepo) as T
    }
}