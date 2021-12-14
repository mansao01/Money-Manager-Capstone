package com.mansao.moneymanagercapstone.ui.addtransacation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mansao.moneymanagercapstone.databinding.ActivityAddTransactionBinding

class AddTransaction : AppCompatActivity() {
    private lateinit var binding : ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}