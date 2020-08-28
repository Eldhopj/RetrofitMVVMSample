package com.example.retrofitmvvmsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.retrofitmvvmsample.adapters.UserAdapter
import com.example.retrofitmvvmsample.databinding.ActivityMainBinding
import com.example.retrofitmvvmsample.modelClass.ApiResponse
import com.example.retrofitmvvmsample.modelClass.Datum
import com.example.retrofitmvvmsample.modelClass.UsersBaseModel
import com.example.retrofitmvvmsample.repo.UsersRepo
import com.example.retrofitmvvmsample.utils.Utility.setVerticalRecyclerView
import com.example.retrofitmvvmsample.viewModel.UserViewModel
import com.example.retrofitmvvmsample.viewModel.UserViewModelProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val usersRepo = UsersRepo.getInstance(this)
        val viewModelProviderFactory = UserViewModelProviderFactory(application, usersRepo!!)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)
        initRecyclerView()
        fetchData()
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { refreshData() }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(applicationContext)
        setVerticalRecyclerView(binding.recyclerView, userAdapter, applicationContext, false)
    }

    private fun refreshData() {
        fetchData()
    }

    private fun fetchData() {
        fetchUsersApi()
    }

    private fun fetchUsersApi() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getUsersResponse(2).observe(this@MainActivity) { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> {
                        binding.swipeRefresh.isRefreshing = false
                        apiResponse.data?.let { processUsersResponse(it) }
                    }
                    is ApiResponse.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        apiResponse.message?.let { message ->
                            Toast.makeText(this@MainActivity, "An error occured: $message", Toast.LENGTH_LONG).show()
                        }
                    }
                    is ApiResponse.Loading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                }
            }
        }
    }

    private fun processUsersResponse(response: UsersBaseModel) {
        val userDatum: List<Datum> = ArrayList(response.data!!)
        userAdapter.submitList(userDatum)
    }
}