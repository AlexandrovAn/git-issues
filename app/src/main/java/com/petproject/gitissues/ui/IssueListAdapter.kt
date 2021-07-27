package com.petproject.gitissues.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petproject.gitissues.databinding.IssueItemBinding
import com.petproject.gitissues.model.Issue

class IssueListAdapter : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    private var issues = mutableListOf<Issue>()
    var clickListener: ((selectItemNum: Int)->Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: IssueItemBinding = IssueItemBinding.inflate(inflater, parent, false)
        val holder = IssueViewHolder(binding)
        holder.binding.root.setOnClickListener{
            if(holder.bindingAdapterPosition != RecyclerView.NO_POSITION){
                Log.e("onClickListener","init")
                clickListener?.invoke(holder.bindingAdapterPosition)
            }
        }
        return holder
    }

    fun setDataset(list: List<Issue>){
        issues.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        Log.e("OnBindViewHolder","init")
        holder.binding.issue = issues[position]
    }

    class IssueViewHolder(val binding: IssueItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return issues.size
    }
}