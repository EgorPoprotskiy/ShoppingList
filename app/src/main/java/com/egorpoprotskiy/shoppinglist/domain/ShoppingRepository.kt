package com.egorpoprotskiy.shoppinglist.domain

import androidx.lifecycle.LiveData
//1.1 Создание Domain-слоя
interface ShoppingRepository {
    suspend fun addItemShopping(listShopping: ListShopping)

    suspend fun deleteItemShopping(listShopping: ListShopping)

    suspend fun editItemShopping(listShopping: ListShopping)

    suspend fun getItemShopping(listShoppingId: Int): ListShopping

    fun getListShopping(): LiveData<List<ListShopping>>
}