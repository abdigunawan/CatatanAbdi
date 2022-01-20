package com.abdigunawan.catatanabdi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val notes: ArrayList<NotesModel.Data>, val listener: OnAdapterListener):RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent,false)
    )

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val data = notes[position]
        holder.textJudul.text = data.judul
        holder.textCatatan.text = data.catatan

        holder.itemView.setOnClickListener {
            listener.onUpdate( data )
        }
    }

    override fun getItemCount() = notes.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textJudul = view.findViewById<TextView>(R.id.tvJudul)
        val textCatatan = view.findViewById<TextView>(R.id.tvCatatan)
    }

    public fun setData(data: List<NotesModel.Data>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()

    }

    interface OnAdapterListener {
        fun onUpdate(note : NotesModel.Data)
    }
}