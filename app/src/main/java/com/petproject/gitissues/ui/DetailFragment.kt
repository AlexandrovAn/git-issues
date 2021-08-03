package com.petproject.gitissues.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueDetailFragmentBinding
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.State
import com.petproject.gitissues.viewmodel.IssueViewModel

class DetailFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels()
    private lateinit var binding: IssueDetailFragmentBinding
    private lateinit var currentIssuesList: List<Issue>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IssueDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.issuesState.observe(viewLifecycleOwner, {
            when (it) {
                is State.DefaultStateWithDataset -> currentIssuesList = it.defaultIssueList
                is State.SuccessfulUpdate -> currentIssuesList = it.updatedIssueList
                is State.UpdateFromDB -> currentIssuesList = it.dbIssueList
            }

        })
        viewModel.selected.observe(viewLifecycleOwner, {
            binding.issue = currentIssuesList[it]
        })
        binding.detailFragmentToolbar?.apply {
            setNavigationIcon(R.drawable.back_icon)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }
}