package com.mansao.moneymanagercapstone.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class HomeViewModel (application: Application) : ViewModel() {
    private val moneyManagerRepository: MoneyManagerRepository = MoneyManagerRepository(application)
    fun getAllMoney(sort: String): LiveData<PagedList<Money>> {
        return LivePagedListBuilder(moneyManagerRepository.getAllMoney(sort), 20).build()
    }
}