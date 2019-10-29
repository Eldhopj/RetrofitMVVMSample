package com.example.retrofitmvvmsample.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.retrofitmvvmsample.modelClass.Datum;
import com.example.retrofitmvvmsample.repo.UserLocalRepo;
import com.example.retrofitmvvmsample.repo.UsersRepo;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UsersRepo usersRepo;
    private final UserLocalRepo userLocalRepo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        usersRepo = UsersRepo.getInstance(application);
        userLocalRepo = UserLocalRepo.getInstance(application);
    }

    public LiveData<List<Datum>> getUsersResponse(int page) {
        usersRepo.getMatches(page);
        return userLocalRepo.getUsers(page);
    }
}