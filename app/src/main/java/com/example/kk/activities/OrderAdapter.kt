package com.example.kk.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class OrderAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val restaurant: TextView =
            itemView.findViewById(R.id.txtRestaurant)

        val pickup: TextView =
            itemView.findViewById(R.id.txtPickup)

        val drop: TextView =
            itemView.findViewById(R.id.txtDrop)

        val distance: TextView =
            itemView.findViewById(R.id.txtDistance)

        val amount: TextView =
            itemView.findViewById(R.id.txtAmount)

        val acceptBtn: MaterialButton =
            itemView.findViewById(R.id.btnAccept)

        val rejectBtn: MaterialButton =
            itemView.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)

        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        val order = orderList[position]

        holder.restaurant.text = order.restaurant
        holder.pickup.text = order.pickup
        holder.drop.text = order.drop
        holder.distance.text = "📍 ${order.distance}"
        holder.amount.text = order.amount

        holder.acceptBtn.setOnClickListener {

            Toast.makeText(
                holder.itemView.context,
                "Order Accepted: ${order.restaurant}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.rejectBtn.setOnClickListener {

            Toast.makeText(
                holder.itemView.context,
                "Order Rejected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {

        return orderList.size
    }
}