package com.ar.practice.presentation.transaction.account

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ar.practice.R
import com.ar.practice.data.model.Transaction
import com.ar.practice.databinding.FragmentManageAccountBinding
import com.ar.practice.utils.*
import com.ar.practice.utils.getTransactionList
import com.ar.practice.utils.putTransactionList
import com.ar.practice.utils.sortTransactionsByDateDescending
import java.util.Date

class ManageAccountFragment : Fragment() {
    private var _binding: FragmentManageAccountBinding? = null
    private val binding get() = _binding!!
    private var historyList = mutableListOf<Transaction>()

    private var listener: AccountListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (parentFragment?.parentFragment as? AccountListener) ?: throw IllegalArgumentException("Outer fragment must implement AccountListener")

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentManageAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initList()
        initListener()
    }

    private fun initListener() {
        binding.btnOk.btnContinue.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val amount = binding.etAmount.text.toString()
            val type = binding.etType.text.toString()
            val isValid = validate(title, amount, type)

            if(isValid){
                historyList.add(
                    Transaction(
                        id = historyList.size,
                        date = formatDate(Date()),
                        title = title,
                        type = type,
                        amount = amount,
                        icon = if(type == "in") R.drawable.ic_cash_in else R.drawable.ic_cash_out
                    )
                )
                saveList(sortTransactionsByDateDescending(historyList.toList()).toMutableList())
                gotoTransactionHistory()
            }
        }
    }

    private fun gotoTransactionHistory() {
        binding.etTitle.text?.clear()
        binding.etAmount.text?.clear()
        binding.etType.text?.clear()
        listener?.onSaveComplete()
    }

    private fun validate(title: String, amount: String, type: String): Boolean {
        if(title.isEmpty() || amount.isEmpty() || type.isEmpty()){
            if (title.isEmpty()) binding.etTitle.error = "All fields are required"
            if(amount.isEmpty()) binding.etAmount.error = "All fields are required"
            if(type.isEmpty()) binding.etType.error = "All fields are required"
            return false
        }
        if(type != "in" && type != "out"){
            binding.etType.error = "only enter in or out"
            return false
        }
        return true
    }

    private fun initList() {
        historyList = requireContext().getSharedPreferences("transaction_list", Context.MODE_PRIVATE).getTransactionList("history") as MutableList<Transaction>
    }

    private fun saveList(historyList: MutableList<Transaction>) {
        requireContext().getSharedPreferences("transaction_list", Context.MODE_PRIVATE).putTransactionList("history", historyList)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.btnOk.tvContinue.text = "Save Transaction"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}