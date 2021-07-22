package com.petproject.gitissues.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.remote.IssueService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepo @Inject constructor(private val issueService: IssueService) {

    private val issuesData: MutableLiveData<List<Issue>> = MutableLiveData<List<Issue>>()

    init{
        CoroutineScope(Dispatchers.IO).launch { issuesData.postValue(getDatasetFromNetwork()) }
    }

    private suspend fun getDatasetFromNetwork() : List<Issue> = withContext(Dispatchers.IO){
        val dataset = issueService.getIssuesList()
        return@withContext dataset
    }

    val data: LiveData<List<Issue>> get() = issuesData

}