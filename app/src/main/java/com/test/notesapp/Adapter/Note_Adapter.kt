package com.test.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.notesapp.Interface.Click_Interface
import com.test.notesapp.R
import com.test.notesapp.Utils.Common
import com.test.notesapp.Entity.Note
import kotlinx.android.synthetic.main.item_note.view.*


class Note_Adapter(val context: Context, var list:ArrayList<Note>,var insertInterface: Click_Interface) : RecyclerView.Adapter<Note_Adapter.ViewHolder>() {



    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        val tv_item_date=itemView.tv_item_date
        val tv_item_title=itemView.tv_item_title
         val tv_item_description=itemView.tv_item_description
         val iv_delete=itemView.iv_delete


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_item_date.setText(list.get(position).date)

         holder.tv_item_date.setText(
             Common.getTimeAgo(
                 list.get(position).date.toLong(),
                 context
             ))

        holder.tv_item_title.setText(list.get(position).title)
        holder.tv_item_description.setText(list.get(position).description)

        holder.itemView.setOnClickListener {
            insertInterface.on_itemClick(list.get(position),position)
        }

     holder.iv_delete.setOnClickListener {
            insertInterface.on_itemDelete(list.get(position),position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}