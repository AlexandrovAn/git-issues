package com.petproject.gitissues.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.petproject.gitissues.R
import com.petproject.gitissues.databinding.IssueItemBinding
import com.petproject.gitissues.model.Issue
import kotlinx.android.synthetic.main.issue_item.view.*

class IssueListAdapter : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    private var issues = mutableListOf<Issue>()
    var clickListener: ((selectItemNum: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IssueItemBinding.inflate(inflater, parent, false)
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

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        with(holder) {
            with(issues[position]) {
                binding.issueTitle.text = title
                binding.secondaryTextTv.text = holder.binding.root.resources.getString(
                    R.string.issue_number_author,
                    number,
                    user?.login
                )
                Glide.with(binding.root)
                    .load(user?.avatarUrl).apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.placeholder)
                    .into(binding.userAvatarIv)

            }
        }

    }

    override fun getItemCount(): Int = issues.size

    override fun getItemId(position: Int): Long = issues[position].id.toLong()

    class IssueViewHolder(val binding: IssueItemBinding) : RecyclerView.ViewHolder(binding.root)

    class IssueItemDiffCallback(
        var oldIssueList: List<Issue>,
        var newIssueList: List<Issue>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldIssueList.size

        override fun getNewListSize(): Int = newIssueList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            (oldIssueList[oldItemPosition].id == newIssueList[newItemPosition].id)

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldIssueList[oldItemPosition] == newIssueList[newItemPosition]
    }


}