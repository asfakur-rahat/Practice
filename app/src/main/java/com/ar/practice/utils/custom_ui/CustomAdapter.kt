package com.ar.practice.utils.custom_ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ar.practice.R
import com.ar.practice.utils.setVisibility
import com.google.android.material.textview.MaterialTextView

class CustomAdapter(
    context: Context,
    private val items: Array<String>
) : ArrayAdapter<String>(context, R.layout.item_drop_down, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_drop_down, parent, false)
        val divider = view.findViewById<View>(R.id.divider)
        val text = view.findViewById<MaterialTextView>(R.id.tv_label)
        text.text = items[position]
        divider.setVisibility(position != items.size - 1)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getDropDownView(position, convertView, parent)
    }
}
