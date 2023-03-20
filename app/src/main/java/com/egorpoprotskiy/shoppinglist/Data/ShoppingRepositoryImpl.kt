package com.egorpoprotskiy.shoppinglist.Data

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
import com.egorpoprotskiy.shoppinglist.Domain.ShoppingRepository
import kotlin.random.Random

//1.2 Добавление Data-слоя
object ShoppingRepositoryImpl: ShoppingRepository {

//    private val listAllShopping = mutableListOf<ListShopping>()
    //сортированный список по ID
    private val listAllShopping = sortedSetOf<ListShopping>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private val listAllShoppingLD = MutableLiveData<List<ListShopping>>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 100) {
            val item = ListShopping("name $i", i, Random.nextBoolean())
            addItemShopping(item)
        }
    }

    override fun addItemShopping(listShopping: ListShopping) {
        //проверка, добавляется новый элемент или добавляется существующий элемент при редактировани.
        if (listShopping.id == ListShopping.ID_NOTFOUND) {
            listShopping.id = autoIncrementId++
        }
        listAllShopping.add(listShopping)
        updateListShopping()
    }

    override fun deleteItemShopping(listShopping: ListShopping) {
        listAllShopping.remove(listShopping)
        updateListShopping()
    }

    override fun editItemShopping(listShopping: ListShopping) {
        //при редактировании элемента, надо сначала его удалить и затем добавить новый(измененный)
        val oldItemId = getItemShopping(listShopping.id)
        listAllShopping.remove(oldItemId)
        addItemShopping(listShopping)
    }

    override fun getItemShopping(listShoppingId: Int): ListShopping {
        //проверка на null, если элемент с нужным id не найден, то будет исключение
        return listAllShopping.find { it.id == listShoppingId} ?: throw java.lang.RuntimeException("Element with ID $listShoppingId not found")
    }

    override fun getListShopping(): LiveData<List<ListShopping>> {
        return listAllShoppingLD
    }

    fun updateListShopping() {
        listAllShoppingLD.value = listAllShopping.toList()
    }
}