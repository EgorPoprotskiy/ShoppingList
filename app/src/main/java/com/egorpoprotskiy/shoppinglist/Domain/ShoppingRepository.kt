package com.egorpoprotskiy.shoppinglist.Domain

interface ShoppingRepository {
    fun addItemShopping(listShopping: ListShopping)

    fun deleteItemShopping(listShopping: ListShopping)

    fun editItemShopping(listShopping: ListShopping)

    fun getItemShopping(listShoppingId: Int): ListShopping

    fun getListShopping(): List<ListShopping>
}