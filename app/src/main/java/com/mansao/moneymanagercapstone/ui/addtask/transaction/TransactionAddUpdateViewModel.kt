package com.mansao.moneymanagercapstone.ui.addtask.transaction

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.money.Transaction
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class TransactionAddUpdateViewModel (application: Application) : ViewModel() {
    private val moneyManagerRepository: MoneyManagerRepository = MoneyManagerRepository(application)

    fun insert(transaction: Transaction) {
        moneyManagerRepository.insertTransaction(transaction)
    }

    fun update(transaction: Transaction) {
        moneyManagerRepository.updateTransaction(transaction)
    }

    fun delete(transaction: Transaction) {
        moneyManagerRepository.deleteTransaction(transaction)
    }

    fun getAllTransaction(): LiveData<List<Transaction>> = moneyManagerRepository.getAllTransaction()



}