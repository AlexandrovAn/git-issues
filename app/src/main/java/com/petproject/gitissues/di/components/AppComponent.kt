package com.petproject.gitissues.di.components

import com.petproject.gitissues.di.modules.*
import com.petproject.gitissues.repository.IssueRepo
import com.petproject.gitissues.ui.MainActivity
import com.petproject.gitissues.viewmodel.IssueViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [AppModule::class,
        NetworkModule::class,
        DaoModule::class,
        DispatcherModule::class,
        ViewModelModule::class]
)
interface AppComponent {
    fun inject(issueViewModel: IssueViewModel)
}