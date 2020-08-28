package com.example.retrofitmvvmsample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object Utility {

    fun loadImageUsingGlide(context: Context?, view: ImageView?, url: String?) {
        if (url != null && !url.isEmpty() && view != null && context != null) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    fun setVerticalRecyclerView(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>?, context: Context?, nestedScroll: Boolean) {
        if (recyclerView != null && context != null) {
            recyclerView.setHasFixedSize(false)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.isNestedScrollingEnabled = nestedScroll
            recyclerView.adapter = adapter
        }
    }

    fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}