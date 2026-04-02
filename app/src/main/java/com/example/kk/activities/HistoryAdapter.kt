package com.example.kk.activities

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.models.Order

class HistoryAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtOrderId: TextView = view.findViewById(R.id.txtOrderId)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
        val txtPickup: TextView = view.findViewById(R.id.txtPickup)
        val txtDrop: TextView = view.findViewById(R.id.txtDrop)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtEarning: TextView = view.findViewById(R.id.txtEarning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val order = orders[position]
        holder.txtOrderId.text = "Order #${order.orderId}"
        holder.txtStatus.text = order.status.uppercase()
        holder.txtPickup.text = "📍 Pickup: ${order.pickupLocation?.address ?: "N/A"}"
        holder.txtDrop.text = "🏁 Drop: ${order.dropLocation?.address ?: "N/A"}"
        holder.txtEarning.text = "₹${order.earning}"
        
        // Using a dummy date for now as it's not in the model
        holder.txtDate.text = "24 Oct 2024 • 12:30 PM"

        if (order.status.lowercase() == "cancelled") {
            holder.txtStatus.setTextColor(Color.parseColor("#F44336"))
            holder.txtStatus.setBackgroundColor(Color.parseColor("#FFEBEE"))
        } else {
            holder.txtStatus.setTextColor(Color.parseColor("#4CAF50"))
            holder.txtStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
        }
    }

    override fun getItemCount() = orders.size
}
