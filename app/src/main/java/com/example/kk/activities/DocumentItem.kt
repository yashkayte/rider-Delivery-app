package com.example.kk.activities

data class DocumentItem(
    val title: String,
    val key: String,
    var isUploaded: Boolean = false,
    var fileUrl: String? = null
)