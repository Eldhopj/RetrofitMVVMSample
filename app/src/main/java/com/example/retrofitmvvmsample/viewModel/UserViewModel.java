package com.example.retrofitmvvmsample.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.retrofitmvvmsample.modelClass.ApiResponse;
import com.example.retrofitmvvmsample.repo.UsersRepo;

public class UserViewModel extends AndroidViewModel {
    private final UsersRepo usersRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        usersRepo = UsersRepo.getInstance(application);
    }

    public LiveData<ApiResponse> getUsersResponse(int page) {
        return usersRepo.getMatches(page);
    }
}