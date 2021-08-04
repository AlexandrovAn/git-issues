package com.petproject.gitissues.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.NonNull
import com.petproject.gitissues.db.IssueDao
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.remote.IssueService
import kotlinx.coroutines.Dispatchers
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
    suspend fun getIssuesDataset(needToUpdate: Boolean = false): State =
        if (needToUpdate) updateDataset() else getDatasetFromDB()

    private suspend fun updateDataset(): State {
        return if (isOnline(context)) {
            try {
                State.UpdateState(getDatasetFromNetwork(), UpdateStatus.NETWORK_UPDATE)
            } catch (e: Exception) {
                State.ErrorOfUpdate
            }
        } else {
            State.LostInternetConnection
        }
    }

    private suspend fun getDatasetFromNetwork(): List<Issue> = withContext(Dispatchers.IO) {
        val dataset = issueService.getIssuesList()
        dao.clear()
        dao.insertAll(dataset)
        return@withContext dataset
    }

    private suspend fun getDatasetFromDB(): State {
        if (dao.getAll().isEmpty()) {
            updateDataset()
        }
        return State.UpdateState(dao.getAll(), UpdateStatus.DB_UPDATE)
    }

    @NonNull
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