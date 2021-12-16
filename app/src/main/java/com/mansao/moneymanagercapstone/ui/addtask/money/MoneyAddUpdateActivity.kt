package com.mansao.moneymanagercapstone.ui.addtask.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.money.Money
import com.mansao.moneymanagercapstone.database.transaction.Transaction
import com.mansao.moneymanagercapstone.databinding.ActivityMoneyAddUpdateBinding
import com.mansao.moneymanagercapstone.helper.DateHelper
import com.mansao.moneymanagercapstone.helper.money.ViewModelFactory
import com.mansao.moneymanagercapstone.helper.transaction.ViewModelTransactionFactory
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel

class MoneyAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Money? = null
    private var position = 0

    private lateinit var moneyAddUpdateViewModel: MoneyAddUpdateViewModel
    private lateinit var transactionAddUpdateViewModel: TransactionAddUpdateViewModel
    private var _activityMoneyAddUpdateBinding: ActivityMoneyAddUpdateBinding? = null
    private val binding get() = _activityMoneyAddUpdateBinding
    private lateinit var adapterTransaction: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMoneyAddUpdateBinding = ActivityMoneyAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        moneyAddUpdateViewModel = obtainViewModel(this@MoneyAddUpdateActivity)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Money()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note ->
                    binding?.edtTitle?.setText(note.title_note)
                    binding?.edtDescription?.setText(note.desc_note)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnSubmit?.text = btnTitle


        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val desc = binding?.edtDescription?.text.toString().trim()
            if (title.isEmpty()) {
                binding?.edtTitle?.error = getString(R.string.empty)
            } else if (desc.isEmpty()) {
                binding?.edtDescription?.error = getString(R.string.empty)
            } else {
                note.let { note ->
                    note?.title_note = title
                    note?.desc_note = desc
                }
                val intent = Intent().apply {
                    putExtra(EXTRA_NOTE, note)
                    putExtra(EXTRA_POSITION, position)
                }
                if (isEdit) {
                    moneyAddUpdateViewModel.update(note as Money)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note.let { note ->
                        note?.date_note = DateHelper.getCurrentDate()
                    }
                    moneyAddUpdateViewModel.insert(note as Money)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }

        //add transaction
        transactionAddUpdateViewModel = obtainTransactionViewModel(this@MoneyAddUpdateActivity)
        transactionAddUpdateViewModel.getAllTransaction().observe(this, transactionObserver)

        adapterTransaction = TransactionAdapter(this@MoneyAddUpdateActivity)

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapterTransaction

        binding?.fabAddTransaction?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MoneyAddUpdateActivity, TransactionAddUpdateActivity::class.java)
                startActivityForResult(intent, TransactionAddUpdateActivity.REQUEST_ADD)
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
        _activityMoneyAddUpdateBinding = null
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
                    moneyAddUpdateViewModel.delete(note as Money)
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

    private fun obtainViewModel(activity: AppCompatActivity): MoneyAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MoneyAddUpdateViewModel::class.java)
    }

    //add transaction

    private fun obtainTransactionViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel {
        val factory = ViewModelTransactionFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(TransactionAddUpdateViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == TransactionAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == TransactionAddUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == TransactionAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == TransactionAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == TransactionAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    private val transactionObserver = Observer<List<Transaction>> { transactionList ->
        if (transactionList != null) {
            adapterTransaction.setListTransaction(transactionList)
        }
    }
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }
}