package com.example.kk.models

data class Order(
    val orderId: String = "",
    val pickup: String = "",
    val drop: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val distanceKm: Double = 0.0,
    val earning: Int = 0,
    val paymentType: String = "",
    val status: String = ""
)