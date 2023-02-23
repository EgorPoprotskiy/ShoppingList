package com.egorpoprotskiy.shoppinglist.Data

import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
import com.egorpoprotskiy.shoppinglist.Domain.ShoppingRepository
//2 Добавление Data-слоя
object ShoppingRepositoryImpl: ShoppingRepository {

    private val listAllShopping = mutableListOf<ListShopping>()

    private var autoIncrementId = 0

    init {
        for (i in 1 until 10) {
            val item = ListShopping("name $i", i, true)
            addItemShopping(item)
        }
    }

    override fun addItemShopping(listShopping: ListShopping) {
        //проверка, добавляется новый элемент или добавляется существующий элеме нт при редактированит.
        if (listShopping.id == ListShopping.ID_NOTFOUND) {
            listShopping.id = autoIncrementId++
        }
        listAllShopping.add(listShopping)
    }

    override fun deleteItemShopping(listShopping: ListShopping) {
        listAllShopping.remove(listShopping)
    }

    override fun editItemShopping(listShopping: ListShopping) {
        //при редактировании элемента, надо сначала его удалить и затем добавить новый(измененный)
        val oldItemId = getItemShopping(listShopping.id)
        listAllShopping.remove(oldItemId)
        listAllShopping.add(listShopping)
    }

    override fun getItemShopping(listShoppingId: Int): ListShopping {
        //проверка на null, если элемент с нужным id не найден, то будет исключение
        return listAllShopping.find { it.id == listShoppingId} ?: throw java.lang.RuntimeException("Element with ID $listShoppingId not found")
    }

    override fun getListShopping(): List<ListShopping> {
        //возвращать необходимо копию списка
        return listAllShopping.toList()
    }
}