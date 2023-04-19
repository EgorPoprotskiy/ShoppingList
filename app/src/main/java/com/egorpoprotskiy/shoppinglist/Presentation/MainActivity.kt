package com.egorpoprotskiy.shoppinglist.Presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egorpoprotskiy.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
//4.6.5 В активити сделать зависимость от интерфейса
class MainActivity : AppCompatActivity(), ItemShoppingFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    //2.2 переменная для привязки адаптера к RecyclerView
    private lateinit var listShoppingAdapter: ListShoppingAdapter
    //4.5.7 Ссылка на контейнер в activity_main(land)
    private var itemShoppingContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //4.5.8 Привязка макета
        itemShoppingContainer = findViewById(R.id.item_shopping_container)
        //проверка работы методов(потом будет удалена)
        //2.2 Вызов метода с REcyclerVeiw
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            //2.2 Отслеживание списка из RecyclerView
            listShoppingAdapter.submitList(it)
        }
        //кнопка добавления нового элемента
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.add_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ItemShoppingActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ItemShoppingFragment.newInstanceAddItem())
            }
        }
    }
    //4.6.1 Функция для взаимодействия с фрагментом
    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

    //4.5.9 По-умолчанию itemShoppingContainer равен 0(значит обычная ориентация), если !=0, то делаем альбомную
    private fun isOnePaneMode(): Boolean {
        return itemShoppingContainer == null
    }
    // 4.5.10 Создаем метод, который запускает этот контейнер
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.item_shopping_container, fragment).addToBackStack(null).commit()
    }

    //2.2 привязка адаптера к RecyclerViewыу
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
    //2.2.5.1 Добавление длительного слушателя нажатий(для пометки активный или неактивный)
    private fun setupOnLongClickListener() {
        listShoppingAdapter.onItemShoppingLongClickListener = {
            viewModel.changeValueState(it)
        }
    }

    //2.2.5.2 Добавление слушателя нажатий(для редактирования элемента)
    private fun setupOnClickListener() {
        listShoppingAdapter.onItemShoppingClickListener = {
            if (isOnePaneMode()) {
                val intent = ItemShoppingActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ItemShoppingFragment.newInstanceEditItem(it.id))
            }
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