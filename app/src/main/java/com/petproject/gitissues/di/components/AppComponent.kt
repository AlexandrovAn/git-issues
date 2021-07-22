package com.petproject.gitissues.di.components

import com.petproject.gitissues.di.modules.AppModule
import com.petproject.gitissues.di.modules.NetworkModule
import com.petproject.gitissues.repository.IssueRepo
import com.petproject.gitissues.ui.MainActivity
import com.petproject.gitissues.viewmodel.IssueViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

//    fun inject(mainActivity: MainActivity)

    fun inject(issueViewModel: IssueViewModel)

//    fun inject(issueRepo: IssueRepo)

}