package com.ar.practice.presentation.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.R
import com.ar.practice.adapter.business.CountryListAdapter
import com.ar.practice.adapter.business.SelectedCountryAdapter
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.FragmentTestBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TestFragment : Fragment() {

    private var _binding : FragmentTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private var countryList = DemoData.countries

    private lateinit var selectedAdapter: SelectedCountryAdapter
    private lateinit var countryListAdapter: CountryListAdapter
    private var selectedCountry = mutableListOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
    }

    private fun setBottomSheet() {
        val bottomSheet  = binding.persistentBottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        initView()
    }

    private fun initView() {
        binding.etSearch.doAfterTextChanged { text ->
            val query = text.toString()
            val newList = countryList.filter { country ->
                country.name.contains(query, ignoreCase = true)
            }
            submitList(newList)
        }
        binding.bottomButton.tvContinue.text = "Continue"
        binding.selectedTag.text = "Selected countries"

        initItems()
        initListener()
    }


    private fun setRecyclerView(countries: List<Country>) {
        countryList = countries
        binding.rvSelectableItems.layoutManager = LinearLayoutManager(context)
        binding.rvSelectableItems.adapter = countryListAdapter
        submitList(countries)
    }

    private fun submitList(countries: List<Country>) {
        countryListAdapter.submitList(countries)
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


    private fun filterItems() {
        val newList = countryList.filter { it.isSelected }
        selectedCountry = newList.toMutableList()
        upDateSelected(newList.toMutableList())
    }

    private fun initListener() {
        binding.bottomButton.btnContinue.setOnClickListener {
            bottomSheetBehavior.isHideable = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.btnCancel.setOnClickListener {
            //dismiss()
        }

        binding.mainContent.setOnClickListener {
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.peekHeight = binding.persistentBottomSheet.height / 2
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }


    private fun initItems() {
        countryListAdapter = CountryListAdapter(onAdd = {
            val newList = countryList.toMutableList()
            newList[it.id-1] = Country(it.id, it.flag,it.name,isSelected = true)
            setRecyclerView(newList)
            selectedCountry.add(it)
            upDateSelected(selectedCountry)
        })
        setRecyclerView(countryList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}