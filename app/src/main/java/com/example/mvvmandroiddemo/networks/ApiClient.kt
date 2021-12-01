package com.example.mvvmandroiddemo.networks

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    fun interfaceApi(): WebServices {

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("ApiClient", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(WebServices.BASE_URL)
            .build()

        return retrofit.create(WebServices::class.java)
    }
}