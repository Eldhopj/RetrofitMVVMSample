package com.example.retrofitmvvmsample.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitmvvmsample.modelClass.ApiResponse;
import com.example.retrofitmvvmsample.modelClass.UsersBaseModel;
import com.example.retrofitmvvmsample.utils.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class UsersNetworkRepo {
    private static final String TAG = "UsersNetworkRepo";
    private static UsersNetworkRepo repoInstance;
    private final Application application;
    private final UserLocalRepo userLocalRepo;

    private UsersNetworkRepo(Application application) {
        this.application = application;
        userLocalRepo = UserLocalRepo.getInstance(application);
    }

    public static UsersNetworkRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new UsersNetworkRepo(application);
        }
        return repoInstance;
    }

    public MutableLiveData<ApiResponse> getMatches(int page) {
        final MutableLiveData<ApiResponse> mutableLiveData = new MutableLiveData<>();
        Call<UsersBaseModel> call;
        call = RetrofitClient.getInstance(application).getUsers().
                getUsers(page);
        call.enqueue(new Callback<UsersBaseModel>() {
            @Override
            public void onResponse(@NotNull Call<UsersBaseModel> call, @NotNull retrofit2.Response<UsersBaseModel> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + "ErrorCode : " + response.code());
                    mutableLiveData.setValue(new ApiResponse(response.message()));
                    return;
                }
                userLocalRepo.insert(response.body().getData());
            }

            @Override
            public void onFailure(@NotNull Call<UsersBaseModel> call, @NotNull Throwable t) {
                mutableLiveData.setValue(new ApiResponse(t.getLocalizedMessage()));
            }
        });
        return mutableLiveData;
    }
}