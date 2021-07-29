package com.petproject.gitissues.di.modules

import android.app.Application
import com.petproject.gitissues.db.IssueDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule(private val context: Application) {

    @Provides
    @Singleton
    fun dao() = IssueDatabase.buildDB(context).issueDao()

}