package com.mansao.moneymanagercapstone.repository

import android.app.Application
import androidx.paging.DataSource
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.database.MoneyDao
import com.mansao.moneymanagercapstone.database.MoneyDatabase
import com.mansao.moneymanagercapstone.database.Transaction
import com.mansao.moneymanagercapstone.helper.SortUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoneyManagerRepository(application: Application) {
    private val moneyDao: MoneyDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = MoneyDatabase.getDatabase(application)
        moneyDao = db.moneyDao()
    }


    // money
    fun getAllMoney(sort: String): DataSource.Factory<Int, Money> {
        val query = SortUtils.getSortedQuery(sort)
        return moneyDao.getAllMoney(query)
    }

    fun insertMoney(note: Money) {
        executorService.execute { moneyDao.insertMoney(note) }
    }
    fun deleteMoney(note: Money) {
        executorService.execute { moneyDao.deleteMoney(note) }
    }
    fun updateMoney(note: Money) {
        executorService.execute { moneyDao.updateMoney(note) }
    }

    //transaction

    fun getAllTransaction(sort: String): DataSource.Factory<Int, Transaction> {
        val query = SortUtils.getSortedTransaction(sort)
        return moneyDao.getAllTransaction(query)
    }

    fun insertTransaction(transaction: Transaction){
        executorService.execute {
            moneyDao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction){
        executorService.execute {
            moneyDao.deleteTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction){
        executorService.execute {
            moneyDao.updateTransaction(transaction)
        }
    }
}