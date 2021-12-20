package com.mansao.moneymanagercapstone.ui.addtask.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
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
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
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
                    }
                    transactionAddUpdateViewModel.insert(transaction as Transaction)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityTransactionAddUpdateBinding = null
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    transactionAddUpdateViewModel.delete(transaction as Transaction)
                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

}