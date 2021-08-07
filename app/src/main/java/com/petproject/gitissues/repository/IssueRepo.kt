package com.petproject.gitissues.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.NonNull
import com.petproject.gitissues.db.IssueDao
import com.petproject.gitissues.di.modules.IoDispatcher
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.remote.IssueService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepo @Inject constructor(
    private val issueService: IssueService,
    private val dao: IssueDao,
    private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun dataInit(onlyUpdate: Boolean = false): Flow<State> = flow {
        if (!onlyUpdate) {
            emit(getDatasetFromDB())
        }
        emit(updateDataset())
    }.flowOn(ioDispatcher)

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

    private suspend fun getDatasetFromNetwork(): List<Issue> {
        val dataset = issueService.getIssuesList()
        dao.clear()
        dao.insertAll(dataset)
        return dataset
    }

    private suspend fun getDatasetFromDB(): State {
        return State.UpdateState(dao.getAll(), UpdateStatus.DB_UPDATE)
    }

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