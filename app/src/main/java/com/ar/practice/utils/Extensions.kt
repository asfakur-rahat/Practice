package com.ar.practice.utils

import android.content.SharedPreferences
import android.view.View
import com.ar.practice.data.model.Transaction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

fun View.setVisibility(it: Boolean){
     if(it) this.beVisible() else this.beGone()
}

fun View.beVisible(){
    this.visibility = View.VISIBLE
}

fun View.beGone(){
    this.visibility = View.GONE
}

fun SharedPreferences.putTransactionList(key: String, list: List<Transaction>) {
    val jsonString = Json.encodeToString(list)
    edit().putString(key, jsonString).apply()
}

fun SharedPreferences.getTransactionList(key: String): List<Transaction>? {
    val jsonString = getString(key, null) ?: return null
    return decodeFromString(jsonString)
}