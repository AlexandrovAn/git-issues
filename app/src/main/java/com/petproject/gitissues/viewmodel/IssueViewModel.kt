package com.petproject.gitissues.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.IssueRepo
import com.petproject.gitissues.repository.UpdateStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var issueRepo: IssueRepo
    private val selectIssuePosition: MutableLiveData<Int> = MutableLiveData<Int>()
    private val issueData: MutableLiveData<List<Issue>> = MutableLiveData<List<Issue>>()
    private val currentUpdateStatus: MutableLiveData<UpdateStatus> = MutableLiveData<UpdateStatus>()
    private val issuesObserver = Observer<List<Issue>> {
        it ?: return@Observer
        issueData.value = it
    }
    private val statusObserver = Observer<UpdateStatus> {
        it ?: return@Observer
        currentUpdateStatus.value = it
    }

    init {
        (application as BaseApp).component.inject(this)
        issueRepo.data.observeForever(issuesObserver)
        issueRepo.status.observeForever(statusObserver)
    }

    val issues: LiveData<List<Issue>> get() = issueData
    val status: LiveData<UpdateStatus> get() = currentUpdateStatus

    fun setSelectItem(selectItemPosition: Int) {
        selectIssuePosition.value = selectItemPosition
    }

    val selected: LiveData<Int> = selectIssuePosition

    fun updateIssuesList() {
        viewModelScope.launch { issueRepo.updateDataset() }
    }

    override fun onCleared() {
        issueRepo.data.removeObserver(issuesObserver)
        issueRepo.status.removeObserver(statusObserver)
        super.onCleared()
    }
}