package com.egorpoprotskiy.shoppinglist.domain

//1.1 Добавление Domain-слоя
class DeleteItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun deleteItemShopping(listShopping: ListShopping) {
        shoppingRepository.deleteItemShopping(listShopping)
    }
}