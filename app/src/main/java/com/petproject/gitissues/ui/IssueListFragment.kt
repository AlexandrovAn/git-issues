package com.petproject.gitissues.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
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
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.State
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
        viewModel.issuesState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.DefaultStateWithDataset -> issueListAdapter.setDataset(it.defaultIssueList)
                is State.UpdateFromDB -> {
                    val currentDBList: List<Issue> = it.dbIssueList
                    issueListAdapter.setDataset(currentDBList)
                    viewModel.setIssuesState(State.DefaultStateWithDataset(currentDBList))
                }
                is State.SuccessfulUpdate -> {
                    val currentUpdateList: List<Issue> = it.updatedIssueList
                    issueListAdapter.setDataset(currentUpdateList)
                    Toast.makeText(
                        activity,
                        getString(R.string.toast_successful_update),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.swipeRefLayout!!.isRefreshing = false
                    viewModel.setIssuesState(State.DefaultStateWithDataset(currentUpdateList))
                }
                is State.LostInternetConnection -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.toast_connection_lost_update),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.swipeRefLayout!!.isRefreshing = false
                    viewModel.setIssuesState(State.DefaultState)
                }
                is State.ErrorOfUpdate -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.toast_unknown_error_update),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.swipeRefLayout!!.isRefreshing = false
                    viewModel.setIssuesState(State.DefaultState)
                }
            }

        })
        issueListAdapter.clickListener = {
            viewModel.setSelectItem(it)
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Navigation.findNavController(binding.root).navigate(R.id.detail_fragment)
            }
        }
    }
}