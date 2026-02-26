package com.example.kk.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class EarningsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earnings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerEarnings)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val list = listOf(
            EarningItem("25 Feb 2026", "8 deliveries", "₹1,250"),
            EarningItem("24 Feb 2026", "6 deliveries", "₹980"),
            EarningItem("23 Feb 2026", "7 deliveries", "₹1,110"),
            EarningItem("22 Feb 2026", "5 deliveries", "₹850")
        )

        recycler.adapter = EarningAdapter(list)

        // Withdraw button
        val btnWithdraw = view.findViewById<MaterialButton>(R.id.btnWithdraw)
        btnWithdraw.setOnClickListener {
            Toast.makeText(requireContext(), "Withdraw requested (demo)", Toast.LENGTH_SHORT).show()
        }
    }
}