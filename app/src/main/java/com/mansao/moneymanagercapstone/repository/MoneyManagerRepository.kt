package com.mansao.moneymanagercapstone.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mansao.moneymanagercapstone.database.money.Money
import com.mansao.moneymanagercapstone.database.money.MoneyDao
import com.mansao.moneymanagercapstone.database.money.MoneyDatabase
import com.mansao.moneymanagercapstone.database.money.Transaction
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
    fun getAllMoney(): LiveData<List<Money>> = moneyDao.getAllMoney()

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

    fun getAllTransaction(): LiveData<List<Transaction>> = moneyDao.getAllTransaction()

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