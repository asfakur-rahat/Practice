package com.ar.practice.adapter.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.R
import com.ar.practice.data.model.Transaction
import com.ar.practice.databinding.ItemTransactionBinding

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
        fun bind(item: Transaction){
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onClick)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        println(currentList.size)
        holder.bind(item)
    }
}