package com.egorpoprotskiy.shoppinglist.domain

import androidx.lifecycle.LiveData
//1.1 Создание Domain-слоя
interface ShoppingRepository {
    fun addItemShopping(listShopping: ListShopping)

    fun deleteItemShopping(listShopping: ListShopping)

    fun editItemShopping(listShopping: ListShopping)

    fun getItemShopping(listShoppingId: Int): ListShopping

    fun getListShopping(): LiveData<List<ListShopping>>
}