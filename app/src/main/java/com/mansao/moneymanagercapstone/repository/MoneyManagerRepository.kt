package com.mansao.moneymanagercapstone.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.database.MoneyDao
import com.mansao.moneymanagercapstone.database.MoneyDatabase

class MoneyManagerRepository(private val moneyDao: MoneyDao ) {

    fun getAllData(): LiveData<PagedList<Money>>{
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(moneyDao.getAllData(), config).build()
    }

    fun insertData(money: Money){
        moneyDao.insertData(money)
    }

    fun deleteData(money: Money){
        return moneyDao.deleteData(money)
    }

    companion object{
        @Volatile
        private var instance : MoneyManagerRepository? = null

        fun getInstance(context: Context): MoneyManagerRepository{
            return instance?: synchronized(this){
                if (instance == null){
                    val database = MoneyDatabase.getInstance(context)
                    instance = MoneyManagerRepository(database.moneyDao())
                }
                return instance as MoneyManagerRepository
            }
        }

    }
}