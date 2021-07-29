package com.petproject.gitissues.di.modules

import com.petproject.gitissues.remote.IssueService
import com.petproject.gitissues.remote.NetworkProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun issueService() = NetworkProvider.issueService

}