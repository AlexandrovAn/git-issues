package com.petproject.gitissues.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.gitissues.databinding.IssueItemBinding
import com.petproject.gitissues.model.Issue

class IssueListAdapter : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    private var issues = mutableListOf<Issue>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: IssueItemBinding = IssueItemBinding.inflate(inflater, parent, false)
        return IssueViewHolder(binding)
    }

    fun setDataset(list: List<Issue>){
        issues.addAll(list)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.binding.issue = issues[position]
    }

    class IssueViewHolder(val binding: IssueItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}