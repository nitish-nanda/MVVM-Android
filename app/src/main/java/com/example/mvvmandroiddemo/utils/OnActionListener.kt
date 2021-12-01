package com.example.mvvmandroiddemo.utils

interface OnActionListener<T> {

    fun notify(model: T, position: Int)
}