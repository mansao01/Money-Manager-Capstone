package com.mansao.moneymanagercapstone.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mansao.moneymanagercapstone.R
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ItemNoteBinding
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity

class MoneyAdapter internal constructor(private val activity: Activity) : PagedListAdapter<Money, MoneyAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val binding = ItemNoteBinding.bind(view)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position) as Money)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(money: Money){
            with(binding){
                tvItemTitle.text = money.titleMoney
                tvItemDate.text = money.dateMoney
                tvItemDescription.text = money.descMoney
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, MoneyAddUpdateActivity::class.java)
                    intent.putExtra(MoneyAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(MoneyAddUpdateActivity.EXTRA_NOTE, money)
                    activity.startActivityForResult(intent, MoneyAddUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Money> = object : DiffUtil.ItemCallback<Money>() {
            override fun areItemsTheSame(oldNote: Money, newNote: Money): Boolean {
                return oldNote.titleMoney == newNote.titleMoney && oldNote.descMoney == newNote.descMoney
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: Money, newNote: Money): Boolean {
                return oldNote == newNote
            }
        }
    }
}