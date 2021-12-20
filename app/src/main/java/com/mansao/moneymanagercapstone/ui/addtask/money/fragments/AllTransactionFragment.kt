package com.mansao.moneymanagercapstone.ui.addtask.money.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mansao.capstonedraft.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Transaction
import com.mansao.moneymanagercapstone.databinding.FragmentAllTransactionBinding
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.addtask.money.TransactionAdapter
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel


class AllTransactionFragment : Fragment() {

    private var _binding: FragmentAllTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : TransactionAdapter
    private lateinit var viewModel: TransactionAddUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllTransaction()
    }

    private fun getAllTransaction(){
        val dataForTypeTransaction = arguments?.getString(MoneyAddUpdateActivity.EXTRA_NOTE)

        viewModel = obtainViewModel(activity as AppCompatActivity)
        if (dataForTypeTransaction != null) {
            viewModel.getAllTransaction(dataForTypeTransaction)
                .observe(viewLifecycleOwner, transactionObserver)
        }

        adapter = TransactionAdapter(activity as AppCompatActivity)
        binding.rvAllTransaction.layoutManager = LinearLayoutManager(activity)
        binding.rvAllTransaction.setHasFixedSize(true)
        binding.rvAllTransaction.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TransactionAddUpdateViewModel::class.java)
    }

    private val transactionObserver = Observer<List<Transaction>> { transactionList ->
        if (transactionList != null) {
            adapter.setListTransaction(transactionList)
        }
    }
}