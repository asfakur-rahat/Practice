package com.ar.practice.adapter.business


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.practice.R.color.*
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.ItemCountryBinding

class CountryListAdapter(
    private val onAdd: (Country) -> Unit,
    private val onRemove: (Country) -> Unit
): ListAdapter<Country, CountryListAdapter.CountryViewHolder>(DiffChecker) {

    companion object {
        val DiffChecker = object : DiffUtil.ItemCallback<Country>(){
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem.id == newItem.id
        }
    }

    class CountryViewHolder(
        private val binding: ItemCountryBinding,
        private val onAdd: (Country) -> Unit,
        private val onRemove: (Country) -> Unit
    ): ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(country: Country){

            binding.countryFlag.setImageResource(country.flag)
            binding.countryName.text = country.name
            if (country.isSelected){
                binding.isSelected.visibility = View.VISIBLE
                binding.root.setOnClickListener {
                    onRemove(country)
                }
            }else{
                binding.isSelected.visibility = View.GONE
                binding.root.setOnClickListener {
                    onAdd(country)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding, onAdd, onRemove)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.isSelected = getItem(position).isSelected
    }
}