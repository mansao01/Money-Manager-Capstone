package com.mansao.moneymanagercapstone.ui.addtask.money

import android.app.Application
import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.money.Money
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class MoneyAddUpdateViewModel (application: Application) : ViewModel() {
    private val mNoteRepository: MoneyManagerRepository = MoneyManagerRepository(application)
    fun insert(note: Money) {
        mNoteRepository.insert(note)
    }
    fun update(note: Money) {
        mNoteRepository.update(note)
    }
    fun delete(note: Money) {
        mNoteRepository.delete(note)
    }
}