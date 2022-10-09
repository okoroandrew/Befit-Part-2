package com.example.befit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private var mItems: List<SleepFeeling>): RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feelingDate = itemView.findViewById<TextView>(R.id.date)
        val hoursSlept = itemView.findViewById<TextView>(R.id.hours_slept_in_viewholder)
        val extraNote = itemView.findViewById<TextView>(R.id.extra_note)
        val feelingRate = itemView.findViewById<TextView>(R.id.feeling_fraction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val myItemView = inflater.inflate(R.layout.item_viewholder, parent, false)
        return ViewHolder(myItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: SleepFeeling = mItems[position]

        holder.hoursSlept.text = item.sleepHours
        holder.extraNote.text = item.note
        holder.feelingRate.text = item.feeling+"/10"
        holder.feelingDate.text = item.date
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}