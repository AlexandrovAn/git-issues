package com.petproject.gitissues.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int? = null,
    val login: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null
)