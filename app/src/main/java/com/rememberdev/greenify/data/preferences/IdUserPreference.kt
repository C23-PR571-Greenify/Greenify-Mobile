package com.rememberdev.greenify.data.preferences

import android.content.Context

internal class IdUserPreference (context: Context){
    companion object{
        private const val USER = ""
        private const val USER_ID = "userId"
    }

    private val preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE)

    fun setUserID(userId: String){
        val editor = preferences.edit()
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun getUserID(): String {
        return preferences.getString(USER_ID, "") ?: ""
    }
}