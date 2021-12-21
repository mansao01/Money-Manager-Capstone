package com.mansao.moneymanagercapstone.ui.addtask.money.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mansao.capstonedraft.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.databinding.FragmentDayTransactionBinding
import com.mansao.moneymanagercapstone.helper.DateHelper
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.addtask.money.TransactionAdapter
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel

class DayTransactionFragment : Fragment() {
    private var _binding: FragmentDayTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : TransactionAdapter
    private lateinit var viewModel: TransactionAddUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTodayTransaction()
    }

    private fun getTodayTransaction(){
        val dataForTypeTransaction = arguments?.getString(MoneyAddUpdateActivity.EXTRA_NOTE)
        val today = DateHelper.getCurrentDate()

        viewModel = obtainViewModel(activity as AppCompatActivity)
        if (dataForTypeTransaction != null){
            viewModel.getTodayTransaction(dataForTypeTransaction, today )
                .observe(viewLifecycleOwner, {transactionList ->
                    adapter.setListTransaction(transactionList)
                })
        }

        adapter = TransactionAdapter(activity as AppCompatActivity)
        binding.rvDayTransaction.layoutManager = LinearLayoutManager(activity)
        binding.rvDayTransaction.setHasFixedSize(true)
        binding.rvDayTransaction.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TransactionAddUpdateViewModel::class.java)
    }


}