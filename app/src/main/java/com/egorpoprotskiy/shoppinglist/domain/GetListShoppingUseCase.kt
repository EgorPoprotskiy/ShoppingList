package com.egorpoprotskiy.shoppinglist.domain

import androidx.lifecycle.LiveData

//1.1 Добавление Domain-слоя
class GetListShoppingUseCase(private val shoppingRepository: ShoppingRepository) {
    fun getListShopping(): LiveData<List<ListShopping>> {
        return shoppingRepository.getListShopping()
    }
}