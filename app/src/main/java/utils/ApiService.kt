package utils

import com.example.kk.models.Order
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("orders")
    fun getOrders(): Call<List<Order>>
}