package com.petproject.gitissues.ui

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.ActivityMainBinding
import com.petproject.gitissues.viewmodel.IssueViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: IssueViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
    }

    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.onBackPressed()
        }
        viewModel.setSelectItem(-1)
    }
}
