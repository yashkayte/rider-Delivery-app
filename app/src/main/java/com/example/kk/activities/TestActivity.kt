package com.example.kk.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.setPadding(32, 32, 32, 32)
        tv.textSize = 18f
        tv.text = "Adding real-life orders for Aurangabad..."
        setContentView(tv)

        val db = FirebaseFirestore.getInstance()

        val realOrders = listOf(
            mapOf(
                "orderId" to "AUR1001",
                "customerName" to "Aniket More",
                "customerPhone" to "9822334455",
                "distanceKm" to 4.2,
                "earning" to 65,
                "paymentType" to "Online",
                "status" to "available",
                "pickupLocation" to mapOf(
                    "address" to "Smokin' Joe's Pizza, Nirala Bazar",
                    "lat" to 19.8824,
                    "lng" to 75.3253
                ),
                "dropLocation" to mapOf(
                    "address" to "Shanti Niketan, N-1 CIDCO",
                    "lat" to 19.8735,
                    "lng" to 75.3582
                )
            ),
            mapOf(
                "orderId" to "AUR1002",
                "customerName" to "Snehal Rao",
                "customerPhone" to "7055441122",
                "distanceKm" to 2.8,
                "earning" to 40,
                "paymentType" to "COD",
                "status" to "available",
                "pickupLocation" to mapOf(
                    "address" to "Burger King, Prozone Mall",
                    "lat" to 19.8933,
                    "lng" to 75.3644
                ),
                "dropLocation" to mapOf(
                    "address" to "MGM Medical College Boys Hostel",
                    "lat" to 19.8801,
                    "lng" to 75.3541
                )
            ),
            mapOf(
                "orderId" to "AUR1003",
                "customerName" to "Dr. Pathak",
                "customerPhone" to "8899771122",
                "distanceKm" to 6.5,
                "earning" to 95,
                "paymentType" to "Online",
                "status" to "available",
                "pickupLocation" to mapOf(
                    "address" to "Hotel Rama International, Chikalthana",
                    "lat" to 19.8732,
                    "lng" to 75.3855
                ),
                "dropLocation" to mapOf(
                    "address" to "Government Arts & Science College, Killa Ark",
                    "lat" to 19.8902,
                    "lng" to 75.3188
                )
            ),
            mapOf(
                "orderId" to "AUR1004",
                "customerName" to "Yogesh Jadhav",
                "customerPhone" to "9112233445",
                "distanceKm" to 3.1,
                "earning" to 50,
                "paymentType" to "COD",
                "status" to "available",
                "pickupLocation" to mapOf(
                    "address" to "KFC, Connaught Place",
                    "lat" to 19.8738,
                    "lng" to 75.3622
                ),
                "dropLocation" to mapOf(
                    "address" to "Seven Hills Flyover, Jalna Road",
                    "lat" to 19.8712,
                    "lng" to 75.3445
                )
            ),
            mapOf(
                "orderId" to "AUR1005",
                "customerName" to "Meera Deshpande",
                "customerPhone" to "8446655443",
                "distanceKm" to 1.5,
                "earning" to 30,
                "paymentType" to "Online",
                "status" to "available",
                "pickupLocation" to mapOf(
                    "address" to "Saraswati Bhuvan College canteen",
                    "lat" to 19.8785,
                    "lng" to 75.3212
                ),
                "dropLocation" to mapOf(
                    "address" to "Kranti Chowk Bus Stop",
                    "lat" to 19.8732,
                    "lng" to 75.3268
                )
            )
        )

        var successCount = 0
        val total = realOrders.size

        realOrders.forEach { order ->
            db.collection("orders").document(order["orderId"] as String)
                .set(order)
                .addOnSuccessListener {
                    successCount++
                    tv.text = "Adding $successCount/$total real-life orders for Aurangabad..."
                    if (successCount == total) {
                        tv.text = "Success! ✅\n\n5 Real-life orders added with actual Aurangabad landmarks (Prozone, Nirala Bazar, CIDCO, etc.).\n\nGo back and check the 'Orders' tab."
                    }
                }
                .addOnFailureListener { e ->
                    tv.text = "Error: ${e.message}"
                }
        }
    }
}
