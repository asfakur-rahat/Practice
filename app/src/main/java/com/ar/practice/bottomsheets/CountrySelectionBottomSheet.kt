package com.ar.practice.bottomsheets

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.adapter.business.CountryListAdapter
import com.ar.practice.adapter.business.SelectedCountryAdapter
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.LayoutSelectableBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CountrySelectionBottomSheet(
    context: Context,
    private val onCountrySelected: (Country) -> Unit,
    private val onCancel: () -> Unit,
    private val onComplete: (List<Country>) -> Unit,
    private var countryList: List<Country>
) : BottomSheetDialog(context) {

    private lateinit var binding: LayoutSelectableBottomSheetBinding
    private lateinit var selectedAdapter: SelectedCountryAdapter
    private lateinit var countryListAdapter: CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSelectableBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initItems()
    }


    private fun initItems() {
        countryListAdapter = CountryListAdapter({
            val newList = countryList.toMutableList()
            newList[it.id-1] = Country(it.id, it.flag,it.name,isSelected = true)
            setRecyclerView(newList)
            println(newList)
        }, {
            val newList = countryList.toMutableList()
            newList[it.id-1] = Country(it.id, it.flag,it.name,isSelected = false)
            setRecyclerView(newList)
            println(newList)
        })
        setRecyclerView(countryList)
    }

    private fun setRecyclerView(countrys: List<Country>) {
        countryList = countrys
        binding.rvSelectableItems.layoutManager = LinearLayoutManager(context)
        binding.rvSelectableItems.adapter = countryListAdapter
        countryListAdapter.submitList(countrys)
    }


    private fun initView() {
        binding.etSearch.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                binding.tagHint.text = it.toString()
            } else {
                binding.tagHint.text = "Search country"
            }
        }
        binding.bottomButton.tvContinue.text = "Continue"
        binding.selectedTag.text = "Selected countries"
    }
}
