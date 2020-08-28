package com.example.retrofitmvvmsample.utils

import android.content.Context
import com.example.retrofitmvvmsample.BuildConfig
import com.example.retrofitmvvmsample.apiInterface.Users
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://reqres.in/"
private const val HEADER_CACHE_CONTROL = "Cache-Control" // removes Cache-Control header from the server, and apply our own cache control
private const val HEADER_PRAGMA = "Pragma" // overrides "Not to use caching scenario"
private const val cacheSize = 5 * 1024 * 1024.toLong() // 5 MB

class RetrofitClient private constructor(context: Context) {

    private lateinit var retrofit: Retrofit

    val users: Users by lazy { retrofit.create(Users::class.java) }

    companion object {

        private var mInstance: RetrofitClient? = null

        fun getInstance(context: Context): RetrofitClient {
            if (mInstance == null) {
                mInstance = RetrofitClient(context)
            }
            return mInstance as RetrofitClient
        }
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        with(httpClient) {
            cache(cache(context))
            addInterceptor(offlineInterceptor(context))
            addNetworkInterceptor(networkInterceptor()) // only used when network is on
            //addInterceptor(headers())
            build()
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            return build()
        }
    }

    private fun headers(): Interceptor {
        /**If there are any headers its adds in here */
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                //Ignoring endpoints which need not needed tokens
                if (original.url.encodedPath.equals("/oauth/token", ignoreCase = true)
                    || original.url.encodedPath.equals("/api/v1/login", ignoreCase = true) && original.method.equals("post", ignoreCase = true)) {
                    return chain.proceed(original)
                }
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "header") // Headers
                    .header("X_HEADER", "android")
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }

    /**
     * This interceptor will be called ONLY if the network is available
     */
    private fun networkInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS) // Cache the response only for 5 sec, so if a request comes within 30sec it will show from cache
                    .build()
                return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL) // removes cache control from the server
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString()) // applying our cache control
                    .build()
            }
        }
    }

    private fun cache(context: Context): Cache {
        return Cache(File(context.cacheDir, "RetrofitMVVM"), cacheSize)
    }

    /**
     * This interceptor will be called both if the network is available and the network is not available
     */
    private fun offlineInterceptor(context: Context): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                if (!Utility.hasNetwork(context)) { // makes the network is not available only
                    val cacheControl = CacheControl.Builder()
                        .maxStale(5, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                return chain.proceed(request)
            }
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(context))
            .build()
    }
}
