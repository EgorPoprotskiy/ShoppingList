package com.egorpoprotskiy.shoppinglist.Domain

class GetItemShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getItemShopping(listShopingId: Int): ListShopping {
        return shoppingRepository.getItemShopping(listShopingId)
    }
}