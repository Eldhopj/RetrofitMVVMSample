package com.example.retrofitmvvmsample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvmsample.repo.UsersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(application: Application, private val usersRepo: UsersRepo) :
    AndroidViewModel(application) {


    suspend fun getUsersResponse(page: Int) =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            usersRepo.getUsers(page)
        }

}