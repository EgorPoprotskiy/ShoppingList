package com.egorpoprotskiy.shoppinglist.Domain

import androidx.lifecycle.LiveData

interface ShoppingRepository {
    fun addItemShopping(listShopping: ListShopping)

    fun deleteItemShopping(listShopping: ListShopping)

    fun editItemShopping(listShopping: ListShopping)

    fun getItemShopping(listShoppingId: Int): ListShopping

    fun getListShopping(): LiveData<List<ListShopping>>
}