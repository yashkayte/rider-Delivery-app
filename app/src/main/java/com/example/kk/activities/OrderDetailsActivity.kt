package com.example.kk.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val txtOrderId = findViewById<MaterialTextView>(R.id.txtOrderId)
        val txtPickup = findViewById<MaterialTextView>(R.id.txtPickup)
        val txtDrop = findViewById<MaterialTextView>(R.id.txtDrop)
        val txtCustomerName = findViewById<MaterialTextView>(R.id.txtCustomerName)
        val txtCustomerPhone = findViewById<MaterialTextView>(R.id.txtCustomerPhone)

        val btnCall = findViewById<MaterialButton>(R.id.btnCallCustomer)
        val btnNavigate = findViewById<MaterialButton>(R.id.btnStartNavigation)
        val btnPicked = findViewById<MaterialButton>(R.id.btnPickedUp)
        val btnDelivered = findViewById<MaterialButton>(R.id.btnDelivered)

        val orderId = intent.getStringExtra("orderId") ?: return
        val pickup = intent.getStringExtra("pickup") ?: "Pickup not found"
        val drop = intent.getStringExtra("drop") ?: "Drop not found"
        val customerName = intent.getStringExtra("customerName") ?: "Customer"
        val customerPhone = intent.getStringExtra("customerPhone") ?: "+910000000000"

        txtOrderId.text = "Order #$orderId"
        txtPickup.text = pickup
        txtDrop.text = drop
        txtCustomerName.text = customerName
        txtCustomerPhone.text = customerPhone

        btnCall.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:$customerPhone")
            startActivity(i)
        }

        btnNavigate.setOnClickListener {
            val gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${Uri.encode(drop)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(mapIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show()
            }
        }

        btnPicked.setOnClickListener {
            updateOrderStatus(orderId, "PICKED_UP")
        }

        btnDelivered.setOnClickListener {
            updateOrderStatus(orderId, "COMPLETED")
        }
    }

    private fun updateOrderStatus(orderId: String, status: String) {
        db.collection("orders").document(orderId)
            .update("status", status)
            .addOnSuccessListener {
                Toast.makeText(this, "Order Status: $status ✅", Toast.LENGTH_SHORT).show()
                if (status == "COMPLETED") finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
