package com.mansao.moneymanagercapstone.ui.addtask.money

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.mansao.capstonedraft.helper.ViewModelFactory
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.database.Transaction
import com.mansao.moneymanagercapstone.databinding.ActivityMoneyAddUpdateBinding
import com.mansao.moneymanagercapstone.helper.DateHelper
import com.mansao.moneymanagercapstone.helper.SectionPagerAdapter
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateActivity
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel

class MoneyAddUpdateActivity : AppCompatActivity() {

    private var isEdit = false
    private var money: Money? = null
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


        money = intent.getParcelableExtra(EXTRA_NOTE)
        if (money != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            money = Money()
        }
        val btnTitle: String
        if (isEdit) {
            supportActionBar?.title = money?.titleMoney
            btnTitle = getString(R.string.update)
            if (money != null) {
                money?.let { money ->
                    binding?.edtTitle?.setText(money.titleMoney)
                    binding?.edtDescription?.setText(money.titleMoney)
                }
            }
        } else {
            btnTitle = getString(R.string.save)
            supportActionBar?.title = getString(R.string.add)
        }

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
                money.let { money ->
                    money?.titleMoney = title
                    money?.descMoney = desc
                }
                val intent = Intent().apply {
                    putExtra(EXTRA_NOTE, money)
                    putExtra(EXTRA_POSITION, position)
                }
                if (isEdit) {
                    moneyAddUpdateViewModel.update(money as Money)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    money.let { money ->
                        money?.dateMoney = DateHelper.getCurrentDate()
                    }
                    moneyAddUpdateViewModel.insert(money as Money)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }


        val dataForTypeTransaction = money?.titleMoney
        transactionAddUpdateViewModel = obtainTransactionViewModel(this@MoneyAddUpdateActivity)
        if (dataForTypeTransaction != null) {

            transactionAddUpdateViewModel.getOutcome(dataForTypeTransaction)
                .observe(this, outcomeObserver)

            transactionAddUpdateViewModel.getIncome(dataForTypeTransaction)
                .observe(this, incomeObserver)

            transactionAddUpdateViewModel.getTotalMoney(dataForTypeTransaction)
                .observe(this, totalMoneyObserver)
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_NOTE, dataForTypeTransaction)
        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)

        binding?.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager){tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        binding?.fabAddTransaction?.setOnClickListener { view ->
            if (view.id == R.id.fab_add_transaction) {
                val intent =
                    Intent(this@MoneyAddUpdateActivity, TransactionAddUpdateActivity::class.java)
                intent.putExtra(TransactionAddUpdateActivity.EXTRA_DATA, money)
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
                    moneyAddUpdateViewModel.delete(money as Money)
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

    private fun obtainTransactionViewModel(activity: AppCompatActivity): TransactionAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
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

    private val outcomeObserver = Observer<Int> { outcome ->
        if (outcome != null) {
            binding?.tvTotalOutcome?.text = "- ${outcome.toString()}"
        }
    }

    private val incomeObserver = Observer<Int> { income ->
        if (income != null) {
            binding?.tvTotalIncome?.text = "+ ${income.toString()}"
        }
    }

    private val totalMoneyObserver = Observer<Int> { totalMoney ->
        if (totalMoney != null) {
            binding?.tvTotalMoney?.text = "Total : ${totalMoney.toString()}"
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

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

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.all_transaction,
            R.string.daily_transaction,
            R.string.weekly_transaction,
            R.string.month_transaction,
            R.string.year_transaction
        )
    }
}