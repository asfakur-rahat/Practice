package com.ar.practice.presentation.transaction.history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.practice.R
import com.ar.practice.R.drawable.ic_cash_out
import com.ar.practice.adapter.transaction.TransactionListAdapter
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.databinding.FragmentHistoryBinding
import com.ar.practice.utils.getTransactionList


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransactionListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var listener: HistoryListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (parentFragment?.parentFragment as? HistoryListener) ?: throw IllegalArgumentException("Outer fragment must implement HistoryListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initItems()
    }

    private fun initItems() {
        sharedPreferences = requireContext().getSharedPreferences("transaction_list", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        adapter = TransactionListAdapter (onClick = {
            //println("onClick in fragment")
            listener?.onItemClick()
        })
        binding.rvTransactionHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTransactionHistory.adapter = adapter
        adapter.submitList(sharedPreferences.getTransactionList("history"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}