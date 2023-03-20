package com.egorpoprotskiy.shoppinglist.Domain

//1.1 Добавление Domain-слоя
class AddItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun addItemShopping(listShopping: ListShopping) {
        shoppingRepository.addItemShopping(listShopping)
    }
}