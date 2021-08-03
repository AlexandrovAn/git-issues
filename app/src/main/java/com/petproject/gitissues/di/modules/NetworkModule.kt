package com.petproject.gitissues.di.modules

import com.petproject.gitissues.remote.IssueService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
    }
    private val issueService: IssueService by lazy {
        retrofitBuilder.build().create(IssueService::class.java)
    }

    @Provides
    fun issueService() = issueService

}