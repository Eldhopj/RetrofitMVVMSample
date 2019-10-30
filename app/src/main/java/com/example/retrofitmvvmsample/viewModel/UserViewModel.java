package com.example.retrofitmvvmsample.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.retrofitmvvmsample.modelClass.Datum;
import com.example.retrofitmvvmsample.repo.UserLocalRepo;
import com.example.retrofitmvvmsample.repo.UsersNetworkRepo;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UsersNetworkRepo usersNetworkRepo;
    private final UserLocalRepo userLocalRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        usersNetworkRepo = UsersNetworkRepo.getInstance(application);
        userLocalRepo = UserLocalRepo.getInstance(application);
    }

    public LiveData<List<Datum>> getUsersResponse(int page) {
        usersNetworkRepo.getMatches(page);
        return userLocalRepo.getUsers();
    }
}