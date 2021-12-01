package com.example.mvvmandroiddemo.networks

import com.example.mvvmandroiddemo.models.RepoModel
import com.example.mvvmandroiddemo.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServices {

    companion object {

        const val BASE_URL = "https://api.github.com"
    }

    @GET("/users")
    fun getUsers(): Call<List<UserModel>>

    @GET("/users/{user_name}")
    fun getUser(@Path("user_name") username: String): Call<UserModel>

    @GET("/users/{user_name}/repos")
    fun getUserRepos(@Path("user_name") username: String): Call<List<RepoModel>>
}