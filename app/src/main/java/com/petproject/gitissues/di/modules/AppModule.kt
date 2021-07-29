package com.petproject.gitissues.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Application) {
    @Provides
    fun provideContext(): Context = context
}