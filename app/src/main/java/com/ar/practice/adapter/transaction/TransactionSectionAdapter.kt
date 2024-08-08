package com.ar.practice.adapter.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.data.model.Transaction
import com.ar.practice.databinding.ItemHistoryMainBinding
import kotlinx.coroutines.launch

class TransactionSectionAdapter(
    private val onClick: () -> Unit,
) : ListAdapter<Transaction, TransactionSectionAdapter.ListViewHolder>(DiffChecker) {
    companion object {
        val DiffChecker = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem.id == newItem.id
        }
    }

    inner class ListViewHolder(
        private val binding: ItemHistoryMainBinding,
        private val parent: ViewGroup,
        private val onClick: () -> Unit,
    ) : ViewHolder(binding.root) {
        fun bindDate(date: String) {
            binding.tvDate.text = date
        }

        fun bind(transactionList: List<Transaction>) {
            val adapter = TransactionListAdapter {
                onClick
            }
            binding.rvTransactionItem.layoutManager =
                LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
            binding.rvTransactionItem.adapter = adapter
            adapter.submitList(transactionList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemHistoryMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, parent, onClick)
    }

    private var date = ""
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if(date!=item.date){
            date = item.date
            holder.bindDate(item.date)
            holder.bind(currentList.filter { it.date == item.date })
        }
    }
}