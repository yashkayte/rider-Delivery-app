package com.example.kk.models

data class Order(
    val orderId: String,
    val pickup: String,
    val drop: String,
    val customerName: String,
    val customerPhone: String,
    val distanceKm: Double,
    val earning: Int,
    val paymentType: String,
    val status: String
)