package com.egorpoprotskiy.shoppinglist.Domain

class AddItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun addItemShopping(listShopping: ListShopping) {
        shoppingRepository.addItemShopping(listShopping)
    }
}