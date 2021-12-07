package com.mansao.moneymanagercapstone.repository

import android.content.Context
import com.mansao.moneymanagercapstone.database.MoneyDao
import com.mansao.moneymanagercapstone.database.MoneyDatabase

class MoneyManagerRepository(private val moneyDao: MoneyDao ) {



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