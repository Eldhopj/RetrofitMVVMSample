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

public class UsersRepo {
    private static final String TAG = "UsersRepo";
    private static UsersRepo repoInstance;
    private final Application application;

    private UsersRepo(Application application) {
        this.application = application;
    }

    public static UsersRepo getInstance(Application application) {
        if (repoInstance == null) {
            repoInstance = new UsersRepo(application);
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
                mutableLiveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NotNull Call<UsersBaseModel> call, @NotNull Throwable t) {
                mutableLiveData.setValue(new ApiResponse(t.getLocalizedMessage()));
            }
        });
        return mutableLiveData;
    }
}
