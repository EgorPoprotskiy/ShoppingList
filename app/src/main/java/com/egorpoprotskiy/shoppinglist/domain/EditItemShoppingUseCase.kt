package com.egorpoprotskiy.shoppinglist.domain

//1.1 Добавление Domain-слоя
class EditItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun editItemShopping(listShopping: ListShopping) {
        shoppingRepository.editItemShopping(listShopping)
    }
}