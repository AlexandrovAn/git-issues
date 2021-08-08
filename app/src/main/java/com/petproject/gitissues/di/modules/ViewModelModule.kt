package com.petproject.gitissues.di.modules

import com.petproject.gitissues.repository.IssueRepo
import com.petproject.gitissues.viewmodel.IssueViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun issueViewModelFactory(issueRepo: IssueRepo) = IssueViewModelFactory(issueRepo)
}