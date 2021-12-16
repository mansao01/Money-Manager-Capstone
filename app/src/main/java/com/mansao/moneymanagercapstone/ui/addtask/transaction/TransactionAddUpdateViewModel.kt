package com.mansao.moneymanagercapstone.ui.addtask.transaction

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.transaction.Transaction
import com.mansao.moneymanagercapstone.repository.TransactionRepository

class TransactionAddUpdateViewModel (application: Application) : ViewModel() {
    private val mNoteRepository: TransactionRepository = TransactionRepository(application)
    fun insert(transaction: Transaction) {
        mNoteRepository.insert(transaction)
    }
    fun update(transaction: Transaction) {
        mNoteRepository.update(transaction)
    }
    fun delete(transaction: Transaction) {
        mNoteRepository.delete(transaction)
    }
    fun getAllTransaction(): LiveData<List<Transaction>> = mNoteRepository.getAllTransaction()
}