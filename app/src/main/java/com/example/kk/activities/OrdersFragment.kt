package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.example.kk.adapters.OrdersAdapter
import com.example.kk.models.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.RetrofitClient

class OrdersFragment : Fragment() {

    private lateinit var adapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()

    private lateinit var progressBar: ProgressBar
    private lateinit var layoutEmpty: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        layoutEmpty = view.findViewById(R.id.layoutEmpty)

        val rv = view.findViewById<RecyclerView>(R.id.rvOrders)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = OrdersAdapter(ordersList) { order ->
            val i = Intent(requireContext(), OrderDetailsActivity::class.java)
            i.putExtra("orderId", order.orderId)
            i.putExtra("pickup", order.pickup)
            i.putExtra("drop", order.drop)
            i.putExtra("customerName", order.customerName)
            i.putExtra("customerPhone", order.customerPhone)
            i.putExtra("distanceKm", order.distanceKm)
            i.putExtra("earning", order.earning)
            i.putExtra("paymentType", order.paymentType)
            startActivity(i)
        }

        rv.adapter = adapter

        fetchOrdersFromApi()   // ✅ ONLY API CALL

        return view
    }

    // ✅ MAIN BACKEND CONNECTION
    private fun fetchOrdersFromApi() {

        progressBar.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE

        RetrofitClient.api.getOrders().enqueue(object : Callback<List<Order>> {

            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body() != null) {

                    ordersList.clear()
                    ordersList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                    if (ordersList.isEmpty()) {
                        layoutEmpty.visibility = View.VISIBLE
                    } else {
                        layoutEmpty.visibility = View.GONE
                    }

                    Log.d("API_SUCCESS", ordersList.toString())

                } else {
                    layoutEmpty.visibility = View.VISIBLE
                    Log.e("API_ERROR", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                progressBar.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
                Log.e("API_FAIL", t.message.toString())
                Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}