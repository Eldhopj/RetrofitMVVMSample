package com.example.retrofitmvvmsample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.retrofitmvvmsample.modelClass.UsersBaseModel;
import com.example.retrofitmvvmsample.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        fetchUsersApi();
    }

    private void fetchUsersApi() {
        viewModel.getUsersResponse(2).observe(this, apiResponse -> {
//            progressBar.setVisibility(View.GONE);
            if (apiResponse.getResponse() != null) {
                processUsersResponse((UsersBaseModel) apiResponse.getResponse());
            } else if (apiResponse.getError() != null) {
                Toast.makeText(this, apiResponse.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processUsersResponse(@NonNull UsersBaseModel response) {

    }
}
