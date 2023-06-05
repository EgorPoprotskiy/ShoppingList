package com.egorpoprotskiy.shoppinglist.domain

//1.1 Добавление Domain-слоя
class AddItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun addItemShopping(listShopping: ListShopping) {
        shoppingRepository.addItemShopping(listShopping)
    }
}