package com.petproject.gitissues.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.petproject.gitissues.BaseApp
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueDetailFragmentBinding
import com.petproject.gitissues.model.Issue
import com.petproject.gitissues.repository.State
import com.petproject.gitissues.viewmodel.IssueViewModel

class DetailFragment : Fragment() {

    private val viewModel: IssueViewModel by activityViewModels() {
        (requireActivity().application as BaseApp).component.getIssueViewModelFactory()
    }
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

    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.issuesState.observe(viewLifecycleOwner, {
            when (it) {
                is State.UpdateState -> currentIssuesList = it.issueList
            }
        })

        binding.detailFragmentToolbar?.apply {

            setNavigationIcon(R.drawable.back_icon)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }

        viewModel.selected.observe(viewLifecycleOwner, {
            if (it != -1) {
                with(currentIssuesList[it]) {
                    binding.toolbarTitleTv?.text =
                        getString(R.string.number_toolbar, number)
                    binding.issueTitleDetailTv.text = title
                    binding.issueBodyDetailTv.text = body
                    user?.avatarUrl?.let { authorAvatar ->
                        binding.authorTv.text = getString(R.string.author_tv)
                        binding.authorLoginTv.text = user.login
                        loadImage(
                            authorAvatar,
                            binding.authorAvatarIv
                        )
                    }
                    assignee?.avatarUrl?.let { assigneeAvatar ->
                        binding.assigneeTv.text = getString(R.string.assignee_tv)
                        binding.assigneeLoginTv.text = assignee.login
                        loadImage(
                            assigneeAvatar,
                            binding.assigneeAvatarIv
                        )
                    }
                }
            }
        })

    }

    private fun loadImage(url: String, view: ImageView) {
        Glide.with(binding.root)
            .load(url).apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.placeholder)
            .into(view)
    }
}