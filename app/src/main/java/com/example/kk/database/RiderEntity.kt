package com.example.kk.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "riders")
data class RiderEntity(
    @PrimaryKey val riderId: String,
    val name: String,
    val phone: String,
    val email: String,
    val totalEarnings: Double = 0.0,
    val totalOrders: Int = 0,
    val rating: Float = 5.0f,
    val isActive: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)