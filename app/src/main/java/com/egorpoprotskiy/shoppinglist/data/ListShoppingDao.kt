package com.egorpoprotskiy.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListShoppingDao {
    //Метод, возвращающий список всех записей
    @Query("SELECT * FROM list_shopping")
    fun getListShopping(): LiveData<List<ListShoppingDbModel>>
    //Метод, добавляет новую запись
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListShopping(listShoppingDbModel: ListShoppingDbModel)
    //Метод, удаляет элемент из базы
    @Query("DELETE FROM list_shopping WHERE id=:listShoppingId")
    suspend fun deleteListShopping(listShoppingId: Int)
    //Метод, возвращает один элемент из базы
    @Query("SELECT * FROM list_shopping WHERE id=:listShoppingId LIMIT 1")
    suspend fun getItemShopping(listShoppingId: Int): ListShoppingDbModel
}