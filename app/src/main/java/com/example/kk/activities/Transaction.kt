package com.example.kk.activities

data class Transaction(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val amount: String = "",
    val isCredit: Boolean = true
)