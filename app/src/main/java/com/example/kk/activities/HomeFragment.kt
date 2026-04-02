package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kk.R
import com.example.kk.models.LocationData
import com.example.kk.models.Order
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var activeOrderListener: ListenerRegistration? = null
    private var riderListener: ListenerRegistration? = null

    private var txtStatus: TextView? = null
    private var txtSubTitle: TextView? = null
    private var switchOnline: MaterialSwitch? = null
    private var cardActiveOrder: MaterialCardView? = null
    
    private var txtPickup: TextView? = null
    private var txtDrop: TextView? = null
    private var txtCustomerName: TextView? = null
    private var txtEarning: TextView? = null
    
    private var currentActiveOrder: Order? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        txtStatus = view.findViewById(R.id.txtStatus)
        txtSubTitle = view.findViewById(R.id.txtSubTitle)
        switchOnline = view.findViewById(R.id.switchOnline)
        cardActiveOrder = view.findViewById(R.id.cardActiveOrder)
        
        txtPickup = view.findViewById(R.id.txtPickup)
        txtDrop = view.findViewById(R.id.txtDrop)
        txtCustomerName = view.findViewById(R.id.txtCustomerName)
        txtEarning = view.findViewById(R.id.txtEarning)

        val btnSupport = view.findViewById<MaterialButton>(R.id.btnSupport)
        val btnWallet = view.findViewById<MaterialButton>(R.id.btnWallet)
        val btnHistory = view.findViewById<MaterialButton>(R.id.btnHistory)
        val btnStart = view.findViewById<MaterialButton>(R.id.btnStartDelivery)

        // Initial UI state
        cardActiveOrder?.visibility = View.GONE

        switchOnline?.setOnCheckedChangeListener { _, isChecked ->
            updateRiderStatus(isChecked)
        }

        btnSupport?.setOnClickListener { startActivity(Intent(requireContext(), SupportActivity::class.java)) }
        btnWallet?.setOnClickListener { startActivity(Intent(requireContext(), WalletActivity::class.java)) }
        btnHistory?.setOnClickListener { startActivity(Intent(requireContext(), HistoryActivity::class.java)) }

        btnStart?.setOnClickListener {
            currentActiveOrder?.let { order ->
                val i = Intent(requireContext(), OrderDetailsActivity::class.java).apply {
                    putExtra("orderId", order.orderId)
                    putExtra("pickup", order.pickupLocation?.address)
                    putExtra("drop", order.dropLocation?.address)
                    putExtra("customerName", order.customerName)
                    putExtra("customerPhone", order.customerPhone)
                    putExtra("distanceKm", order.distanceKm)
                    putExtra("earning", order.earning)
                    putExtra("paymentType", order.paymentType)
                }
                startActivity(i)
            }
        }

        observeRiderData()
    }

    private fun observeRiderData() {
        val uid = auth.currentUser?.uid ?: return
        
        riderListener = db.collection("riders").document(uid)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("HomeFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val isOnline = snapshot.getBoolean("isOnline") ?: false
                    switchOnline?.isChecked = isOnline
                    updateUI(isOnline)
                    
                    if (isOnline) {
                        observeActiveOrder(uid)
                    } else {
                        activeOrderListener?.remove()
                        cardActiveOrder?.visibility = View.GONE
                        currentActiveOrder = null
                    }
                }
            }
    }

    private fun observeActiveOrder(riderId: String) {
        activeOrderListener?.remove()
        
        activeOrderListener = db.collection("orders")
            .whereEqualTo("riderId", riderId)
            .whereEqualTo("status", "ACTIVE")
            .limit(1)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("HomeFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    val doc = snapshots.documents[0]
                    val order = doc.toObject(Order::class.java)
                    if (order != null) {
                        currentActiveOrder = order
                        displayActiveOrder(order)
                    }
                } else {
                    cardActiveOrder?.visibility = View.GONE
                    currentActiveOrder = null
                }
            }
    }

    private fun displayActiveOrder(order: Order) {
        txtPickup?.text = order.pickupLocation?.address ?: "Unknown"
        txtDrop?.text = order.dropLocation?.address ?: "Unknown"
        txtCustomerName?.text = order.customerName
        txtEarning?.text = "₹${order.earning}"
        cardActiveOrder?.visibility = View.VISIBLE
    }

    private fun updateRiderStatus(isOnline: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("riders").document(uid)
            .update("isOnline", isOnline)
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update status", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUI(isOnline: Boolean) {
        txtStatus?.text = if (isOnline) "🟢 ONLINE" else "🔴 OFFLINE"
        txtSubTitle?.text = if (isOnline) "Ready to deliver today?" else "Go online to start getting orders"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        riderListener?.remove()
        activeOrderListener?.remove()
    }
}
