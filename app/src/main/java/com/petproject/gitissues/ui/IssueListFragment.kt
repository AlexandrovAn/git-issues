package com.petproject.gitissues.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueListFragmentBinding
import com.petproject.gitissues.repository.UpdateStatus
import com.petproject.gitissues.viewmodel.IssueViewModel

class IssueListFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels()
    private lateinit var binding: IssueListFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IssueListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val issueListAdapter = IssueListAdapter()
        with(binding.issueRecycler) {
            adapter = issueListAdapter
        }
        binding.swipeRefLayout?.setOnRefreshListener {
            viewModel.updateIssuesList()
        }
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            issueListAdapter.setDataset(it)
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                UpdateStatus.SUCCESSFUL -> Toast.makeText(
                    activity,
                    getString(R.string.toast_successful_update),
                    Toast.LENGTH_SHORT
                ).show()
                UpdateStatus.CONNECTION_LOST -> Toast.makeText(
                    activity,
                    getString(R.string.toast_connection_lost_update),
                    Toast.LENGTH_SHORT
                ).show()
                UpdateStatus.UNKNOWN_ERROR -> Toast.makeText(
                    activity,
                    getString(R.string.toast_unknown_error_update),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.swipeRefLayout!!.isRefreshing = false
        })
        issueListAdapter.clickListener = {
            viewModel.setSelectItem(it)
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Navigation.findNavController(binding.root).navigate(R.id.detail_fragment)
            }
        }
    }
}