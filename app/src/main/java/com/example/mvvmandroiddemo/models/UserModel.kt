package com.example.mvvmandroiddemo.models

import java.io.Serializable

data class UserModel(
    var id: Int? = null,
    var avatar_url: String? = null,
    var login: String? = null,
    var type: String? = null,
    var name: String? = null,
    var bio: String? = null
) : Serializable
