package com.egorpoprotskiy.shoppinglist.presentation

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

// указываем из каких источников брать данные(или из ID из макетов, или из БД)
//5.2.1
class ItemShoppingViewHoler(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)