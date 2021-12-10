package com.mansao.moneymanagercapstone.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoneyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Money)
    @Update
    fun update(note: Money)
    @Delete
    fun delete(note: Money)
    @Query("SELECT * from money ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Money>>
}