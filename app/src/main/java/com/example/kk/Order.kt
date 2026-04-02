package com.example.kk.models

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("orderId") val orderId: String = "",
    @SerializedName("pickupLocation") val pickupLocation: LocationData? = null,
    @SerializedName("dropLocation") val dropLocation: LocationData? = null,
    @SerializedName("customerName") val customerName: String = "",
    @SerializedName("customerPhone") val customerPhone: String = "",
    @SerializedName("distanceKm") val distanceKm: Double = 0.0,
    @SerializedName("earning") val earning: Int = 0,
    @SerializedName("paymentType") val paymentType: String = "",
    @SerializedName("status") val status: String = ""
)

data class LocationData(
    @SerializedName("address") val address: String = "",
    @SerializedName("lat") val lat: Double = 0.0,
    @SerializedName("lng") val lng: Double = 0.0
)
