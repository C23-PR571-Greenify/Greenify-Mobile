package com.rememberdev.greenify.data.preferences

import android.content.Context

internal class TokenPreference(context: Context) {
    companion object{
        private const val USER = ""
        private const val TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE)

    fun setToken(token: String){
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return preferences.getString(TOKEN, "") ?: ""
    }
}