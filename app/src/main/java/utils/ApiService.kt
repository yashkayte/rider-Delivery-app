package utils

import com.example.kk.models.Order
import com.example.kk.models.LoginRequest
import com.example.kk.models.AuthResponse
import com.example.kk.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("orders")
    fun getOrders(): Call<List<Order>>

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>
}
