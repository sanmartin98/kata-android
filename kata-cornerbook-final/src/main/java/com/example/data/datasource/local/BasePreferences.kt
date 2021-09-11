package com.example.data.datasource.local

import android.content.SharedPreferences

open class BasePreferences(var sharedPref: SharedPreferences) {
    private val edit: SharedPreferences.Editor? by lazy { sharedPref.edit() }

    fun clear() {
        edit?.clear()?.commit()
    }

    protected fun save() {
        edit?.apply()
    }

    protected fun save(key: Enum<*>, value: Int) {
        edit?.putInt(key.name, value)
        save()
    }

    protected fun save(key: Enum<*>, value: String?) {
        edit?.putString(key.name, value)
        save()
    }

    protected fun save(key: Enum<*>, value: Boolean) {
        edit?.putBoolean(key.name, value)
        save()
    }

    protected fun getInt(key: Enum<*>, defaultValue: Int): Int {
        return sharedPref.getInt(key.name, defaultValue)
    }

    protected fun getString(key: Enum<*>, defaultValue: String): String {
        return sharedPref.getString(key.name, defaultValue) ?: defaultValue
    }

    protected fun getBoolean(key: Enum<*>, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key.name, defaultValue)
    }

    protected fun remove(key: Enum<*>) {
        edit?.remove(key.name)
        save()
    }
}