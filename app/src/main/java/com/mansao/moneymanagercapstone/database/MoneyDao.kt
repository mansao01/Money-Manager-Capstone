package com.mansao.moneymanagercapstone.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface MoneyDao {
    @Query("SELECT * FROM money")
    fun getAllData() : DataSource.Factory<Int, Money>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun deleteData(money: Money)
}