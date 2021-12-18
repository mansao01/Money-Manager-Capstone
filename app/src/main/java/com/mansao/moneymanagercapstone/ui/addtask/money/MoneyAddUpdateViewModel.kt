package com.mansao.moneymanagercapstone.ui.addtask.money

import android.app.Application
import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.money.Money
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class MoneyAddUpdateViewModel (application: Application) : ViewModel() {
    private val moneyManagerRepository: MoneyManagerRepository = MoneyManagerRepository(application)
    fun insert(money: Money) {
        moneyManagerRepository.insertMoney(money)
    }
    fun update(money: Money) {
        moneyManagerRepository.updateMoney(money)
    }
    fun delete(money: Money) {
        moneyManagerRepository.deleteMoney(money)
    }
}