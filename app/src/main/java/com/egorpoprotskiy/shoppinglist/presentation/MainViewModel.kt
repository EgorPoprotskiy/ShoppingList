package com.egorpoprotskiy.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.shoppinglist.data.ShoppingRepositoryImpl
import com.egorpoprotskiy.shoppinglist.domain.DeleteItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.EditItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.GetListShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//1.3+1.4 Добавление Presentation слоя + автообновление списка
class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShoppingRepositoryImpl(application)

    private val getListShoppingUseCase = GetListShoppingUseCase(repository)
    private val deleteItemShoppingUseCase = DeleteItemShoppingUseCase(repository)
    private val editItemShoppingUseCase = EditItemShoppingUseCase(repository)

//    private val scope = CoroutineScope(Dispatchers.Main)

    val shopList = getListShoppingUseCase.getListShopping()

    fun deleteItemShopping(listShopping: ListShopping) {
        viewModelScope.launch {
            deleteItemShoppingUseCase.deleteItemShopping(listShopping)
        }
    }

    fun changeValueState(listShopping: ListShopping) {
        viewModelScope.launch {
            val newItem = listShopping.copy(active = !listShopping.active)
            editItemShoppingUseCase.editItemShopping(newItem)
        }
    }
    //Метод для закрытия скоупа
//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }
}