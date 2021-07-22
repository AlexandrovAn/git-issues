package com.petproject.gitissues.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.petproject.gitissues.di.BaseApp
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.IssueRepo
import javax.inject.Inject

class IssueViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var issueRepo: IssueRepo
    private val issueData: MutableLiveData<List<Issue>> = MutableLiveData<List<Issue>>()
    private val issuesObserver = Observer<List<Issue>> {
        it ?: return@Observer
        issueData.value = it
    }

    init {
        (application as BaseApp).component.inject(this)
        issueRepo.data.observeForever(issuesObserver)
    }

    val issues: LiveData<List<Issue>> get() = issueData

    override fun onCleared() {
        issueRepo.data.removeObserver(issuesObserver)
        super.onCleared()
    }
}