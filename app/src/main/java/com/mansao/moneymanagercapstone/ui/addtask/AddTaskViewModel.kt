package com.mansao.moneymanagercapstone.ui.addtask

import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class AddTaskViewModel(private val moneyManagerRepository: MoneyManagerRepository): ViewModel() {
    fun insertData(money: Money){
        moneyManagerRepository.insertData(money)
    }
}