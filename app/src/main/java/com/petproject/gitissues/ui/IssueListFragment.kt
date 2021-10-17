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
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueListFragmentBinding
import com.petproject.gitissues.repository.State
import com.petproject.gitissues.repository.UpdateStatus
import com.petproject.gitissues.viewmodel.IssueViewModel

class IssueListFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels() {
        (requireActivity().application as BaseApp).component.getIssueViewModelFactory()
    }
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
        issueListAdapter.setHasStableIds(true)
        binding.issueRecycler.adapter = issueListAdapter
        binding.swipeRefLayout?.setOnRefreshListener {
            viewModel.updateIssuesList()
        }
        viewModel.issuesState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.UpdateState -> {
                    val currentList = it.issueList
                    issueListAdapter.setDataset(currentList)
                    when (it.status) {
                        UpdateStatus.NETWORK_UPDATE -> {
                            showInfoToast(R.string.toast_successful_update)
                            binding.swipeRefLayout!!.isRefreshing = false
                            viewModel.setIssuesState(State.UpdateState(currentList))
                        }
                        UpdateStatus.DB_UPDATE -> {
                            binding.swipeRefLayout!!.isRefreshing = true
                            viewModel.setIssuesState(State.UpdateState(currentList))
                        }
                    }
                }
                is State.LostInternetConnection -> {
                    showInfoToast(R.string.toast_connection_lost_update)
                    binding.swipeRefLayout!!.isRefreshing = false
                    viewModel.setIssuesState(State.DefaultState)
                }
                is State.ErrorOfUpdate -> {
                    showInfoToast(R.string.toast_unknown_error_update)
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

    private fun showInfoToast(stringResId: Int) {
        Toast.makeText(
            activity,
            getString(stringResId),
            Toast.LENGTH_SHORT
        ).show()
    }
}