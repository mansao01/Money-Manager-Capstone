package com.mansao.moneymanagercapstone.database.money

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Money::class], version = 1)
abstract class MoneyDatabase : RoomDatabase() {
    abstract fun moneyDao(): MoneyDao

    companion object{
        @Volatile
        private var INSTANCE: MoneyDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MoneyDatabase {
            if (INSTANCE == null) {
                synchronized(MoneyDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MoneyDatabase::class.java, "money_database")
                        .build()
                }
            }
            return INSTANCE as MoneyDatabase
        }
    }
}