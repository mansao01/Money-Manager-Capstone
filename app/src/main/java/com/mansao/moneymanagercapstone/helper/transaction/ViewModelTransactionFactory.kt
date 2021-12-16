package com.mansao.moneymanagercapstone.helper.transaction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateViewModel
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateViewModel

class ViewModelTransactionFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelTransactionFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelTransactionFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelTransactionFactory::class.java) {
                    INSTANCE = ViewModelTransactionFactory(application)
                }
            }
            return INSTANCE as ViewModelTransactionFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoneyAddUpdateViewModel::class.java)) {
            return MoneyAddUpdateViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(TransactionAddUpdateViewModel::class.java)) {
            return TransactionAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}