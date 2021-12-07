package com.mansao.moneymanagercapstone.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)

    @Query("SELECT * FROM money")
    fun getAllRecord() : LiveData<List<Money>>
}