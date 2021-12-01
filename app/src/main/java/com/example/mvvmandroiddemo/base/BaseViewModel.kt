package com.example.mvvmandroiddemo.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val error = MutableLiveData<String>()

    val isLoading = MutableLiveData<Boolean>()

    fun getError(): LiveData<String> {
        return error
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}