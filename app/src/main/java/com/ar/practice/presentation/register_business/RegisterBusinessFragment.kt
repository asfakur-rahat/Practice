package com.ar.practice.presentation.register_business

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ar.practice.R
import com.ar.practice.bottomsheets.CountrySelectionBottomSheet
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.databinding.FragmentRegisterBusinessBinding
import com.google.android.material.textview.MaterialTextView

class RegisterBusinessFragment : Fragment() {

    private val viewModel: RegisterBusinessViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBusinessBinding

    private var allCountry = DemoData.countries

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
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.actionBar.tvTitle.text = "Business activities"
        binding.actionBar.tvSubtitle.text = "Please tell us a bit more about your business activities"

        binding.employeeNumber.tagLabel.text = "Employee number"
        binding.employeeNumber.hint.text = "Select one"
        binding.employeeNumber.ivInfo.visibility = View.GONE

        binding.internationalActivity.tagLabel.text = "International activity"
        binding.internationalActivity.tvInfo.text = "If you sell products and services, or source any supplies from outside your trading country, please select “Yes”"

        binding.internationalCountries.tagLabel.text = "Active countries"
        binding.internationalCountries.hint.text ="Select all that applies"
        binding.internationalCountries.tvInfo.text = "Select all countries where you have any kind of buying or selling activity"
        binding.internationalCountries.root.visibility = View.GONE

        binding.bottomButton.tvContinue.text = "Continue"
        binding.bottomButton.btnContinue.setCardBackgroundColor(resources.getColor(R.color.inactive_orange, null))

        setupSpinner()
        initListener()

    }

    private fun initListener() {
        binding.internationalCountries.root.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun openBottomSheet() {
        val countrySheet = CountrySelectionBottomSheet(
            context = requireContext(),
            onCountrySelected = {

            },
            onComplete = {

            },
            onCancel = {

            },
            countryList = allCountry
        )
        countrySheet.show()
    }


    private fun setupSpinner() {
        val items = listOf("Select One", "Yes", "No")

        val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.drop_down_item, items) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view as MaterialTextView

                if (position == 0) {
                    textView.setTextColor(resources.getColor(R.color.light_grey, null))
                } else {
                    textView.setTextColor(resources.getColor(R.color.black, null))
                }

                return view
            }
        }

        // Set the adapter to the Spinner
        binding.internationalActivity.dropdownMenu.customSpinner.adapter = adapter

        // Set a listener to handle item selection
        binding.internationalActivity.dropdownMenu.customSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position != 0) {
                    (view as MaterialTextView).setTextColor(resources.getColor(R.color.black, null))
                    if(position == 1){
                        binding.internationalCountries.root.visibility = View.VISIBLE
                    }else{
                        binding.internationalCountries.root.visibility = View.GONE
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle no selection if needed
            }
        }
    }
}