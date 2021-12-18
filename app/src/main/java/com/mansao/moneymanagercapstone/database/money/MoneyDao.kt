package com.mansao.moneymanagercapstone.database.money

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyDao {
    // money
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoney(note: Money)

    @Update
    fun updateMoney(note: Money)

    @Delete
    fun deleteMoney(note: Money)

    @Query("SELECT * from money ORDER BY id ASC")
    fun getAllMoney(): LiveData<List<Money>>

    // transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransaction(transaction: Transaction)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * from `transaction` WHERE type_transaction = :typeTransaction ORDER BY id ASC")
    fun getAllTransaction(typeTransaction: String): LiveData<List<Transaction>>

    @Query("SELECT SUM(income) FROM `transaction` WHERE type_transaction = :typeTransaction ")
    fun getIncome(typeTransaction: String): LiveData<Int>

    @Query("SELECT SUM(outcome) FROM `transaction` WHERE type_transaction = :typeTransaction")
    fun getOutCome(typeTransaction: String): LiveData<Int>
}