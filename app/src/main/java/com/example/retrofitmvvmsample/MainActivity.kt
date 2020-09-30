package com.example.retrofitmvvmsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.retrofitmvvmsample.adapters.UserAdapter
import com.example.retrofitmvvmsample.databinding.ActivityMainBinding
import com.example.retrofitmvvmsample.modelClass.Datum
import com.example.retrofitmvvmsample.modelClass.State
import com.example.retrofitmvvmsample.modelClass.UsersBaseModel
import com.example.retrofitmvvmsample.utils.Utility.setVerticalRecyclerView
import com.example.retrofitmvvmsample.viewModel.UserViewModel
import com.example.retrofitmvvmsample.viewModel.ViewModelProviderFactory
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

        val viewModelProviderFactory = ViewModelProviderFactory(application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)
        initRecyclerView()
        fetchUsersApi()
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { refreshData() }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(this)
        setVerticalRecyclerView(binding.recyclerView, userAdapter, this, false)
    }

    private fun refreshData() {
        fetchUsersApi()
    }


    private fun fetchUsersApi() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getUsersResponse(2).observe(this@MainActivity) { apiResponse ->
                when (apiResponse) {
                    is State.Success -> {
                        binding.swipeRefresh.isRefreshing = false
                        processUsersResponse(apiResponse.data)
                    }
                    is State.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        apiResponse.errorMessage?.let { message ->
                            Toast.makeText(
                                this@MainActivity,
                                "An error occured: $message",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    is State.Loading -> {
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