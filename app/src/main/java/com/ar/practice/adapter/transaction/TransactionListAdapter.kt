package com.ar.practice.adapter.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.R
import com.ar.practice.data.model.Transaction
import com.ar.practice.databinding.ItemTransactionBinding
import com.ar.practice.utils.beGone
import com.ar.practice.utils.isToday
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
        @SuppressLint("SetTextI18n")
        fun bind(item: Transaction, date: String, visibility: Boolean){
            binding.icTransactionType.setImageResource(item.icon)
            binding.tvTransactionTitle.text = item.title
            binding.root.setOnClickListener {
                //println("onClick")
                onClick()
            }
            if (item.type == "in"){
                binding.tvTransactionAmount.setTextColor(binding.root.context.getColor(R.color.green))
                binding.tvTransactionAmount.text = "+ " + item.amount
            }else{
                binding.tvTransactionAmount.setTextColor(binding.root.context.getColor(R.color.orange))
                binding.tvTransactionAmount.text = "- " + item.amount
            }
            if(visibility){
                if(isToday(date)){
                    binding.tvDate.text = "Today"
                }else{
                    binding.tvDate.text = date
                }
            }else{
                binding.tvDate.beGone()
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