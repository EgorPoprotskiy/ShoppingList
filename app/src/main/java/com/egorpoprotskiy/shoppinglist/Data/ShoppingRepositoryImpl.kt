package com.egorpoprotskiy.shoppinglist.Data

import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
import com.egorpoprotskiy.shoppinglist.Domain.ShoppingRepository
//2 Добавление Data-слоя
object ShoppingRepositoryImpl: ShoppingRepository {

    private val ListAllShopping = mutableListOf<ListShopping>()

    private var autoIncrementId = 0

    override fun addItemShopping(listShopping: ListShopping) {
        //проверка, добавляется новый элемент или добавляется существующий элемент при редактированит.
        if (listShopping.id == ListShopping.ID_NOTFOUND) {
            listShopping.id = autoIncrementId++
        }
        ListAllShopping.add(listShopping)
    }

    override fun deleteItemShopping(listShopping: ListShopping) {
        ListAllShopping.remove(listShopping)
    }

    override fun editItemShopping(listShopping: ListShopping) {
        //при редактировании элемента, надо сначала его удалить и затем добавить новый(измененный)
        val oldItemId = getItemShopping(listShopping.id)
        ListAllShopping.remove(oldItemId)
        ListAllShopping.add(listShopping)
    }

    override fun getItemShopping(listShoppingId: Int): ListShopping {
        //проверка на null, если элемент с нужным id не найден, то будет исключение
        return ListAllShopping.find { it.id == listShoppingId} ?: throw java.lang.RuntimeException("Element with ID $listShoppingId not found")
    }

    override fun getListShopping(): List<ListShopping> {
        //возвращать необходимо копию списка
        return ListAllShopping.toList()
    }
}