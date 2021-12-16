package com.mansao.moneymanagercapstone.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mansao.moneymanagercapstone.database.transaction.Transaction
import com.mansao.moneymanagercapstone.database.transaction.TransactionDao
import com.mansao.moneymanagercapstone.database.transaction.TransactionDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionRepository (application: Application) {
    private val mTransactionDao: TransactionDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = TransactionDatabase.getDatabase(application)
        mTransactionDao = db.TransactionDao()
    }
    fun getAllTransaction(): LiveData<List<Transaction>> = mTransactionDao.getAllTransaction()
    fun insert(transaction: Transaction) {
        executorService.execute { mTransactionDao.insert(transaction) }
    }
    fun delete(transaction: Transaction) {
        executorService.execute { mTransactionDao.delete(transaction) }
    }
    fun update(transaction: Transaction) {
        executorService.execute { mTransactionDao.update(transaction) }
    }
}