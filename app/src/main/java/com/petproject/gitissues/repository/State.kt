package com.petproject.gitissues.repository

import com.petproject.gitissues.model.Issue

sealed class State {
    class UpdateState(
        val issueList: List<Issue>,
        val status: UpdateStatus = UpdateStatus.DEFAULT_STATE
    ) : State()

    object DefaultState : State()
    object LostInternetConnection : State()
    object ErrorOfUpdate : State()
}

enum class UpdateStatus {
    NETWORK_UPDATE, DB_UPDATE, DEFAULT_STATE
}