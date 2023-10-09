package com.eb.retrofitexample

import com.google.gson.annotations.SerializedName


data class User(
    var login: String? = null,

    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)