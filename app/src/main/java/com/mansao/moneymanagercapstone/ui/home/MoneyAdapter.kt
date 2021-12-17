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
import com.mansao.moneymanagercapstone.helper.money.MoneyDiffCallback
import com.mansao.moneymanagercapstone.ui.addtask.money.MoneyAddUpdateActivity

class MoneyAdapter internal constructor(private val activity: Activity) : PagedListAdapter<Money, MoneyAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    private val listNotes = ArrayList<Money>()
    fun setListNotes(listNotes: List<Money>) {
        val diffCallback = MoneyDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val binding = ItemNoteBinding.bind(view)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position) as Money)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Money){
            with(binding){
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

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Money> = object : DiffUtil.ItemCallback<Money>() {
            override fun areItemsTheSame(oldNote: Money, newNote: Money): Boolean {
                return oldNote.title_note == newNote.title_note && oldNote.desc_note == newNote.desc_note
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: Money, newNote: Money): Boolean {
                return oldNote == newNote
            }
        }
    }
}