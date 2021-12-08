package com.mansao.moneymanagercapstone.ui.addtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mansao.moneymanagercapstone.ViewModelFactory
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ActivityAddTaskBinding
import com.mansao.moneymanagercapstone.helper.DateHelper

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel

    private var money: Money? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]


        binding.btnAddTask.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val description = binding.edtDescription.text.toString()
            val outcome = binding.edtOutCome.text.toString()
            val income = binding.edtInCome.text.toString()
            val date = DateHelper.getCurrentDate()

            money.let { money ->
                money?.title = title
                money?.description = description
                money?.outcome = outcome.toInt()
                money?.income = income.toInt()
                money?.date = date
            }
            viewModel.insertData(money as Money)
        }

    }
}