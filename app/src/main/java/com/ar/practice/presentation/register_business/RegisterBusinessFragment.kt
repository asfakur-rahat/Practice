package com.ar.practice.presentation.register_business

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.R
import com.ar.practice.adapter.business.SelectedCountryAdapter
import com.ar.practice.bottomsheets.CountrySelectionBottomSheet
import com.ar.practice.bottomsheets.EmployeeNumberSelectionBottomSheet
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.data.model.Country
import com.ar.practice.databinding.FragmentRegisterBusinessBinding
import com.ar.practice.utils.custom_ui.CustomAdapter
import com.ar.practice.utils.setVisibility
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textview.MaterialTextView

class RegisterBusinessFragment : Fragment() {

    private val viewModel: RegisterBusinessViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBusinessBinding
    private lateinit var adapter: SelectedCountryAdapter

    private var allCountry = DemoData.countries.toMutableList()
    private var selectedCountryList = mutableListOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SelectedCountryAdapter {
            val newList = allCountry
            newList[it.id- 1] = Country(it.id, it.flag,it.name,isSelected = false)
            selectedCountryList.remove(it)
            allCountry = newList
            updateSelectedCountry(selectedCountryList)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        binding.actionBar.tvTitle.text = "Business activities"
        binding.actionBar.tvSubtitle.text = "Please tell us a bit more about your business activities"

        binding.employeeNumber.customLabel = "Employee number"
        binding.employeeNumber.customHint = "Select one"

        binding.internationalActivity.dropdownMenu.hint = "International activity"
        binding.internationalActivity.tvInfo.text = "If you sell products and services, or source any supplies from outside your trading country, please select “Yes”"

        binding.internationalCountries.tagLabel.text = "Active countries"
        binding.internationalCountries.hint.text ="Select all that applies"
        binding.internationalCountries.tvInfo.text = "Select all countries where you have any kind of buying or selling activity"
        binding.internationalCountries.root.visibility = View.GONE

        binding.bottomButton.tvContinue.text = "Continue"
        binding.bottomButton.btnContinue.setCardBackgroundColor(resources.getColor(R.color.inactive_orange, null))
        initListener()

    }

    override fun onResume() {
        super.onResume()
        setupDropDown()
    }

    private fun initListener() {
        binding.internationalCountries.root.setOnClickListener {
            openBottomSheet()
        }
        binding.employeeNumber.setOnClickListener {
            openEmployeeBottomSheet()
        }

        binding.internationalActivity.customSpinner.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            binding.internationalCountries.root.setVisibility(selectedItem == "Yes")
        }
    }

    private fun openEmployeeBottomSheet() {
        val employeeSheet = EmployeeNumberSelectionBottomSheet(requireContext()){ range ->
            binding.employeeNumber.mainText = range.range
        }
        employeeSheet.show()
    }

    private fun openBottomSheet() {
        val countrySheet = CountrySelectionBottomSheet(
            context = requireContext(),
            onComplete = { mainList,selectedList ->
                allCountry = mainList.toMutableList()
                updateSelectedCountry(selectedList)
            },
            countryList = allCountry
        )
        countrySheet.setCancelable(false)
        countrySheet.show()
    }

    private fun updateSelectedCountry(list: List<Country>) {
        val isVisible = list.isEmpty()
        selectedCountryList = list.toMutableList()
        binding.internationalCountries.rvSelectedItems.setVisibility(!isVisible)
        binding.internationalCountries.hint.setVisibility(isVisible)
        if(!isVisible){
            setRV(list)
        }
    }

    private fun setRV(list: List<Country>) {
        binding.internationalCountries.rvSelectedItems.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.internationalCountries.rvSelectedItems.adapter = adapter
        adapter.submitList(list)
    }


    private fun setupDropDown() {
        val options = resources.getStringArray(R.array.options)
        val adapter = CustomAdapter(requireContext(), options)
        val dropDown = binding.internationalActivity.customSpinner
        dropDown.dropDownVerticalOffset = 25
        dropDown.setDropDownBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.bg_spinner, null))
        dropDown.setAdapter(adapter)

    }
}