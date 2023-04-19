package com.egorpoprotskiy.shoppinglist.Presentation

import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.shoppinglist.R
import com.egorpoprotskiy.shoppinglist.databinding.ItemShoppingInactiveBinding

// указываем из каких источников брать данные(или из ID из макетов, или из БД)
//5.2.1
class ItemShoppingViewHoler(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)