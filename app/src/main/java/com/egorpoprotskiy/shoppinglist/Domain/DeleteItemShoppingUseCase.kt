package com.egorpoprotskiy.shoppinglist.Domain

class DeleteItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun deleteItemShopping(listShopping: ListShopping) {
        shoppingRepository.deleteItemShopping(listShopping)
    }
}