package com.example.kk.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val pickupAddress: String,
    val pickupLat: Double,
    val pickupLng: Double,
    val dropAddress: String,
    val dropLat: Double,
    val dropLng: Double,
    val customerName: String,
    val customerPhone: String,
    val distanceKm: Double,
    val earning: Int,
    val paymentType: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)