package com.petproject.gitissues.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.petproject.gitissues.repository.IssueRepo
import javax.inject.Inject

class IssueViewModel(application: Application):AndroidViewModel(application) {
    @Inject
    lateinit var issueRepo: IssueRepo


}