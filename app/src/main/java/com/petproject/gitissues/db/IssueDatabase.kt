package com.petproject.gitissues.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.petproject.gitissues.model.Issue

@Database(
    entities = [Issue::class],
    version = 1
)
abstract class IssueDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        private const val NAME = "issue.db"

        fun buildDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            IssueDatabase::class.java,
            NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    }

}