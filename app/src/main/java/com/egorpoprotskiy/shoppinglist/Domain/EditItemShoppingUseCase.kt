package com.egorpoprotskiy.shoppinglist.Domain

class EditItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun editItemShopping(listShopping: ListShopping) {
        shoppingRepository.editItemShopping(listShopping)
    }
}