package com.egorpoprotskiy.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.shoppinglist.data.ShoppingRepositoryImpl
import com.egorpoprotskiy.shoppinglist.domain.DeleteItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.EditItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.GetListShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
//1.3+1.4 Добавление Presentation слоя + автообновление списка
class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShoppingRepositoryImpl(application)

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