package com.egorpoprotskiy.shoppinglist.Domain

//1 Добавление Domain-слоя
class EditItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun editItemShopping(listShopping: ListShopping) {
        shoppingRepository.editItemShopping(listShopping)
    }
}