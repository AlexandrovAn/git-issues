package com.petproject.gitissues.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
    }
    val issueService: IssueService by lazy {
        retrofitBuilder.build().create(IssueService::class.java)
    }
}