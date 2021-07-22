package com.petproject.gitissues.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("login")
    val login: String? = null

    @SerializedName("avatar_url")
    val avatarUrl: String? = null
}