package com.example.kk.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerOrders)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val orderList: List<Order> = listOf(
            Order("Pizza Hut", "Andheri West", "Bandra East", "2.3 km", "₹120"),
            Order("McDonald's", "Dadar", "Lower Parel", "3.1 km", "₹95"),
            Order("Domino's", "Malad", "Goregaon", "1.8 km", "₹140"),
            Order("Subway", "Powai", "Vikhroli", "4.5 km", "₹160")
        )

        recyclerView.adapter = OrderAdapter(orderList)
    }
}