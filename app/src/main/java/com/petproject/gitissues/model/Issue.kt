package com.petproject.gitissues.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "issues_table")
data class Issue(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val number: Int? = null,
    val title: String? = null,
    @Embedded(prefix = "issue_user_")
    val user: User? = null,
    @Embedded(prefix = "issue_assignee_")
    val assignee: User? = null,
    val body: String? = null
)