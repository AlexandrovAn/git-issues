package com.petproject.gitissues.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.repository.IssueRepo
import com.petproject.gitissues.repository.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel (application: Application) : ViewModel() {
    @Inject
    lateinit var issueRepo: IssueRepo
    private val selectIssuePosition: MutableLiveData<Int> = MutableLiveData<Int>()
    private val issueData: MutableLiveData<State> = MutableLiveData<State>()


    init {
        (application as BaseApp).component.inject(this)
        issueRepo.dataInit().onEach { state ->
            issueData.postValue(state)
        }.launchIn(viewModelScope)
    }

    val issuesState: LiveData<State> get() = issueData

    fun setSelectItem(selectItemPosition: Int) {
        selectIssuePosition.value = selectItemPosition
    }

    fun setIssuesState(state: State) {
        issueData.value = state
    }

    val selected: LiveData<Int> = selectIssuePosition

    fun updateIssuesList() {
        issueRepo.dataInit(true).onEach { state ->
            issueData.postValue(state)
        }.launchIn(viewModelScope)
    }
}