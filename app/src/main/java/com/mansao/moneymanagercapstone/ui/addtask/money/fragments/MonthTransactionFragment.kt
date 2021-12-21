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
import com.mansao.moneymanagercapstone.databinding.FragmentMonthTransactionBinding
import com.mansao.moneymanagercapstone.helper.DateHelper
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.addtask.money.TransactionAdapter
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel

class MonthTransactionFragment : Fragment() {
    private var _binding: FragmentMonthTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : TransactionAdapter
    private lateinit var viewModel: TransactionAddUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMonthTransaction()
    }

    private fun getMonthTransaction(){
        val dataForTypeTransaction = arguments?.getString(MoneyAddUpdateActivity.EXTRA_NOTE)
        val thisMonth = DateHelper.getMonth()

        viewModel = obtainViewModel(activity as AppCompatActivity)
        if (dataForTypeTransaction != null){
            viewModel.getMonthTransaction(dataForTypeTransaction, thisMonth)
                .observe(viewLifecycleOwner, {transactionList ->
                    adapter.setListTransaction(transactionList)
                })
        }

        adapter = TransactionAdapter(activity as AppCompatActivity)
        binding.rvMonthTransaction.layoutManager = LinearLayoutManager(activity)
        binding.rvMonthTransaction.setHasFixedSize(true)
        binding.rvMonthTransaction.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TransactionAddUpdateViewModel::class.java)
    }
}