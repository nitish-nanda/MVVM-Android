package com.example.mvvmandroiddemo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmandroiddemo.base.BaseViewModel
import com.example.mvvmandroiddemo.models.RepoModel
import com.example.mvvmandroiddemo.models.UserModel
import com.example.mvvmandroiddemo.repositary.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel constructor(private val repository: UserRepository) : BaseViewModel() {

    private val usersList = MutableLiveData<List<UserModel>>()
    private val userModel = MutableLiveData<UserModel>()
    private val userReposList = MutableLiveData<List<RepoModel>>()

    fun getUsers(): LiveData<List<UserModel>> {
        return usersList
    }

    fun getUser(): LiveData<UserModel> {
        return userModel
    }

    fun getUserRepos(): LiveData<List<RepoModel>> {
        return userReposList
    }

    fun getAllUsers() {

        isLoading.postValue(true)

        repository.getUsers().enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>?,
                response: Response<List<UserModel>>?
            ) {
                isLoading.postValue(false)

                if (response?.isSuccessful == true && response.body() != null)
                    usersList.postValue(response.body())
                else
                    error.postValue("Server error")
            }

            override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {
                handleError(t)
            }
        })
    }

    fun getUser(username: String) {

        isLoading.postValue(true)

        repository.getUser(username).enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>?, response: Response<UserModel>?) {
                isLoading.postValue(false)

                if (response?.isSuccessful == true && response.body() != null)
                    userModel.postValue(response.body())
                else
                    error.postValue("Server error")
            }

            override fun onFailure(call: Call<UserModel>?, t: Throwable?) {
                handleError(t)
            }
        })
    }

    fun getAllUserRepos(username: String) {

        isLoading.postValue(true)

        repository.getUserRepos(username).enqueue(object : Callback<List<RepoModel>> {
            override fun onResponse(
                call: Call<List<RepoModel>>?,
                response: Response<List<RepoModel>>?
            ) {
                isLoading.postValue(false)

                if (response?.isSuccessful == true && response.body() != null)
                    userReposList.postValue(response.body())
                else
                    error.postValue("Server error")
            }

            override fun onFailure(call: Call<List<RepoModel>>?, t: Throwable?) {
                handleError(t)
            }
        })
    }

    private fun handleError(t: Throwable?) {
        isLoading.postValue(false)
        t?.localizedMessage?.let {
            error.postValue(it)
            Log.d("TAG", it)
        }
    }
}