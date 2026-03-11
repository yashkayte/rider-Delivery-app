package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.adapters.OrdersAdapter
import com.example.kk.models.Order

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.rvOrders)
        rv.layoutManager = LinearLayoutManager(requireContext())

        // ✅ Dummy orders (professional list)
        val orders = listOf(
            Order(
                orderId = "KH1024",
                pickup = "Pizza Hut, Andheri West",
                drop = "Bandra East, Mumbai",
                customerName = "Rahul Sharma",
                customerPhone = "+919876543210",
                distanceKm = 4.2,
                earning = 75,
                paymentType = "COD",
                status = "NEW"
            ),
            Order(
                orderId = "KH1025",
                pickup = "McDonald's, Malad",
                drop = "Goregaon West",
                customerName = "Amit Verma",
                customerPhone = "+919812345678",
                distanceKm = 3.1,
                earning = 60,
                paymentType = "Online",
                status = "NEW"
            ),
            Order(
                orderId = "KH1026",
                pickup = "Domino's, Borivali",
                drop = "Kandivali East",
                customerName = "Neha Patel",
                customerPhone = "+919900112233",
                distanceKm = 5.6,
                earning = 90,
                paymentType = "Online",
                status = "PRIORITY"
            )
        )

        rv.adapter = OrdersAdapter(orders) { order ->
            val i = Intent(requireContext(), OrderDetailsActivity::class.java)
            i.putExtra("orderId", order.orderId)
            i.putExtra("pickup", order.pickup)
            i.putExtra("drop", order.drop)
            i.putExtra("customerName", order.customerName)
            i.putExtra("customerPhone", order.customerPhone)
            i.putExtra("distanceKm", order.distanceKm)
            i.putExtra("earning", order.earning)
            i.putExtra("paymentType", order.paymentType)
            startActivity(i)
        }

        return view
    }
}