package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        val rvTransactions = findViewById<RecyclerView>(R.id.rvTransactions)
        val btnWithdraw = findViewById<MaterialButton>(R.id.btnWithdraw)

        // Setup RecyclerView
        val transactions = listOf(
            Transaction("1", "Order #5521", "24 Oct, 12:45 PM", "120.00", true),
            Transaction("2", "Withdrawal to Bank", "23 Oct, 10:15 AM", "1500.00", false),
            Transaction("3", "Order #5498", "23 Oct, 08:30 AM", "85.00", true),
            Transaction("4", "Order #5482", "22 Oct, 09:20 PM", "210.00", true),
            Transaction("5", "Penalty: Late Delivery", "22 Oct, 02:10 PM", "50.00", false)
        )

        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = TransactionAdapter(transactions)

        btnWithdraw.setOnClickListener {
            startActivity(Intent(this, WithdrawActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}