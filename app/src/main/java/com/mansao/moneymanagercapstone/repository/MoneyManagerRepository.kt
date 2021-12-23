package com.mansao.moneymanagercapstone.repository

import android.app.Application
import androidx.lifecycle.LiveData
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

    fun getAllTransaction(typeTransaction: String): LiveData<List<Transaction>> =
        moneyDao.getAllTransaction(typeTransaction)

    fun getTodayTransaction(typeTransaction: String, today: String): LiveData<List<Transaction>> =
        moneyDao.getTodayTransaction(typeTransaction, today)

    fun getWeekTransaction(typeTransaction: String, week: String): LiveData<List<Transaction>> =
        moneyDao.getWeekTransaction(typeTransaction, week)

    fun getMonthTransaction(typeTransaction: String, month: String): LiveData<List<Transaction>> =
        moneyDao.getMonthTransaction(typeTransaction, month)

    fun getYearTransaction(typeTransaction: String, year: String): LiveData<List<Transaction>> =
        moneyDao.getYearTransaction(typeTransaction, year)

    fun insertTransaction(transaction: Transaction) {
        executorService.execute {
            moneyDao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        executorService.execute {
            moneyDao.deleteTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        executorService.execute { moneyDao.updateTransaction(transaction)
        }
    }

    fun getOutcome(typeTransaction: String): LiveData<Int> = moneyDao.getOutCome(typeTransaction)

    fun getIncome(typeTransaction: String): LiveData<Int> = moneyDao.getIncome(typeTransaction)

    fun getTotalMoney(typeTransaction: String): LiveData<Int> =
        moneyDao.getTotalMoney(typeTransaction)
}
