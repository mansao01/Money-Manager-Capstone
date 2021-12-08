package com.mansao.moneymanagercapstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ListItemsBinding
import com.mansao.moneymanagercapstone.repository.MoneyManagerRepository

class HomeAdapter(private val moneyManagerRepository: MoneyManagerRepository): PagedListAdapter<Money, HomeAdapter.MoneyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        val view = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoneyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MoneyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item)
        }
    }

    inner class MoneyViewHolder(private val binding: ListItemsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(money: Money){
            with(binding){
                tvItemTitle.text = money.title
                tvItemDate.text = money.date
                tvItemDescription.text = money.description
                tvItemOutcome.text = money.outcome.toString()
                tvItemIncome.text = money.income.toString()

                imgDelete.setOnClickListener {
                    moneyManagerRepository.deleteData(money)
                }

            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Money>(){
            override fun areItemsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Money, newItem: Money): Boolean {
                return oldItem == newItem
            }

        }
    }
}