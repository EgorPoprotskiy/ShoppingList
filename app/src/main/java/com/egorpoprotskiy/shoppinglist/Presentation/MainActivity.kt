package com.egorpoprotskiy.shoppinglist.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    //2.2 переменная для привязки адаптера к RecyclerView
    private lateinit var listShoppingAdapter: ListShoppingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //проверка работы методов(потом будет удалена)
        //2.2 Вызов метода с REcyclerVeiw
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            //2.2 Отслеживание списка из RecyclerView
            listShoppingAdapter.submitList(it)
        }
    }

    //2.2 привязка адаптера к RecyclerView
    private fun setupRecyclerView() {
        //присваивание переменной id из макета
        val recyclerViewList = findViewById<RecyclerView>(R.id.recyclerView_list)
        with(recyclerViewList) {
            //создание экземпляра переменно из ListShoppingAdapter
            listShoppingAdapter = ListShoppingAdapter()
            //назначение переменной для RecyclerView
            adapter = listShoppingAdapter
            //2.2.4 определяем сколько вьюХолдеров может хранит пул recyclerView
            recycledViewPool.setMaxRecycledViews(
                ListShoppingAdapter.VIEW_TYPE_ACTIVE,
                ListShoppingAdapter.MAX_SIZE_POOL
            )
            recycledViewPool.setMaxRecycledViews(
                ListShoppingAdapter.VIEW_TYPE_INACTIVE,
                ListShoppingAdapter.MAX_SIZE_POOL
            )
        }
        //2.2.5.1 Вызов функции длительного слушателя нажатий
        setupOnLongClickListener()
        //2.2.5.2 Вызов функции слушателя нажатий
        setupOnClickListener()
        //2.2.5.3 Вызов функции свайпа
        setupOnSwipe(recyclerViewList)
    }
    //2.2.5.1 Добавление длительного слушателя нажатий
    private fun setupOnLongClickListener() {
        listShoppingAdapter.onItemShoppingLongClickListener = {
            viewModel.changeValueState(it)
        }
    }

    //2.2.5.2 Добавление слушателя нажатий
    private fun setupOnClickListener() {
        listShoppingAdapter.onItemShoppingClickListener = {
            Log.d("MainActivityQ", it.toString())
        }
    }

    //2.2.5.3 Добавление свайпа
    private fun setupOnSwipe(recyclerViewList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = listShoppingAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteItemShopping(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewList)
    }
}