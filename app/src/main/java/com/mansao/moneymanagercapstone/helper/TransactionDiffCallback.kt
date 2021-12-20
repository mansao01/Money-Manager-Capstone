package com.mansao.moneymanagercapstone.helper.transaction

import androidx.recyclerview.widget.DiffUtil
import com.mansao.moneymanagercapstone.database.Transaction

class TransactionDiffCallback(
    private val mOldTransactionList: List<Transaction>,
    private val mNewTransactionList: List<Transaction>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldTransactionList.size
    }

    override fun getNewListSize(): Int {
        return mNewTransactionList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldTransactionList[oldItemPosition].id == mNewTransactionList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldTransactionList[oldItemPosition]
        val newEmployee = mNewTransactionList[newItemPosition]
        return oldEmployee.title_transaction == newEmployee.title_transaction && oldEmployee.desc_transaction == newEmployee.desc_transaction && oldEmployee.income == newEmployee.income && oldEmployee.outcome == newEmployee.outcome
    }
}