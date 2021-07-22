package com.petproject.gitissues.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueItemBinding
import com.petproject.gitissues.viewmodel.IssueViewModel

class IssueListFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels()
    private lateinit var issueItemBinding: IssueItemBinding
    private lateinit var issueRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        issueItemBinding = IssueItemBinding.inflate(inflater, container, false)
        issueRecyclerView = inflater.inflate(R.layout.issue_list_fragment,container,false).findViewById(R.id.issue_recycler)
        issueRecyclerView.layoutManager = LinearLayoutManager(inflater.inflate(R.layout.issue_list_fragment,container,false).context)
        return issueItemBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val issueListAdapter = IssueListAdapter()
        issueListAdapter.setHasStableIds(true)
        issueRecyclerView.adapter = issueListAdapter

        viewModel.issues.observe(viewLifecycleOwner, Observer { issueListAdapter.setDataset(it) })


    }
}