package com.ar.practice.presentation.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ar.practice.R
import com.ar.practice.databinding.FragmentTransactionHistoryBinding
import com.google.android.material.textview.MaterialTextView


class TransactionHistoryFragment : Fragment() {
    private var _binding: FragmentTransactionHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItem()
        initNavigation()
        initListener()
    }

    private fun initItem() {
        setTabOneActive()
    }

    private fun initListener() {
        binding.tab.itemOne.setOnClickListener {
            setTabOneActive()
            navController.navigate(R.id.historyFragment)
        }
        binding.tab.itemTwo.setOnClickListener {
            setTabTwoActive()
            navController.navigate(R.id.manageAccountFragment)
        }
    }

    private fun setTabTwoActive() {
        binding.tab.itemTwo.isActivated = true
        binding.tab.itemOne.isActivated = false
    }

    private fun setTabOneActive() {
        binding.tab.itemOne.isActivated = true
        binding.tab.itemTwo.isActivated = false
    }

    private fun initNavigation() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fv_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}