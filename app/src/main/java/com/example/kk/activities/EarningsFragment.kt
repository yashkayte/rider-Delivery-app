package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.models.Order
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EarningsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var txtTotalEarnings: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_earnings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        txtTotalEarnings = view.findViewById(R.id.txtTotalEarnings)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEarnings)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        loadEarningsData(recycler)

        val btnWithdraw = view.findViewById<MaterialButton>(R.id.btnWithdraw)
        btnWithdraw.setOnClickListener {
            val i = Intent(requireContext(), WithdrawActivity::class.java)
            startActivity(i)
        }
    }

    private fun loadEarningsData(recycler: RecyclerView) {
        val uid = auth.currentUser?.uid ?: return

        db.collection("orders")
            .whereEqualTo("riderId", uid)
            .whereEqualTo("status", "COMPLETED")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                var total = 0
                val earningItems = mutableListOf<EarningItem>()
                
                for (doc in documents) {
                    val order = doc.toObject(Order::class.java)
                    total += order.earning
                    
                    // Simple date placeholder for now
                    earningItems.add(EarningItem("Completed Order", "1 delivery", "₹${order.earning}"))
                }
                
                txtTotalEarnings.text = "₹$total"
                
                if (earningItems.isEmpty()) {
                    // Fallback to sample data if no completed orders for demo/testing
                    val sampleItems = listOf(
                        EarningItem("25 Feb 2026", "8 deliveries", "₹1,250"),
                        EarningItem("24 Feb 2026", "6 deliveries", "₹980"),
                        EarningItem("23 Feb 2026", "7 deliveries", "₹1,110"),
                        EarningItem("22 Feb 2026", "5 deliveries", "₹850")
                    )
                    recycler.adapter = EarningAdapter(sampleItems)
                } else {
                    recycler.adapter = EarningAdapter(earningItems)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Earnings", "Failed to load earnings", e)
            }
    }
}
