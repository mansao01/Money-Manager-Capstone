package com.mansao.moneymanagercapstone.database.transaction

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: Transaction)
    @Update
    fun update(transaction: Transaction)
    @Delete
    fun delete(transaction: Transaction)
    @Query("SELECT * from `transaction` ORDER BY id ASC")
    fun getAllTransaction(): LiveData<List<Transaction>>
}