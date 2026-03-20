package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kk.R
import com.example.kk.models.Order
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        val txtSubTitle = view.findViewById<TextView>(R.id.txtSubTitle)
        val switchOnline = view.findViewById<MaterialSwitch>(R.id.switchOnline)
        val cardActiveOrder = view.findViewById<MaterialCardView>(R.id.cardActiveOrder)

        val btnSupport = view.findViewById<MaterialButton>(R.id.btnSupport)
        val btnWallet = view.findViewById<MaterialButton>(R.id.btnWallet)
        val btnHistory = view.findViewById<MaterialButton>(R.id.btnHistory)
        val btnStart = view.findViewById<MaterialButton>(R.id.btnStartDelivery)

        // Dummy active order data
        val activeOrder = Order(
            orderId = "KH1024",
            pickup = "Pizza Hut, Andheri West",
            drop = "Bandra East, Mumbai",
            customerName = "Rahul Sharma",
            customerPhone = "+919876543210",
            distanceKm = 4.2,
            earning = 75,
            paymentType = "COD",
            status = "ACTIVE"
        )

        fun applyStatus(isOnline: Boolean) {
            if (isOnline) {
                txtStatus.text = "🟢 ONLINE"
                txtSubTitle.text = "Ready to deliver today?"
                cardActiveOrder.visibility = View.VISIBLE
            } else {
                txtStatus.text = "🔴 OFFLINE"
                txtSubTitle.text = "Go online to start getting orders"
                cardActiveOrder.visibility = View.GONE
            }
        }

        applyStatus(switchOnline.isChecked)

        switchOnline.setOnCheckedChangeListener { _, isChecked ->
            applyStatus(isChecked)
            Toast.makeText(requireContext(), if (isChecked) "You are Online" else "You are Offline", Toast.LENGTH_SHORT).show()
        }

        btnSupport.setOnClickListener {
            startActivity(Intent(requireContext(), SupportActivity::class.java))
        }
        
        btnWallet.setOnClickListener {
            startActivity(Intent(requireContext(), WalletActivity::class.java))
        }
        
        btnHistory.setOnClickListener {
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

        btnStart.setOnClickListener {
            val i = Intent(requireContext(), OrderDetailsActivity::class.java)
            i.putExtra("orderId", activeOrder.orderId)
            i.putExtra("pickup", activeOrder.pickup)
            i.putExtra("drop", activeOrder.drop)
            i.putExtra("customerName", activeOrder.customerName)
            i.putExtra("customerPhone", activeOrder.customerPhone)
            i.putExtra("distanceKm", activeOrder.distanceKm)
            i.putExtra("earning", activeOrder.earning)
            i.putExtra("paymentType", activeOrder.paymentType)
            startActivity(i)
        }
    }
}