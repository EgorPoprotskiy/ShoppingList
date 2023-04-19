package com.egorpoprotskiy.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.egorpoprotskiy.shoppinglist.domain.ListShopping

//2.2.6.1 Данный класс принимает два писка и говорит, как их сравнивать
class ListShoppingDiffCallback(private val oldListShopping: List<ListShopping>, private val newListShopping: List<ListShopping>): DiffUtil.Callback() {
    //2.2.6.1 Возвращает размер старого списка
    override fun getOldListSize(): Int {
        return oldListShopping.size
    }
    //2.2.6.1 Возвращает размер нового списка
    override fun getNewListSize(): Int {
        return newListShopping.size
    }
    //2.2.6.1 Сравнивает сами объекты, чтобы адаптер понял, работает он с етм же объектом или нет
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldListShopping[oldItemPosition]
        val newItem = newListShopping[newItemPosition]
        return oldItem.id == newItem.id
    }
    //2.2.6.1 Сравнивает поля объектов, чтобы унать нужно перерисовывать какой-то конкретный объект или нет
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldListShopping[oldItemPosition]
        val newItem = newListShopping[newItemPosition]
        return oldItem == newItem
    }
}