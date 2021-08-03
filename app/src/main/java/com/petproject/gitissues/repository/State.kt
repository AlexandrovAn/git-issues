package com.petproject.gitissues.repository

import com.petproject.gitissues.model.Issue

sealed class State {
    class DefaultStateWithDataset(val defaultIssueList: List<Issue>) : State()
    class SuccessfulUpdate(val updatedIssueList: List<Issue>) : State()
    class UpdateFromDB(val dbIssueList: List<Issue>) : State()
    object DefaultState : State()
    object LostInternetConnection : State()
    object ErrorOfUpdate : State()
}