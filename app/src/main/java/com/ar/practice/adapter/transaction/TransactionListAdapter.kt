package com.ar.practice.adapter.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.R
import com.ar.practice.data.model.Transaction
import com.ar.practice.databinding.ItemTransactionBinding
import com.ar.practice.utils.setVisibility

class TransactionListAdapter(
    private val onClick: () -> Unit
):ListAdapter<Transaction, TransactionListAdapter.ListViewHolder>(DiffChecker) {
    companion object {
        val DiffChecker = object : DiffUtil.ItemCallback<Transaction>(){
            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean  = oldItem.id == newItem.id
        }
    }

    class  ListViewHolder(
        private val binding: ItemTransactionBinding,
        private val onClick: () -> Unit
    ):ViewHolder(binding.root){
        fun bind(item: Transaction, date: String, visibility: Boolean){
            binding.icTransactionType.setImageResource(item.icon)
            binding.tvTransactionTitle.text = item.title
            binding.tvTransactionAmount.text = item.amount
            binding.root.setOnClickListener {
                onClick
            }
            if (item.type == "in"){
                binding.tvTransactionAmount.setTextColor(binding.root.context.getColor(R.color.green))
            }else{
                binding.tvTransactionAmount.setTextColor(binding.root.context.getColor(R.color.orange))
            }
            if(visibility){
                binding.tvDate.text = date
            }else{
                binding.tvDate.setVisibility(visibility)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onClick)
    }

    private var date = ""
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if(date!=item.date) {
            date = item.date
            holder.bind(item, date, true)
        }
        else{
            holder.bind(item, date, false)
        }
    }
}