package com.mansao.moneymanagercapstone.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate(): String{
        val dateFormat = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getWeek(): String{
        val dateFormat = SimpleDateFormat("ww-yyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getMonth(): String{
        val dateFormat = SimpleDateFormat("yyy-MM", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getYear(): String{
        val dateFormat = SimpleDateFormat("yyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}