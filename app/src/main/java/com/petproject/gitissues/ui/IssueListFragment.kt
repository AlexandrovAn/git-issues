package com.petproject.gitissues.ui

import android.os.Bundle
import android.util.Log
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
import com.petproject.gitissues.databinding.IssueListFragmentBinding
import com.petproject.gitissues.viewmodel.IssueViewModel

class IssueListFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels()
    private lateinit var issueRecyclerView: RecyclerView
    private lateinit var binding: IssueListFragmentBinding


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
        issueListAdapter.setHasStableIds(true)
        with(binding.issueRecycler) {
            adapter = issueListAdapter
        }
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            Log.e("Fragmnet observer", "init")
            Log.e("Size of Dataset", "size = " + it.size)
            if (it.isEmpty()) {
                Log.e("LiveData", "dataset is empty")
            }
            issueListAdapter.setDataset(it)
        })


    }
}