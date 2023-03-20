package com.egorpoprotskiy.shoppinglist.Presentation

import androidx.recyclerview.widget.DiffUtil
import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
//2.2.6.2
class ItemShoppingDiffCallback: DiffUtil.ItemCallback<ListShopping>() {
    override fun areItemsTheSame(oldItem: ListShopping, newItem: ListShopping): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListShopping, newItem: ListShopping): Boolean {
        return oldItem == newItem
    }
}