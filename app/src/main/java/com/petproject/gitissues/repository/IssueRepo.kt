package com.petproject.gitissues.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.db.IssueDao
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.remote.IssueService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepo @Inject constructor(
    private val issueService: IssueService,
    private val dao: IssueDao,
    private val context: Context
) {
    private val issuesData: MutableLiveData<List<Issue>> = MutableLiveData<List<Issue>>()
    private val updateStatus: MutableLiveData<UpdateStatus> = MutableLiveData<UpdateStatus>()


    init {
        CoroutineScope(Dispatchers.IO).launch {

            issuesData.postValue(getDatasetFromDB())
        }
    }

    suspend fun updateDataset() {
        if (isOnline(context)) {
            try {
                issuesData.postValue(getDatasetFromNetwork())
                updateStatus.postValue(UpdateStatus.SUCCESSFUL)
            } catch (e: Exception) {
                updateStatus.postValue(UpdateStatus.UNKNOWN_ERROR)
            }
        } else {
            updateStatus.postValue(UpdateStatus.CONNECTION_LOST)
        }
    }

    private suspend fun getDatasetFromNetwork(): List<Issue> = withContext(Dispatchers.IO) {
        val dataset = issueService.getIssuesList()
        dao.insertAll(dataset)
        return@withContext dataset
    }

    private suspend fun getDatasetFromDB(): List<Issue> {
        if (dao.getAll().isEmpty()) {
            updateDataset()
        }
        return dao.getAll()
    }

    val data: LiveData<List<Issue>> get() = issuesData
    val status: LiveData<UpdateStatus> get() = updateStatus

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

}