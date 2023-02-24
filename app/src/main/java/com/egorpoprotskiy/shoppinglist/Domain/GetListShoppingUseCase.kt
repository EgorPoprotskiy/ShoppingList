package com.egorpoprotskiy.shoppinglist.Domain

import androidx.lifecycle.LiveData

//1 Добавление Domain-слоя
class GetListShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getListShopping(): LiveData<List<ListShopping>> {
        return shoppingRepository.getListShopping()
    }
}