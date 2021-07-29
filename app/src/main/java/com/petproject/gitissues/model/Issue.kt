package com.petproject.gitissues.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "issues_table")
data class Issue(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("issue_id")
    val id: Int? = null,

    @ColumnInfo(name = "issue_number")
    @SerializedName("number")
    val number: Int? = null,

    @ColumnInfo(name = "issue_title")
    @SerializedName("title")
    val title: String? = null,

    @Embedded(prefix = "issue_user_")
    @SerializedName("user")
    val user: User? = null,

    @Embedded(prefix = "issue_assignee_")
    @SerializedName("assignee")
    val assignee: User? = null,

    @ColumnInfo(name = "issue_body")
    @SerializedName("body")
    val body: String? = null
)