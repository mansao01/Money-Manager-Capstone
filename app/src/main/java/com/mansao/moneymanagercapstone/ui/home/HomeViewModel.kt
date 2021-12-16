package com.mansao.moneymanagercapstone.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mansao.moneymanagercapstone.database.money.Money
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class HomeViewModel (application: Application) : ViewModel() {
    private val mNoteRepository: MoneyManagerRepository = MoneyManagerRepository(application)
    fun getAllNotes(): LiveData<List<Money>> = mNoteRepository.getAllNotes()
}