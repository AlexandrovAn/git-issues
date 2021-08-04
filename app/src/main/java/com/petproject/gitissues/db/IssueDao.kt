package com.petproject.gitissues.db

import androidx.room.*
import com.petproject.gitissues.model.Issue

@Dao
interface IssueDao {
    @Query("SELECT * FROM ISSUES_TABLE")
    suspend fun getAll(): List<Issue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issues: List<Issue>)

    @Delete
    suspend fun delete(issue: Issue)

    @Query("DELETE FROM ISSUES_TABLE")
    suspend fun clear()
}