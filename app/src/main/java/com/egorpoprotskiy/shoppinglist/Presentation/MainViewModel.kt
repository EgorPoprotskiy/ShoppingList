package com.egorpoprotskiy.shoppinglist.Presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.shoppinglist.Data.ShoppingRepositoryImpl
import com.egorpoprotskiy.shoppinglist.Data.ShoppingRepositoryImpl.getListShopping
import com.egorpoprotskiy.shoppinglist.Domain.DeleteItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.Domain.EditItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.Domain.GetListShoppingUseCase
import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
//1.3+1.4 Добавление Presentation слоя + автообновление списка
class MainViewModel: ViewModel() {

    private val repository = ShoppingRepositoryImpl

    private val getListShoppingUseCase = GetListShoppingUseCase(repository)
    private val deleteItemShoppingUseCase = DeleteItemShoppingUseCase(repository)
    private val editItemShoppingUseCase = EditItemShoppingUseCase(repository)

    val shopList = getListShoppingUseCase.getListShopping()

    fun deleteItemShopping(listShopping: ListShopping) {
        deleteItemShoppingUseCase.deleteItemShopping(listShopping)
    }

    fun changeValueState(listShopping: ListShopping) {
        val newItem = listShopping.copy(active = !listShopping.active)
        editItemShoppingUseCase.editItemShopping(newItem)
    }
}