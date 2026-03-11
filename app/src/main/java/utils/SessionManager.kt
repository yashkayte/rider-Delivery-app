package com.example.kk.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("kk_session", Context.MODE_PRIVATE)

    fun setLoggedIn(value: Boolean) {
        prefs.edit().putBoolean("isLoggedIn", value).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}