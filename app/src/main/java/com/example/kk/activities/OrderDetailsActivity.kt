package com.example.kk.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class OrderDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val txtOrderId = findViewById<MaterialTextView>(R.id.txtOrderId)
        val txtPickup = findViewById<MaterialTextView>(R.id.txtPickup)
        val txtDrop = findViewById<MaterialTextView>(R.id.txtDrop)
        val txtCustomerName = findViewById<MaterialTextView>(R.id.txtCustomerName)
        val txtCustomerPhone = findViewById<MaterialTextView>(R.id.txtCustomerPhone)

        val btnCall = findViewById<MaterialButton>(R.id.btnCallCustomer)
        val btnNavigate = findViewById<MaterialButton>(R.id.btnStartNavigation)
        val btnPicked = findViewById<MaterialButton>(R.id.btnPickedUp)
        val btnDelivered = findViewById<MaterialButton>(R.id.btnDelivered)

        // ✅ Get Data from Intent (from Home or Orders)
        val orderId = intent.getStringExtra("orderId") ?: "KH-0000"
        val pickup = intent.getStringExtra("pickup") ?: "Pickup not found"
        val drop = intent.getStringExtra("drop") ?: "Drop not found"
        val customerName = intent.getStringExtra("customerName") ?: "Customer"
        val customerPhone = intent.getStringExtra("customerPhone") ?: "+910000000000"

        txtOrderId.text = "Order #$orderId"
        txtPickup.text = pickup
        txtDrop.text = drop
        txtCustomerName.text = customerName
        txtCustomerPhone.text = customerPhone

        // ✅ Call button
        btnCall.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:$customerPhone")
            startActivity(i)
        }

        // ✅ Navigation button (Google Maps)
        btnNavigate.setOnClickListener {
            // open directions from pickup -> drop
            val gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${Uri.encode(drop)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            try {
                startActivity(mapIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Pickup status
        btnPicked.setOnClickListener {
            Toast.makeText(this, "Marked as Picked Up ✅", Toast.LENGTH_SHORT).show()
        }

        // ✅ Delivered status
        btnDelivered.setOnClickListener {
            Toast.makeText(this, "Marked as Delivered ✅", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}