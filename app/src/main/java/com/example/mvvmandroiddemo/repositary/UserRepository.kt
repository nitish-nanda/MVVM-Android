package com.example.mvvmandroiddemo.repositary

import com.example.mvvmandroiddemo.networks.ApiClient
import com.example.mvvmandroiddemo.networks.WebServices

class UserRepository {

    private val webServices: WebServices = ApiClient.interfaceApi()

    fun getUsers() = webServices.getUsers()

    fun getUser(username: String) = webServices.getUser(username = username)

    fun getUserRepos(username: String) = webServices.getUserRepos(username = username)
}