package com.route.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.notesapp.DataBase.Model.Note


/**
 * Created by Mohamed Nabil Mohamed on 2/14/2020.
 * m.nabil.fci2015@gmail.com
 */
class NotesAdapter(var notes:MutableList<Note>?):
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var recentlyDeletedItem: Note? = null
    private var recentlyDeletedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note,parent,false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes?.get(position)
        holder.title.setText(note?.title)
        holder.date.setText(note?.time)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position, note)
        }
    }

    fun changeData(list: List<Note>?){
        this.notes= list as MutableList
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        recentlyDeletedItemPosition = position
        recentlyDeletedItem = notes?.get(position)
        notes?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoDelete(){

        notes?.add(recentlyDeletedItemPosition, recentlyDeletedItem!!)
        notifyItemInserted(recentlyDeletedItemPosition)

        recentlyDeletedItem = null
        recentlyDeletedItemPosition = -1

    }

    fun getRecentlyDeletedItem(): Note?{
        return recentlyDeletedItem
    }

    lateinit var onItemClickListener:OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int,note: Note?)
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var title:TextView
        var date:TextView
        init {
            title=itemView.findViewById(R.id.title)
            date=itemView.findViewById(R.id.date)
        }
    }
}