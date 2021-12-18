package com.mansao.moneymanagercapstone.helper.money

import androidx.recyclerview.widget.DiffUtil
import com.mansao.moneymanagercapstone.database.Money

class MoneyDiffCallback (private val mOldNoteList: List<Money>, private val mNewNoteList: List<Money>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }
    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.title_note == newEmployee.title_note && oldEmployee.desc_note == newEmployee.desc_note
    }
}