package com.petproject.gitissues.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.petproject.gitissues.databinding.IssueItemBinding
import com.petproject.gitissues.model.Issue

class IssueListAdapter : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    private var issues = mutableListOf<Issue>()
    var clickListener: ((selectItemNum: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: IssueItemBinding = IssueItemBinding.inflate(inflater, parent, false)
        val holder = IssueViewHolder(binding)
        holder.binding.root.setOnClickListener {
            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                clickListener?.invoke(holder.bindingAdapterPosition)
            }
        }
        return holder
    }

    fun setDataset(list: List<Issue>) {
        val oldList = issues
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            IssueItemDiffCallback(
                oldList,
                list
            )
        )
        issues = list as MutableList<Issue>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        Log.e("Title: " + issues[position].title,  "  ID = " + issues[position].id)
        holder.binding.issue = issues[position]
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun getItemId(position: Int): Long {
        return issues[position].id.toLong()
    }

    class IssueViewHolder(val binding: IssueItemBinding) : RecyclerView.ViewHolder(binding.root)

    class IssueItemDiffCallback(
        var oldIssueList: List<Issue>,
        var newIssueList: List<Issue>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldIssueList.size
        }

        override fun getNewListSize(): Int {
            return newIssueList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldIssueList[oldItemPosition].id == newIssueList[newItemPosition].id)

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldIssueList[oldItemPosition] == newIssueList[newItemPosition]

        }

    }


}