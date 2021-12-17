package com.mansao.moneymanagercapstone.database

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

    @RawQuery(observedEntities = [Transaction::class])
    fun getAllTransaction(query: SupportSQLiteQuery): DataSource.Factory<Int, Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTransaction(list: List<Transaction>)

//    @Query("SELECT SUM(income) FROM `transaction` WHERE type_transaction = :typeTransaction ")
//    fun getIncome(typeTransaction: String)
//
//    @Query("SELECT SUM(outcome) FROM `transaction` WHERE type_transaction = :typeTransaction")
//    fun getOutCome(typeTransaction: String)
}