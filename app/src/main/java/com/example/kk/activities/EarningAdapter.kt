package com.example.kk.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R

class EarningAdapter(private val items: List<EarningItem>) :
    RecyclerView.Adapter<EarningAdapter.EarningVH>() {

    class EarningVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.txtDate)
        val deliveries: TextView = itemView.findViewById(R.id.txtOrdersCount)
        val amount: TextView = itemView.findViewById(R.id.txtAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_earning, parent, false)
        return EarningVH(view)
    }

    override fun onBindViewHolder(holder: EarningVH, position: Int) {
        val item = items[position]
        holder.date.text = item.date
        holder.deliveries.text = item.deliveries
        holder.amount.text = item.amount
    }

    override fun getItemCount(): Int = items.size
}