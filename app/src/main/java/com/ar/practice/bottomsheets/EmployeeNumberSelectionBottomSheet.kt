package com.ar.practice.bottomsheets

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.adapter.business.CountryListAdapter
import com.ar.practice.adapter.business.SelectedCountryAdapter
import com.ar.practice.adapter.employee.EmployeeNumberAdapter
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.data.model.Country
import com.ar.practice.data.model.EmployeeNumber
import com.ar.practice.databinding.LayoutEmployeeNumberBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class EmployeeNumberSelectionBottomSheet(
    context: Context,
    private val onClick: (EmployeeNumber) -> Unit,
) : BottomSheetDialog(context) {

    private lateinit var binding: LayoutEmployeeNumberBottomSheetBinding
    private lateinit var adapter: EmployeeNumberAdapter
    private val initList = DemoData.employeeNum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutEmployeeNumberBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        }

    }

    private fun initListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initItems() {
        adapter = EmployeeNumberAdapter {
            onClick(it)
            dismiss()
        }
        setRecyclerView(initList)
    }

    private fun setRecyclerView(list: List<EmployeeNumber>) {
        binding.rvSelectableItems.layoutManager = LinearLayoutManager(context)
        binding.rvSelectableItems.adapter = adapter
        submitList(list)
    }

    private fun submitList(list: List<EmployeeNumber>) {
        adapter.submitList(list)
    }


    private fun initView() {
        binding.etSearch.doAfterTextChanged {
            val query = it.toString()
            //println(query.toInt())
            if(query.isNotEmpty()){
                val newList = initList.filter { range ->
                    val (min, max) = parseRange(range.range)
                    query.toInt() in min..max
                }
                submitList(newList)
            }else submitList(initList)
        }
    }



    private fun parseRange(range: String): Pair<Int, Int> {
        return when {
            range.endsWith("+") -> {
                val min = range.substringBefore("+").toInt()
                Pair(min, Int.MAX_VALUE)
            }
            range.contains("-") -> {
                val parts = range.split("-")
                val min = parts[0].toInt()
                val max = parts[1].toInt()
                Pair(min, max)
            }
            else -> Pair(-1, -1)
        }
    }
}