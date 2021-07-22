package com.petproject.gitissues.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.petproject.gitissues.R
import com.petproject.gitissues.viewmodel.IssueViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: IssueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
    }
}
