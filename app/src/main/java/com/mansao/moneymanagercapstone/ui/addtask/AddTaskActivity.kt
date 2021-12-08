package com.mansao.moneymanagercapstone.ui.addtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.ViewModelFactory
import com.mansao.moneymanagercapstone.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]
    }
}