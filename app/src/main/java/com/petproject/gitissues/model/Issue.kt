package com.petproject.gitissues.model

import com.google.gson.annotations.SerializedName

class Issue {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("number")
    val number: Int? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("user")
    val user: User? = null

    @SerializedName("assignee")
    val assignee: User? = null

    @SerializedName("body")
    val body: String? = null
}