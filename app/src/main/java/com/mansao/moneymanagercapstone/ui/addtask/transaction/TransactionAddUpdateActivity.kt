package com.mansao.moneymanagercapstone.ui.addtask.transaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mansao.capstonedraft.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.database.Transaction
import com.mansao.moneymanagercapstone.databinding.ActivityTransactionAddUpdateBinding
import com.mansao.moneymanagercapstone.helper.DateHelper

class TransactionAddUpdateActivity : AppCompatActivity() {

    private var isEdit = false
    private var transaction: Transaction? = null
    private var dataTypeTransaction: Money? = null
    private var position = 0

    private lateinit var transactionAddUpdateViewModel: TransactionAddUpdateViewModel
    private var _activityTransactionAddUpdateBinding: ActivityTransactionAddUpdateBinding? = null
    private val binding get() = _activityTransactionAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityTransactionAddUpdateBinding =
            ActivityTransactionAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        transactionAddUpdateViewModel =
            obtainTransactionViewModel(this@TransactionAddUpdateActivity)

        transaction = intent.getParcelableExtra(EXTRA_NOTE)
        if (transaction != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            transaction = Transaction()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = transaction?.title_transaction.toString()
            btnTitle = getString(R.string.delete)
            if (transaction != null) {
                transaction?.let { transaction ->
                    binding?.edtTitle?.setText(transaction.title_transaction)
                    binding?.edtDescription?.setText(transaction.desc_transaction)
                    binding?.edtIncome?.setText(transaction.income)
                    binding?.edtOutcome?.setText(transaction.outcome)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnSubmit?.text = btnTitle

        dataTypeTransaction = intent.getParcelableExtra(EXTRA_DATA)
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val desc = binding?.edtDescription?.text.toString().trim()
            val income = binding?.edtIncome?.text.toString().trim()
            val outcome = binding?.edtOutcome?.text.toString().trim()
            if (title.isEmpty()) {
                binding?.edtTitle?.error = getString(R.string.empty)
            } else if (desc.isEmpty()) {
                binding?.edtDescription?.error = getString(R.string.empty)
            } else if (income.isEmpty()) {
                binding?.edtIncome?.error = getString(R.string.empty)
            } else if (outcome.isEmpty()) {
                binding?.edtOutcome?.error = getString(R.string.empty)
            } else {
                transaction.let { transaction ->
                    transaction?.title_transaction = title
                    transaction?.desc_transaction = desc
                    transaction?.income = income
                    transaction?.outcome = outcome
                    transaction?.typeTransaction = dataTypeTransaction?.titleMoney
                }
                val intent = Intent().apply {
                    putExtra(EXTRA_NOTE, transaction)
                    putExtra(EXTRA_POSITION, position)
                }
                if (isEdit) {
                    transactionAddUpdateViewModel.update(transaction as Transaction)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    transaction.let { transaction ->
                        transaction?.date_transaction = DateHelper.getCurrentDate()
                        transaction?.weekTransaction = DateHelper.getWeek()
                        transaction?.monthTransaction = DateHelper.getMonth()
                        transaction?.yearTransaction = DateHelper.getYear()
                    }
                    transactionAddUpdateViewModel.insert(transaction as Transaction)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityTransactionAddUpdateBinding = null
    }


    private fun obtainTransactionViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TransactionAddUpdateViewModel::class.java)
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
    }

}