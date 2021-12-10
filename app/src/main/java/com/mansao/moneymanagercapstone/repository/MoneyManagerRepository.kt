package com.mansao.moneymanagercapstone.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.database.MoneyDao
import com.mansao.moneymanagercapstone.database.MoneyDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoneyManagerRepository(application: Application) {
    private val mNotesDao: MoneyDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = MoneyDatabase.getDatabase(application)
        mNotesDao = db.moneyDao()
    }
    fun getAllNotes(): LiveData<List<Money>> = mNotesDao.getAllNotes()
    fun insert(note: Money) {
        executorService.execute { mNotesDao.insert(note) }
    }
    fun delete(note: Money) {
        executorService.execute { mNotesDao.delete(note) }
    }
    fun update(note: Money) {
        executorService.execute { mNotesDao.update(note) }
    }
}