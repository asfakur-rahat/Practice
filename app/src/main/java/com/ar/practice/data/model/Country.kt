package com.ar.practice.data.model

data class Country(
    val id: Int,
    val flag: Int,
    val name: String,
    var isSelected: Boolean = false
)
