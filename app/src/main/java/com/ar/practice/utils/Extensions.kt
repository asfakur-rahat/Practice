package com.ar.practice.utils

import android.view.View

fun View.setVisibility(it: Boolean){
    this.visibility = if(it) View.VISIBLE else View.GONE
}