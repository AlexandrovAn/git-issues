package com.petproject.gitissues.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.IssueRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var issueRepo: IssueRepo
    private val selectIssuePosition: MutableLiveData<Int> = MutableLiveData<Int>()
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

    fun setSelectItem(selectItemPosition: Int){
        selectIssuePosition.value = selectItemPosition
    }

    val selected: LiveData<Int> = selectIssuePosition

    fun updateIssuesList(){
        viewModelScope.launch { issueRepo.updateDataset() }
    }

    override fun onCleared() {
        issueRepo.data.removeObserver(issuesObserver)
        super.onCleared()
    }
}