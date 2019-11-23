package com.example.retrofitmvvmsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.retrofitmvvmsample.Adapters.UserAdapter;
import com.example.retrofitmvvmsample.databinding.ActivityMainBinding;
import com.example.retrofitmvvmsample.utils.Utility;
import com.example.retrofitmvvmsample.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    ActivityMainBinding binding;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initRecyclerView();
        fetchData();
        swipeToRefresh();

    }

    private void swipeToRefresh() {
        viewModel.deleteData();
        binding.swipeRefresh.setOnRefreshListener(this::refreshData);
    }


    private void initRecyclerView() {
        userAdapter = new UserAdapter(getApplicationContext());
        Utility.setVerticalRecyclerView(binding.recyclerView, userAdapter, getApplicationContext(), false);
    }

    private void refreshData() {
        userAdapter.clearData();
        fetchData();
    }

    private void fetchData() {
        binding.swipeRefresh.setRefreshing(true);
        fetchUsersApi();
    }

    private void fetchUsersApi() {
        viewModel.getUsersResponse(1).observe(this, data -> {
            binding.swipeRefresh.setRefreshing(false);
            userAdapter.clearData();
            userAdapter.addItemRange(data);
        });
    }

//    private void processUsersResponse(@NonNull UsersBaseModel response) {
//        List<Datum> userDatum = new ArrayList<>(response.getData());
//        userAdapter.addItemRange(userDatum);
//    }
}
