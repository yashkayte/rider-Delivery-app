package com.example.kk.activities

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtAmount: TextView = view.findViewById(R.id.txtAmount)
        val imgType: ImageView = view.findViewById(R.id.imgType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.txtTitle.text = transaction.title
        holder.txtDate.text = transaction.date
        holder.txtAmount.text = if (transaction.isCredit) "+ ₹${transaction.amount}" else "- ₹${transaction.amount}"
        
        if (transaction.isCredit) {
            holder.txtAmount.setTextColor(Color.parseColor("#4CAF50"))
            holder.imgType.setImageResource(android.R.drawable.ic_menu_add)
            holder.imgType.setColorFilter(Color.parseColor("#4CAF50"))
            holder.imgType.setBackgroundColor(Color.parseColor("#E8F5E9"))
        } else {
            holder.txtAmount.setTextColor(Color.parseColor("#F44336"))
            holder.imgType.setImageResource(android.R.drawable.ic_menu_send)
            holder.imgType.setColorFilter(Color.parseColor("#F44336"))
            holder.imgType.setBackgroundColor(Color.parseColor("#FFEBEE"))
        }
    }

    override fun getItemCount() = transactions.size
}