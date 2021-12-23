package com.mansao.moneymanagercapstone.ui.addtask.money

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mansao.moneymanagercapstone.database.Transaction
import com.mansao.moneymanagercapstone.databinding.ListItemsBinding
import com.mansao.moneymanagercapstone.helper.transaction.TransactionDiffCallback
import com.mansao.moneymanagercapstone.ui.addtask.transaction.TransactionAddUpdateActivity

class TransactionAdapter internal constructor(private val activity: Activity) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private val listTransaction = ArrayList<Transaction>()

    fun setListTransaction(listTransaction: List<Transaction>) {
        val diffCallback = TransactionDiffCallback(this.listTransaction, listTransaction)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listTransaction.clear()
        this.listTransaction.addAll(listTransaction)
        this.notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(listTransaction[position])
    }

    override fun getItemCount(): Int {
        return listTransaction.size
    }

    inner class TransactionViewHolder(private val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            with(binding) {
                tvItemTitle.text = transaction.title_transaction
                tvItemDate.text = transaction.date_transaction
                tvItemDescription.text = transaction.desc_transaction
                tvItemIncome.text = "+ ${transaction.income}"
                tvItemOutcome.text = "- ${transaction.outcome}"
                cvItemTransaction.setOnClickListener {
                    val intent = Intent(activity, TransactionAddUpdateActivity::class.java)
                    intent.putExtra(TransactionAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(TransactionAddUpdateActivity.EXTRA_NOTE, transaction)
                    activity.startActivityForResult(intent, TransactionAddUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }
}