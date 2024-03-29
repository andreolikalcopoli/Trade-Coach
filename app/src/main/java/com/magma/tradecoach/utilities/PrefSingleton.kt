package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.magma.tradecoach.BaseApplication

class PrefSingleton {
    private var preferences: SharedPreferences =
        BaseApplication.applicationContext().getSharedPreferences("main", Context.MODE_PRIVATE)

    companion object {
        private const val DEFAULT_INT_VALUE = 0
        private const val DEFAULT_STRING_VALUE = ""

        @JvmStatic
        val instance: PrefSingleton by lazy { PrefSingleton() }

    }

    fun saveString(key: String, value: String) {
        val e = preferences.edit()
        e.putString(key, value)
        e.apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "") ?: ""
    }

    fun saveInt(key: String, value: Int) {
        val e = preferences.edit()
        e.putInt(key, value)
        e.apply()
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun saveBool(key: String, value: Boolean) {
        val e = preferences.edit()
        e.putBoolean(key, value)
        e.apply()
    }

    fun getBool(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }
    fun isLogged(): Boolean {
        return preferences.getBoolean(Constants.LOGGED_KEY,false)
    }
}