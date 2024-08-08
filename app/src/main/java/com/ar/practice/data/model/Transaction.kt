package com.ar.practice.data.model


data class Transaction(
    val id: Int,
    val date: String,
    val title: String,
    val type: String,
    val icon: Int,
    val amount: String
)
