package com.example.kk.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        supportActionBar?.title = "Wallet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}