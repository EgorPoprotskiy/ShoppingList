package com.egorpoprotskiy.shoppinglist.Presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.shoppinglist.R

// указываем из каких источников брать данные(или из ID из макетов, или из БД)
class ItemShoppingViewHoler(val view: View): RecyclerView.ViewHolder(view){
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)
}