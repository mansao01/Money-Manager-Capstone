package com.mansao.moneymanagercapstone.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mansao.moneymanagercapstone.database.Money
import com.mansao.moneymanagercapstone.databinding.ItemNoteBinding
import com.mansao.moneymanagercapstone.helper.MoneyDiffCallback
import com.mansao.moneymanagercapstone.ui.addtask.MoneyAddUpdateActivity

class MoneyAdapter internal constructor(private val activity: Activity) : RecyclerView.Adapter<MoneyAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Money>()
    fun setListNotes(listNotes: List<Money>) {
        val diffCallback = MoneyDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Money) {
            with(binding) {
                tvItemTitle.text = note.title_note
                tvItemDate.text = note.date_note
                tvItemDescription.text = note.desc_note
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, MoneyAddUpdateActivity::class.java)
                    intent.putExtra(MoneyAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(MoneyAddUpdateActivity.EXTRA_NOTE, note)
                    activity.startActivityForResult(intent, MoneyAddUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }
}