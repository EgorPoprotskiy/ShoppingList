package com.egorpoprotskiy.shoppinglist.Domain

//1 Добавление Domain-слоя
class DeleteItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun deleteItemShopping(listShopping: ListShopping) {
        shoppingRepository.deleteItemShopping(listShopping)
    }
}