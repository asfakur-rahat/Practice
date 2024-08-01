package com.ar.practice.bottomsheets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.adapter.business.CountryListAdapter
import com.ar.practice.adapter.business.SelectedCountryAdapter
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.LayoutSelectableBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CountrySelectionBottomSheet(
    context: Context,
    private val onCountrySelected: (Country) -> Unit,
    private val onCancel: () -> Unit,
    private val onComplete: (List<Country>) -> Unit,
    private var countryList: List<Country>
): BottomSheetDialog(context) {
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
            newList[it.id] = it.copy(isSelected = true)
            countryList = newList
            countryListAdapter.submitList(newList)
        },{})
        binding.rvSelectableItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectableItems.adapter = countryListAdapter
        countryListAdapter.submitList(countryList)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.tagHint.text = "Search country"
        binding.selectedTag.text = "Selected Countries"
        binding.bottomButton.tvContinue.text = "Continue"

        binding.etSearch.doAfterTextChanged {
            if (it.toString().isNotEmpty()){
                binding.tagHint.text = it.toString()
            }else{
                binding.tagHint.text = "Search country"
            }

        }
    }
}