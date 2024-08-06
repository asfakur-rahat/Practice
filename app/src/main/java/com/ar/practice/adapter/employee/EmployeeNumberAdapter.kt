package com.ar.practice.adapter.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.data.model.EmployeeNumber
import com.ar.practice.databinding.CustomEmployeeRangeBinding

class EmployeeNumberAdapter(
    private val onClick: (EmployeeNumber) -> Unit
):ListAdapter<EmployeeNumber, EmployeeNumberAdapter.NumberViewHolder>(DiffChecker) {
    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<EmployeeNumber>(){
            override fun areItemsTheSame(
                oldItem: EmployeeNumber,
                newItem: EmployeeNumber
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: EmployeeNumber,
                newItem: EmployeeNumber
            ): Boolean = oldItem.range == newItem.range
        }
    }
    class NumberViewHolder(
        private val binding: CustomEmployeeRangeBinding,
        private val onClick: (EmployeeNumber) -> Unit
    ): ViewHolder(binding.root){
        fun bind(item: EmployeeNumber){
            binding.range.text = item.range
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = CustomEmployeeRangeBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NumberViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}