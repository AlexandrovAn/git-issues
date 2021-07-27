package com.petproject.gitissues.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueListFragmentBinding
import com.petproject.gitissues.viewmodel.IssueViewModel

class IssueListFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels()
    private lateinit var binding:IssueListFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IssueListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val issueListAdapter = IssueListAdapter()
        with(binding.issueRecycler) {
            adapter = issueListAdapter
        }
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            issueListAdapter.setDataset(it)
        })
        issueListAdapter.clickListener = {
            viewModel.setSelectItem(it)
            Navigation.findNavController(binding.root).navigate(R.id.detail_fragment)
        }


    }
}