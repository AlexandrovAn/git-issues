package com.petproject.gitissues.ui

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
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

    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.onBackPressed()
        }
    }
}
