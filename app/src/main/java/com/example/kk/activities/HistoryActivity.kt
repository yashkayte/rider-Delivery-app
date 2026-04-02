package com.example.kk.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: HistoryAdapter
    private val historyList = mutableListOf<Order>()

    private lateinit var progressBar: ProgressBar
    private lateinit var txtEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        progressBar = findViewById(R.id.progressBar)
        txtEmpty = findViewById(R.id.txtEmpty)
        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)

        adapter = HistoryAdapter(historyList)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = adapter

        fetchHistory()
    }

    private fun fetchHistory() {
        val user = auth.currentUser ?: return

        progressBar.visibility = View.VISIBLE
        txtEmpty.visibility = View.GONE

        db.collection("orders")
            .whereEqualTo("riderId", user.uid)
            .whereEqualTo("status", "COMPLETED")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("HistoryActivity", "Successfully fetched ${documents.size()} documents")
                progressBar.visibility = View.GONE
                historyList.clear()
                for (document in documents) {
                    try {
                        val order = document.toObject(Order::class.java)
                        historyList.add(order)
                    } catch (e: Exception) {
                        Log.e("HistoryActivity", "Error parsing order: ${document.id}", e)
                    }
                }
                adapter.notifyDataSetChanged()

                if (historyList.isEmpty()) {
                    txtEmpty.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                Log.e("HistoryActivity", "Error fetching history", e)
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
