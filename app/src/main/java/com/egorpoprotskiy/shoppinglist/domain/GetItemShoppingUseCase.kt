package com.egorpoprotskiy.shoppinglist.domain

//1.1 Добавление Domain-слоя
class GetItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun getItemShopping(listShopping: Int): ListShopping {
        return shoppingRepository.getItemShopping(listShopping)
    }
}