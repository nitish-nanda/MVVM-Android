package com.example.mvvmandroiddemo.models

import java.io.Serializable

data class RepoModel(
    var id: Int? = null,
    var name: String? = null,
    var full_name: String? = null,
    var owner: UserModel? = null,
    var language: String? = null
) : Serializable
