package com.example.kk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.models.Order
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class OrdersAdapter(
    private val items: List<Order>,
    private val onAccept: (Order) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtOrderId: TextView = v.findViewById(R.id.txtOrderId)
        val chipStatus: Chip = v.findViewById(R.id.chipStatus)
        val txtPickup: TextView = v.findViewById(R.id.txtPickup)
        val txtDrop: TextView = v.findViewById(R.id.txtDrop)
        val txtMeta: TextView = v.findViewById(R.id.txtMeta)
        val txtEarning: TextView = v.findViewById(R.id.txtEarning)
        val btnAccept: MaterialButton = v.findViewById(R.id.btnAccept)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val o = items[position]
        holder.txtOrderId.text = "Order #${o.orderId}"
        holder.chipStatus.text = o.status

        holder.txtPickup.text = o.pickupLocation?.address ?: "No Address"
        holder.txtDrop.text = o.dropLocation?.address ?: "No Address"

        holder.txtMeta.text = "Distance: ${o.distanceKm} km  •  ${o.paymentType}"
        holder.txtEarning.text = "₹${o.earning}"

        holder.btnAccept.setOnClickListener { onAccept(o) }
    }

    override fun getItemCount(): Int = items.size
}
