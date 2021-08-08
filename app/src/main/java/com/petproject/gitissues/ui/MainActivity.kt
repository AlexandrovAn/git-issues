package com.petproject.gitissues.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.databinding.ActivityMainBinding
import com.petproject.gitissues.viewmodel.IssueViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: IssueViewModel by viewModels() {
        (this.application as BaseApp).component.getIssueViewModelFactory()
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            super.onBackPressed()
        }
        viewModel.setSelectItem(-1)
    }
}
