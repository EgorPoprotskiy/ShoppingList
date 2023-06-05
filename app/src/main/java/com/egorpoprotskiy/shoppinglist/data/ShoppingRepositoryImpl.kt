package com.egorpoprotskiy.shoppinglist.data

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
import com.egorpoprotskiy.shoppinglist.domain.ShoppingRepository
import kotlin.random.Random

//1.2 Добавление Data-слоя
class ShoppingRepositoryImpl(application: Application): ShoppingRepository {
    private val listShoppingDao = AppDatabase.getInstance(application).listShoppingDao()
    private val mapper = ListShoppingMapper()
//    private val listAllShopping = mutableListOf<ListShopping>()
    //сортированный список по ID
    //Данный код не нужен, после создния БД
//    private val listAllShopping = sortedSetOf<ListShopping>({ o1, o2 -> o1.id.compareTo(o2.id) })
//    private val listAllShoppingLD = MutableLiveData<List<ListShopping>>()
//    private var autoIncrementId = 0
//    init {
//        for (i in 0 until 10) {
//            val item = ListShopping("name $i", i, Random.nextBoolean())
//            addItemShopping(item)
//        }
//    }

    override fun addItemShopping(listShopping: ListShopping) {
        //После добавления БД, меняется реализация методов
        //проверка, добавляется новый элемент или добавляется существующий элемент при редактировани.
//        if (listShopping.id == ListShopping.ID_NOTFOUND) {
//            listShopping.id = autoIncrementId++
//        }
//        listAllShopping.add(listShopping)
//        updateListShopping()
        listShoppingDao.addListShopping(mapper.mapEntityToDbModel(listShopping))
    }

    override fun deleteItemShopping(listShopping: ListShopping) {
        //После добавления БД, меняется реализация методов
//        listAllShopping.remove(listShopping)
//        updateListShopping()
        listShoppingDao.deleteListShopping(listShopping.id)
    }

    override fun editItemShopping(listShopping: ListShopping) {
        //После добавления БД, меняется реализация методов
        //при редактировании элемента, надо сначала его удалить и затем добавить новый(измененный)
//        val oldItemId = getItemShopping(listShopping.id)
//        listAllShopping.remove(oldItemId)
//        addItemShopping(listShopping)
        listShoppingDao.addListShopping(mapper.mapEntityToDbModel(listShopping))

    }

    override fun getItemShopping(listShoppingId: Int): ListShopping {
        //После добавления БД, меняется реализация методов
        //проверка на null, если элемент с нужным id не найден, то будет исключение
//        return listAllShopping.find { it.id == listShoppingId} ?: throw java.lang.RuntimeException("Element with ID $listShoppingId not found")
        val dbModel = listShoppingDao.getItemShopping(listShoppingId)
        return mapper.mapDbModelToEntity(dbModel)
    }
    //Метод MediatorLiveData - перехватывает LiveData, в него добавляем источник данных addSourse,
    // в который добавляем LiveData, значение которой будем перехватывать.
    // После перехвата, мы можем установить значение вручную


    override fun getListShopping(): LiveData<List<ListShopping>> = MediatorLiveData<List<ListShopping>>().apply {
        addSource(listShoppingDao.getListShopping()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }

//    fun updateListShopping() {
//        listAllShoppingLD.value = listAllShopping.toList()
//    }
}