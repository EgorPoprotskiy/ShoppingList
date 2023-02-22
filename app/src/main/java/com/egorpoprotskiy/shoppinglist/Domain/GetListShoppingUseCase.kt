package com.egorpoprotskiy.shoppinglist.Domain

class GetListShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getListShopping(): List<ListShopping> {
        return shoppingRepository.getListShopping()
    }
}