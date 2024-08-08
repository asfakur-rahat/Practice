package com.ar.practice.bottomsheets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
    private val onComplete: (List<Country>, List<Country>) -> Unit,
    private var countryList: List<Country>
) : BottomSheetDialog(context) {

    private lateinit var binding: LayoutSelectableBottomSheetBinding
    private lateinit var selectedAdapter: SelectedCountryAdapter
    private lateinit var countryListAdapter: CountryListAdapter
    private var selectedCountry = mutableListOf<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSelectableBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        filterItems()
        initView()
        initBehavior()
        initListener()
        initItems()
    }

    private fun initBehavior() {
        val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            bottomSheet.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(it)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            //behavior.setDraggableOnNestedScroll(true)
        }
    }

    private fun filterItems() {
        val newList = countryList.filter { it.isSelected }
        selectedCountry = newList.toMutableList()
        upDateSelected(newList.toMutableList())
    }

    private fun initListener() {
        binding.bottomButton.btnContinue.setOnClickListener {
            onComplete(countryList, selectedCountry)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }


    private fun initItems() {
        countryListAdapter = CountryListAdapter(onAdd = {
            val newList = countryList.toMutableList()
            newList[it.id-1] = Country(it.id, it.flag,it.name,isSelected = true)
            setRecyclerView(newList)
            selectedCountry.add(it)
            upDateSelected(selectedCountry)
        }, onRemove =  {

        })
        setRecyclerView(countryList)
    }

    private fun updateList(item: Country) {
        val newList = countryList.toMutableList()
        newList[item.id-1] = Country(item.id, item.flag,item.name,isSelected = false)
        setRecyclerView(newList)
    }

    private fun upDateSelected(country: MutableList<Country>) {
        binding.rvSelectedItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        selectedAdapter = SelectedCountryAdapter {
            selectedCountry.remove(it)
            updateList(it)
            upDateSelected(selectedCountry)
        }
        binding.rvSelectedItems.adapter = selectedAdapter
        selectedAdapter.submitList(country)
    }

    private fun setRecyclerView(countrys: List<Country>) {
        countryList = countrys
        binding.rvSelectableItems.layoutManager = LinearLayoutManager(context)
        binding.rvSelectableItems.adapter = countryListAdapter
        submitList(countrys)
    }

    private fun submitList(countrys: List<Country>) {
        countryListAdapter.submitList(countrys)
    }


    private fun initView() {
        binding.etSearch.doAfterTextChanged {
            val query = it.toString()
            val newList = countryList.filter {
                it.name.contains(query, ignoreCase = true)
            }
            submitList(newList)
        }
        binding.bottomButton.tvContinue.text = "Continue"
        binding.selectedTag.text = "Selected countries"
    }
}
