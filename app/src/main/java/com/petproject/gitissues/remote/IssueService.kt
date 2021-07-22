package com.petproject.gitissues.remote

import com.petproject.gitissues.model.Issue
import retrofit2.http.GET

interface IssueService {
    @GET("/repos/kachmazoff/study-helper-back/issues?state=all")
    fun getIssuesList(): List<Issue>
}