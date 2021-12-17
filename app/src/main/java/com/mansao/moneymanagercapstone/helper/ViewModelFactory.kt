package com.mansao.capstonedraft.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateViewModel
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel
import com.mansao.moneymanagercapstone.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(MoneyAddUpdateViewModel::class.java) -> {
                MoneyAddUpdateViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(TransactionAddUpdateViewModel::class.java) -> {
                TransactionAddUpdateViewModel(mApplication) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}