package com.egorpoprotskiy.shoppinglist.Domain

//1.1 Добавление Domain-слоя
class GetItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getItemShopping(listShopping: Int): ListShopping {
        return shoppingRepository.getItemShopping(listShopping)
    }
}