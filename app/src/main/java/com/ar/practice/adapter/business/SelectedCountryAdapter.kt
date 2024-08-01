package com.ar.practice.adapter.business

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.ItemBasicSelectableBinding

class SelectedCountryAdapter(
    private val onRemove: (Country) -> Unit
):ListAdapter<Country, SelectedCountryAdapter.SelectedCountryViewHolder>(DiffChecker) {

    companion object {
        val DiffChecker = object : DiffUtil.ItemCallback<Country>(){
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem == newItem
        }
    }

    class SelectedCountryViewHolder(
        private val binding: ItemBasicSelectableBinding,
        private val onRemove: (Country) -> Unit
    ): ViewHolder(binding.root){
        fun bind(country: Country){
            binding.countryName.text = country.name
            binding.removeIcon.setOnClickListener {
                onRemove(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedCountryViewHolder {
        val binding = ItemBasicSelectableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedCountryViewHolder(binding, onRemove)
    }

    override fun onBindViewHolder(holder: SelectedCountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}