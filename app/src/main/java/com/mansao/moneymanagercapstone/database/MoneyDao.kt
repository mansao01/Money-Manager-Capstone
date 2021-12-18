package com.mansao.moneymanagercapstone.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface MoneyDao {
    // money
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoney(note: Money)

    @Update
    fun updateMoney(note: Money)

    @Delete
    fun deleteMoney(note: Money)

    @RawQuery(observedEntities = [Money::class])
    fun getAllMoney(query: SupportSQLiteQuery): DataSource.Factory<Int, Money>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Money>)

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