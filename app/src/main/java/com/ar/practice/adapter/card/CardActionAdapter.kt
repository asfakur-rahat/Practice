package com.ar.practice.adapter.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.data.model.CardAction
import com.ar.practice.databinding.ItemCardOptionsBinding

class CardActionAdapter(
    private val onActionClick: (CardAction) -> Unit
): ListAdapter<CardAction, CardActionAdapter.CardActionViewHolder>(DiffChecker) {

    class CardActionViewHolder(
        private val binding: ItemCardOptionsBinding,
        private val onActionClick: (CardAction) -> Unit
    ): ViewHolder(binding.root) {
        fun bind(item: CardAction){
            binding.icon.setImageResource(item.icon)
            binding.tvTitle.text = item.title

            binding.root.setOnClickListener {
                onActionClick(item)
            }
        }
    }

    companion object{
        val DiffChecker = object : DiffUtil.ItemCallback<CardAction>() {
            override fun areItemsTheSame(oldItem: CardAction, newItem: CardAction): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: CardAction, newItem: CardAction): Boolean  = newItem.title == oldItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardActionViewHolder {
        val binding = ItemCardOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardActionViewHolder(binding, onActionClick)
    }

    override fun onBindViewHolder(holder: CardActionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}