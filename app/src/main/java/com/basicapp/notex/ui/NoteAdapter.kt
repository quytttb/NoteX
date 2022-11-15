package com.basicapp.notex.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basicapp.notex.R
import com.basicapp.notex.model.Note

class NoteAdapter(private val onItemClicked: (Note) -> Unit) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtTitle.text = item.noteTitle
        holder.txtTimeStamp.text = "Last Updated: " + item.timeStamp
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
    }

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var txtTitle: TextView = view.findViewById(R.id.txtTitle)
        var txtTimeStamp: TextView = view.findViewById(R.id.txtTime)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.noteContent == newItem.noteContent
            }
        }
    }
}


