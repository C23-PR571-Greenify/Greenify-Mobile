package com.rememberdev.greenify.data.remote

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {

        private const val BASE_URL = "https://greenify-backend-ernafpm2wq-et.a.run.app/"

        fun getApiService(): ApiService {
            val httpClient = createHttpClient()
            val retorofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
            return retorofit.create(ApiService::class.java)
        }

        private fun createHttpClient(): OkHttpClient {
            val httpClientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            httpClientBuilder.addInterceptor(loggingInterceptor)
            return httpClientBuilder.build()
        }

    }
}