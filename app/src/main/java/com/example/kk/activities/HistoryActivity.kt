package com.example.kk.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.models.Order

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)

        // Setup RecyclerView with dummy history data
        val historyOrders = listOf(
            Order("5521", "Pizza Hut, Andheri", "Bandra East", "John Doe", "9988776655", 4.2, 120, "COD", "Completed"),
            Order("5498", "Burger King, Sakinaka", "Powai", "Jane Smith", "8877665544", 6.5, 185, "Paid", "Completed"),
            Order("5482", "Subway, Kurla", "Chembur", "Mike Ross", "7766554433", 3.1, 95, "Paid", "Cancelled"),
            Order("5471", "Domino's, Ghatkopar", "Vikhroli", "Sarah Jenkins", "6655443322", 5.8, 150, "Paid", "Completed"),
            Order("5465", "KFC, Sion", "Dadra", "Harvey Specter", "5544332211", 8.2, 220, "COD", "Completed")
        )

        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = HistoryAdapter(historyOrders)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}