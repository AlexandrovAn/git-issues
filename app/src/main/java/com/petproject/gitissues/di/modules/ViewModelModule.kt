package com.petproject.gitissues.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petproject.gitissues.di.ViewModelKey
import com.petproject.gitissues.viewmodel.IssueViewModel
import com.petproject.gitissues.viewmodel.IssueViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(IssueViewModel::class)
    abstract fun bindsIssueViewModel(viewModel: IssueViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: IssueViewModelFactory): ViewModelProvider.Factory
}