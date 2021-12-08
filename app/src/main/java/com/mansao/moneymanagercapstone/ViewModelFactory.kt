package com.mansao.moneymanagercapstone

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository
import com.mansao.moneymanagercapstone.ui.addtask.AddTaskViewModel

class ViewModelFactory private constructor(private val moneyManagerRepository: MoneyManagerRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(AddTaskViewModel::class.java) ->{
                AddTaskViewModel(moneyManagerRepository) as T
            }
            else -> throw Throwable("Something wrong i can feel it with class:" + modelClass.name)

        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    MoneyManagerRepository.getInstance(context)
                )
            }
    }
}