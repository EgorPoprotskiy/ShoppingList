package com.egorpoprotskiy.shoppinglist.Domain

//1 Добавление Domain-слоя
class GetListShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getListShopping(): List<ListShopping> {
        return shoppingRepository.getListShopping()
    }
}