package com.ar.practice.presentation.transaction.history

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


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransactionListAdapter

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
        adapter = TransactionListAdapter {

        }
        binding.rvTransactionHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTransactionHistory.adapter = adapter
        adapter.submitList(DemoData.transactionHistory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}