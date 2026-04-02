package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.adapters.OrdersAdapter
import com.example.kk.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrdersFragment : Fragment() {

    private lateinit var adapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()
    
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var progressBar: ProgressBar
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var btnGenerateDemo: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        progressBar = view.findViewById(R.id.progressBar)
        layoutEmpty = view.findViewById(R.id.layoutEmpty)
        btnGenerateDemo = view.findViewById(R.id.btnGenerateDemo)

        val rv = view.findViewById<RecyclerView>(R.id.rvOrders)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = OrdersAdapter(ordersList) { order ->
            acceptOrder(order)
        }

        rv.adapter = adapter

        btnGenerateDemo.setOnClickListener {
            startActivity(Intent(requireContext(), TestActivity::class.java))
        }

        fetchOrdersFromFirestore()

        return view
    }

    private fun acceptOrder(order: Order) {
        val uid = auth.currentUser?.uid ?: return
        
        // Atomically assign rider and change status
        db.collection("orders").document(order.orderId)
            .update(
                "riderId", uid,
                "status", "ACTIVE"
            )
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Order Accepted! ✅", Toast.LENGTH_SHORT).show()
                val i = Intent(requireContext(), OrderDetailsActivity::class.java)
                i.putExtra("orderId", order.orderId)
                i.putExtra("pickup", order.pickupLocation?.address)
                i.putExtra("drop", order.dropLocation?.address)
                i.putExtra("customerName", order.customerName)
                i.putExtra("customerPhone", order.customerPhone)
                i.putExtra("distanceKm", order.distanceKm)
                i.putExtra("earning", order.earning)
                i.putExtra("paymentType", order.paymentType)
                startActivity(i)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to accept: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchOrdersFromFirestore() {
        progressBar.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE

        // Fetching "available" orders that don't have a rider assigned yet
        db.collection("orders")
            .whereEqualTo("status", "available")
            .addSnapshotListener { snapshots, e ->
                if (!isAdded) return@addSnapshotListener

                progressBar.visibility = View.GONE
                if (e != null) {
                    Log.e("Firestore", "Error fetching orders", e)
                    return@addSnapshotListener
                }

                ordersList.clear()
                if (snapshots != null) {
                    for (document in snapshots) {
                        val order = document.toObject(Order::class.java)
                        ordersList.add(order)
                    }
                }
                adapter.notifyDataSetChanged()

                if (ordersList.isEmpty()) {
                    layoutEmpty.visibility = View.VISIBLE
                } else {
                    layoutEmpty.visibility = View.GONE
                }
            }
    }
}
