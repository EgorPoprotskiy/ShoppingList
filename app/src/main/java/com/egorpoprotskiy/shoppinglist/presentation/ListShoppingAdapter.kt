package com.egorpoprotskiy.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
import com.egorpoprotskiy.shoppinglist.R
import com.egorpoprotskiy.shoppinglist.databinding.ItemShoppingActiveBinding
import com.egorpoprotskiy.shoppinglist.databinding.ItemShoppingInactiveBinding

//2.2.2 Создание Adapter для RecyclerView
class ListShoppingAdapter: androidx.recyclerview.widget.ListAdapter<ListShopping, ItemShoppingViewHoler>(ItemShoppingDiffCallback()) {

    //2.2.5.1 Создание функции для длительного слушателя нажатий. В качестве параметра принимает ListShop и ничего не возвращает(Unit).
    var onItemShoppingLongClickListener: ((ListShopping) -> Unit)?= null
    //2.2.5.2 Создание функции для слушателя нажатий. В качестве параметра принимает ListShop и ничего не возвращает(Unit).
    var onItemShoppingClickListener: ((ListShopping) -> Unit)?= null

    //раздувает макет
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShoppingViewHoler {
        //2.2.4 Выбор макета, в зависимости от значения, которое вернул метод getItemViewType
        val layout = when (viewType) {
            VIEW_TYPE_ACTIVE -> R.layout.item_shopping_active
            VIEW_TYPE_INACTIVE -> R.layout.item_shopping_inactive
            else -> throw  java.lang.RuntimeException("Unknow view type: $viewType")
        }
        //Вставка выбранного макета
//        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        // 5.2.2 Присваивание макета с помощью binding
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layout, parent, false)
        return ItemShoppingViewHoler(binding)
    }

    //передает текущие значения списка(полученные в классе ItemShoppingViewHoler), в данные из макета
    override fun onBindViewHolder(holder: ItemShoppingViewHoler, position: Int) {
        val itemShopping = getItem(position)
        //5.2.3 получение объекта binding
        val binding = holder.binding
        //2.2.5.1 Вызов функции, в которую передаем элемент в текущей позиции. invoke - для того, если функция равна null
        binding.root.setOnLongClickListener {
            onItemShoppingLongClickListener?.invoke(itemShopping)
            true
        }
        //2.2.5.2 Вызов функции, в которую передаем элемент в текущей позиции. invoke - для того, если функция равна null
        binding.root.setOnClickListener {
            onItemShoppingClickListener?.invoke(itemShopping)
        }
        //5.2.4
        //6.3.1 Перенести в макет и изменить код
        when (binding) {
            is ItemShoppingInactiveBinding -> {
                binding.itemShopping = itemShopping
            }
            is ItemShoppingActiveBinding -> {
                binding.itemShopping = itemShopping
            }
        }
    }

    //2.2.4 Переопределение метода для viewType, который используется в onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        //определяем позицию элемента
        val itemShopping = getItem(position)
        //возвращаем числа в зависимости от активности
        return if (itemShopping.active) {
            VIEW_TYPE_ACTIVE
        } else {
            VIEW_TYPE_INACTIVE
        }
    }

    //2.2.4 Константы для хранения чисел
    companion object {
        const val VIEW_TYPE_ACTIVE = 0
        const val VIEW_TYPE_INACTIVE = 1
        //константа для хранения количества элементов в пуле recyclerView
        const val MAX_SIZE_POOL = 15
    }
}